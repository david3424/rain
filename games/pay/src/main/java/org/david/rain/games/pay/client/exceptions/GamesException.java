/**
 * ****************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * *****************************************************************************
 */
package org.david.rain.games.pay.client.exceptions;

/**
 * Games公用的Exception.
 * <p/>
 * 继承自RuntimeException.
 */
public abstract class GamesException extends RuntimeException {


    private static final long serialVersionUID = -8477109221269022292L;

    public GamesException() {
        super();
    }

    public GamesException(String message) {
        super(message);
    }

    public GamesException(Throwable cause) {
        super(cause);
    }

    public GamesException(String message, Throwable cause) {
        super(message, cause);
    }
}
