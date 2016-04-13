package org.david.rain.wmproxy.module.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

/**
 * @ClassName SpringJmsErrorHandler
 * @Description SpringJmsErrorHandler
 */
public class SpringJmsErrorHandler implements ErrorHandler {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void handleError(Throwable th) {
        log.info("===SpringJmsErrorHandler===正常抛出{}", th.getMessage());
    }

}
