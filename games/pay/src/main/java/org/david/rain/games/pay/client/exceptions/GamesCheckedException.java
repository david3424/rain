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
public abstract class GamesCheckedException extends Exception {


    private static final long serialVersionUID = -8477109221269022292L;

    public GamesCheckedException() {
        super();
    }

    public GamesCheckedException(String message) {
        super(message);
    }

    public GamesCheckedException(Throwable cause) {
        super(cause);
    }

    public GamesCheckedException(String message, Throwable cause) {
        super(message, cause);
    }
}
