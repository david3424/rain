package com.noah.crm.cloud.user.service;

import com.noah.crm.cloud.apis.exception.ServiceException;
import com.noah.crm.cloud.common.event.TransactionalForExceptionRollback;
import com.noah.crm.cloud.common.event.service.EventBus;
import com.noah.crm.cloud.user.api.UserErrorCode;
import com.noah.crm.cloud.user.api.dtos.RegisterDto;
import com.noah.crm.cloud.user.api.events.UserCreated;
import com.noah.crm.cloud.user.dao.UserRepository;
import com.noah.crm.cloud.user.domain.User;
import com.noah.crm.cloud.utils.text.PasswordHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;
import java.util.Optional;

/**
 * @author xdw9486
 */
@Service
public class UserService {

    protected Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventBus eventBus;



    @TransactionalForExceptionRollback
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getById(Long userId) {
        return Optional.ofNullable(userRepository.findOne(userId));
    }

    @Transactional
    public User register(RegisterDto registerDto) {
        if (isUsernameExist(registerDto.getUsername(), Optional.empty())) {
            throw new ServiceException(UserErrorCode.UsernameExist,
                    String.format("用户名%s已存在", registerDto.getUsername()));
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        try {
            user.setPassword(PasswordHash.createHash(registerDto.getPassword()));
        } catch (GeneralSecurityException e) {
            logger.error("创建哈希密码的时候发生错误", e);
            throw new ServiceException("用户注册失败");
        }

        userRepository.save(user);

        //用户创建事件
        eventBus.publish(new UserCreated(user.getId(), user.getUsername(), user.getCreateTime()));

        return user;
    }


    /**
     * 判断用户名是否存在
     *
     * @param username
     * @param userId   当前用户ID,如果是修改用户的话,需要传,否则可以传empty
     * @return
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public boolean isUsernameExist(String username, Optional<Integer> userId) {

        return userRepository.isUsernameExist(username, userId);
    }

}
