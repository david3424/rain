package com.noah.crm.cloud.common.spring.cloud.stream;

import com.google.common.base.Stopwatch;
import com.noah.crm.cloud.apis.event.constants.EventType;
import com.noah.crm.cloud.common.event.EventRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.cloud.stream.binder.*;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 自定义类与父类的区别:
 * 1. 重新定义bindConsumer, 因为在启动的时候需要监听事件的都调用了EventRegistry.register.
 *    所以EventRegistry已经包含了所有的感兴趣的事件类型(即topic), 在这里就将所有感兴趣的topic注册到Processor.INPUT这个channel
 *
 * 2. 重新定义bindProducer, 因为output channel全都是根据事件名称动态创建的, 他们的配置全部沿用Processor.OUTPUT这个channel的配置
 *
 *
 * @author xdw9486*/


public class CustomChannelBindingService extends BindingService {

    private static Logger log = LoggerFactory.getLogger(CustomChannelBindingService.class);

    private final CustomValidatorBean validator;

    private BinderFactory binderFactory;

    private final BindingServiceProperties channelBindingServiceProperties;

    private final Map<String, List<Binding<MessageChannel>>> consumerBindings = new HashMap<>();

    private final Map<String, Binding<MessageChannel>> producerBindings = new HashMap<>();

    private final EventRegistry eventRegistry;

    public CustomChannelBindingService(BindingServiceProperties channelBindingServiceProperties,
                                       BinderFactory binderFactory, EventRegistry eventRegistry) {
        super(channelBindingServiceProperties, binderFactory);
        this.channelBindingServiceProperties = channelBindingServiceProperties;
        this.binderFactory = binderFactory;
        this.eventRegistry = eventRegistry;
        this.validator = new CustomValidatorBean();
        this.validator.afterPropertiesSet();
    }

    @Override
    public <MessageChannel>Collection<Binding<MessageChannel>> bindConsumer(MessageChannel inputChannel, String inputChannelName) {
        Set<EventType> eventTypeSet = eventRegistry.getInterestedEventTypes();
        String[] channelBindingTargets = eventTypeSet.stream().
                map(EventType::name).collect(Collectors.toList()).toArray(new String[eventTypeSet.size()]);
        if(log.isInfoEnabled()) {
            log.info("spring kafka consumer bind to these topics: " + Arrays.toString(channelBindingTargets));
        }
        List<Binding<MessageChannel>> bindings = new ArrayList<>();
        Binder<MessageChannel, ConsumerProperties, ?> binder =
                (Binder<MessageChannel, ConsumerProperties, ?>) getBinderForChannel(inputChannelName);
        ConsumerProperties consumerProperties =
                this.channelBindingServiceProperties.getConsumerProperties(inputChannelName);
        if (binder instanceof ExtendedPropertiesBinder) {
            ExtendedPropertiesBinder extendedPropertiesBinder = (ExtendedPropertiesBinder) binder;
            Object extension = extendedPropertiesBinder.getExtendedConsumerProperties(inputChannelName);
            ExtendedConsumerProperties extendedConsumerProperties = new ExtendedConsumerProperties(extension);
            BeanUtils.copyProperties(consumerProperties, extendedConsumerProperties);
            consumerProperties = extendedConsumerProperties;
        }
        validate(consumerProperties);
        for (String target : channelBindingTargets) {
            Binding<MessageChannel> binding = binder.bindConsumer(target, channelBindingServiceProperties.getGroup(inputChannelName), inputChannel, consumerProperties);
            bindings.add(binding);
        }
        this.consumerBindings.put(inputChannelName, bindings);
        return bindings;

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Binding<T> bindProducer(T output, String outputChannelName) {
        String channelBindingTarget = this.channelBindingServiceProperties.getBindingDestination(outputChannelName);
        Binder<MessageChannel, ?, ProducerProperties> binder =
                (Binder<MessageChannel, ?, ProducerProperties>) getBinderForChannel(outputChannelName);
        //统一使用OUTPUT的配置
        String channelNameForProperties = Processor.OUTPUT;
        ProducerProperties producerProperties = this.channelBindingServiceProperties.getProducerProperties(channelNameForProperties);
        if (binder instanceof ExtendedPropertiesBinder) {
            Object extension = ((ExtendedPropertiesBinder) binder).getExtendedProducerProperties(channelNameForProperties);
            ExtendedProducerProperties extendedProducerProperties = new ExtendedProducerProperties<>(extension);
            BeanUtils.copyProperties(producerProperties, extendedProducerProperties);
            producerProperties = extendedProducerProperties;
        }
        validate(producerProperties);

        Stopwatch stopwatch = null;
        if(log.isDebugEnabled()) {
            stopwatch = Stopwatch.createStarted();
        }

        Binding<MessageChannel> binding = binder.bindProducer(channelBindingTarget, (MessageChannel)output, producerProperties);

        if(log.isDebugEnabled() && stopwatch != null) {
            stopwatch.stop();
            log.debug(String.format("bind kafka producer [%s] cost %d ms",
                    outputChannelName, stopwatch.elapsed(TimeUnit.MILLISECONDS)));
        }

        this.producerBindings.put(outputChannelName, binding);
        return binding;
    }

    @Override
    public void unbindConsumers(String inputChannelName) {
        List<Binding<MessageChannel>> bindings = this.consumerBindings.remove(inputChannelName);
        if (bindings != null && !CollectionUtils.isEmpty(bindings)) {
            for (Binding<MessageChannel> binding : bindings) {
                binding.unbind();
            }
        }
        else if (log.isWarnEnabled()) {
            log.warn("Trying to unbind channel '" + inputChannelName + "', but no binding found.");
        }
    }

    @Override
    public void unbindProducers(String outputChannelName) {
        Binding<MessageChannel> binding = this.producerBindings.remove(outputChannelName);
        if (binding != null) {
            binding.unbind();
        }
        else if (log.isWarnEnabled()) {
            log.warn("Trying to unbind channel '" + outputChannelName + "', but no binding found.");
        }
    }


    private Binder<MessageChannel, ?, ?> getBinderForChannel(String channelName) {
        String transport = this.channelBindingServiceProperties.getBinder(channelName);
        return binderFactory.getBinder(transport);
    }

    private void validate(Object properties) {
        RelaxedDataBinder dataBinder = new RelaxedDataBinder(properties);
        dataBinder.setValidator(validator);
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new IllegalStateException(dataBinder.getBindingResult().toString());
        }
    }

}
