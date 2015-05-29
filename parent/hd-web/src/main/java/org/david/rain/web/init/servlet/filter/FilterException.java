package org.david.rain.web.init.servlet.filter;


/**
 * filter层的Exception.
 *
 * 继承自RuntimeException.
 *
 */
public class FilterException extends RuntimeException {

	private static final long serialVersionUID = 3583566093089790852L;

	public FilterException() {
		super();
	}

	public FilterException(String message) {
		super(message);
	}

	public FilterException(Throwable cause) {
		super(cause);
	}

	public FilterException(String message, Throwable cause) {
		super(message, cause);
	}
}