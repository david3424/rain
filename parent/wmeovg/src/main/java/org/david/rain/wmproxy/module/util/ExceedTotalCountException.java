package org.david.rain.wmproxy.module.util;

import org.hibernate.HibernateException;

/**
 * @ClassName ExceedTotalCountException
 * @version 1.0
 * @date 2010-9-13 下午01:59:20
 */
public class ExceedTotalCountException extends HibernateException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param s
	 */
	public ExceedTotalCountException(String s) {
		super(s);
	}

}
