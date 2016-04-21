/**
 * ****************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * *****************************************************************************
 */
package org.david.rain.games.pay.client.exceptions;

/**
 * utils公用的Exception.
 */
public class UtilsException extends GamesCheckedException {

    private static final long serialVersionUID = -366373373148653863L;

    public UtilsException() {
        super();
    }

    public UtilsException(String message) {
        super(message);
    }

    public UtilsException(Throwable cause) {
        super(cause);
    }

    public UtilsException(String message, Throwable cause) {
        super(message, cause);
    }

}
