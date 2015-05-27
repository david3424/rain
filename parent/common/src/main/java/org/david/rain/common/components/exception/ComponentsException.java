package org.david.rain.common.components.exception;

/**
 * User: david
 * Date: 12-7-26
 * Time: 下午4:40
 */
public class ComponentsException extends Exception {
    public ComponentsException() {
        super();
    }

    public ComponentsException(String message) {
        super(message);
    }

    public ComponentsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentsException(Throwable cause) {
        super(cause);
    }
}
