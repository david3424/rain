/**
 * ****************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * *****************************************************************************
 */
package org.david.rain.games.pay.client.exceptions;

/**
 * Service层公用的Exception.
 */
public class ServiceException extends GamesCheckedException {


    private static final long serialVersionUID = -6435591110896578495L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
