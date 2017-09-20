package org.david.rain.common.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.Reflection;

/**
 * 日志工具
 */
public class LoggerUtil {

	/**
	 * 从LoggerFactory中获取（或创建）指定类的日志记录对象.
	 *
	 * @param caller
	 *            Object 调用记录日志的类.
	 * @return Logger
	 */
	private static Logger getLogger(final Class<?> caller) {
		return LoggerFactory.getLogger(caller);
	}

	/**
	 * 从LoggerFactory中获取（或创建）指定名字的日志记录对象.
	 *
	 * @param loggerName
	 * @return
	 */
	private static Logger getLogger(final String loggerName) {
		return LoggerFactory.getLogger(loggerName);
	}

	/**
	 * 得到调用LoggerUtil类的trace、debug、info、error方法的调用者所在的类.
	 *
	 * @return
	 */
	private static Class<?> getCallerClass() {
		return Reflection.getCallerClass(3);
	}

	/**
	 * 记录跟踪日志信息.
	 *
	 * @param msg
	 */
	public static void trace(final String msg) {
		Logger logger = getLogger(getCallerClass());
		logger.trace(msg);
	}
	/**
	 *
	 * @param format
	 * @param arguments
	 */
	public static void trace(final String format, Object ...arguments) {
		Logger logger = getLogger(getCallerClass());
		logger.trace(format, arguments);
	}
	/**
	 * 记录跟踪日志信息.
	 *
	 * @param clazz
	 * @param msg
	 */
	public static void trace(Class<?> clazz, final String msg) {
		Logger logger = getLogger(clazz);
		logger.trace(msg);
	}

	/**
	 * 记录跟踪日志信息.
	 *
	 * @param loggerName
	 * @param msg
	 */
	public static void trace(final String loggerName, final String msg) {
		Logger logger = getLogger(loggerName);
		logger.trace(msg);
	}

	/**
	 * 记录调式日志信息.
	 *
	 * @param msg
	 */
	public static void debug(final String msg) {
		Logger logger = getLogger(getCallerClass());
		logger.debug(msg);
	}

	/**
	 *
	 * @param format
	 * @param arguments
	 */
	public static void debug(final String format, Object ...arguments) {
		Logger logger = getLogger(getCallerClass());
		logger.debug(format, arguments);
	}

	/**
	 * 记录调式日志信息.
	 *
	 * @param clazz
	 * @param msg
	 */
	public static void debug(final Class<?> clazz, final String msg) {
		Logger logger = getLogger(clazz);
		logger.debug(msg);
	}

	/**
	 * 记录调式日志信息.
	 *
	 * @param loggerName
	 * @param msg
	 *//*
	public static void debug(final String loggerName, final String msg) {
		Logger logger = getLogger(loggerName);
		logger.debug(msg);
	}*/

	/**
	 * 记录提示日志信息.
	 *
	 * @param msg
	 */
	public static void info(final String msg) {
		Logger logger = getLogger(getCallerClass());
		logger.info(msg);
	}

	/**
	 *
	 * @param format
	 * @param arguments
	 */
	public static void info(final String format, Object ...arguments) {
		Logger logger = getLogger(getCallerClass());
		logger.info(format, arguments);
	}

	/**
	 * 记录提示日志信息.
	 *
	 * @param clazz
	 * @param msg
	 */
	public static void info(final Class<?> clazz, final String msg) {
		Logger logger = getLogger(clazz);
		logger.info(msg);
	}

	/**
	 * 记录提示日志信息.
	 *
	 * @param loggerName
	 * @param msg
	 *//*
	public static void info(final String loggerName, final String msg) {
		Logger logger = getLogger(loggerName);
		logger.info(msg);
	}
*/
	/**
	 * 记录警告日志信息.
	 *
	 * @param msg
	 */
	public static void warn(final String msg) {
		Logger logger = getLogger(getCallerClass());
		logger.warn(msg);
	}

	/**
	 *
	 * @param format
	 * @param arguments
	 */
	public static void warn(final String format, Object ...arguments) {
		Logger logger = getLogger(getCallerClass());
		logger.warn(format, arguments);
	}

	/**
	 * 记录警告日志信息.
	 *
	 * @param clazz
	 * @param msg
	 */
	public static void warn(final Class<?> clazz, final String msg) {
		Logger logger = getLogger(clazz);
		logger.warn(msg);
	}

	/**
	 * 记录警告日志信息.
	 *
	 * @param loggerName
	 * @param msg
	 *//*
	public static void warn(final String loggerName, final String msg) {
		Logger logger = getLogger(loggerName);
		logger.warn(msg);
	}*/

	/**
	 * 记录警告日志信息.
	 *
	 * @param e
	 */
	public static void warn(final Throwable e) {
		Logger logger = getLogger(getCallerClass());
		logger.warn(e.getMessage(), e);
	}

	/**
	 * 记录警告日志信息.
	 *
	 * @param clazz
	 * @param e
	 */
	public static void warn(final Class<?> clazz, final Throwable e) {
		Logger logger = getLogger(clazz);
		logger.warn(e.getMessage(), e);
	}

	/**
	 * 记录警告日志信息.
	 *
	 * @param loggerName
	 * @param e
	 */
	public static void warn(final String loggerName, final Throwable e) {
		Logger logger = getLogger(loggerName);
		logger.warn(e.getMessage(), e);
	}

	/**
	 * 记录警告日志信息.
	 *
	 * @param msg
	 * @param e
	 */
	public static void warn(final String msg, final Exception e) {
		Logger logger = getLogger(getCallerClass());
		logger.warn(msg, e);
	}

	/**
	 * 记录警告日志信息.
	 *
	 * @param clazz
	 * @param msg
	 * @param e
	 */
	public static void warn(final Class<?> clazz, final String msg,
							final Exception e) {
		Logger logger = getLogger(clazz);
		logger.warn(msg, e);
	}

	/**
	 * 记录警告日志信息.
	 *
	 * @param loggerName
	 * @param msg
	 * @param e
	 */
	public static void warn(final String loggerName, final String msg,
							final Exception e) {
		Logger logger = getLogger(loggerName);
		logger.warn(msg, e);
	}

	/**
	 * 记录错误日志信息.
	 *
	 * @param caller
	 * @param msg
	 */
	public static void error(final String msg) {
		Logger logger = getLogger(getCallerClass());
		logger.error(msg);
	}

	/**
	 *
	 * @param format
	 * @param arguments
	 */
	public static void error(final String format, Object ...arguments) {
		Logger logger = getLogger(getCallerClass());
		logger.error(format, arguments);
	}

	/**
	 * 记录错误日志信息.
	 *
	 * @param clazz
	 * @param msg
	 */
	public static void error(final Class<?> clazz, final String msg) {
		Logger logger = getLogger(clazz);
		logger.error(msg);
	}

	/**
	 * 记录错误日志信息.
	 *
	 * @param loggerName
	 * @param msg
	 */
	public static void error(final String loggerName, final String msg) {
		Logger logger = getLogger(loggerName);
		logger.error(msg);
	}

	/**
	 * 记录错误日志信息.
	 *
	 * @param caller
	 * @param e
	 */
	public static void error(final Throwable e) {
		Logger logger = getLogger(getCallerClass());
		logger.error(e.getMessage(), e);
	}

	/**
	 * 记录错误日志信息.
	 *
	 * @param clazz
	 * @param e
	 */
	public static void error(final Class<?> clazz, final Throwable e) {
		Logger logger = getLogger(clazz);
		logger.error(e.getMessage(), e);
	}

	/**
	 * 记录错误日志信息.
	 *
	 * @param loggerName
	 * @param e
	 */
	public static void error(final String loggerName, final Throwable e) {
		Logger logger = getLogger(loggerName);
		logger.error(e.getMessage(), e);
	}

	/**
	 * 记录错误日志信息.
	 *
	 * @param msg
	 * @param e
	 */
	public static void error(final String msg, final Exception e) {
		Logger logger = getLogger(getCallerClass());
		logger.error(msg, e);
	}

	/**
	 * 记录错误日志信息.
	 *
	 * @param clazz
	 * @param msg
	 * @param e
	 */
	public static void error(final Class<?> clazz, final String msg,
							 final Exception e) {
		Logger logger = getLogger(clazz);
		logger.error(msg, e);
	}

	/**
	 * 记录错误日志信息.
	 *
	 * @param loggerName
	 * @param msg
	 * @param e
	 */
	public static void error(final String loggerName, final String msg,
							 final Exception e) {
		Logger logger = getLogger(loggerName);
		logger.error(msg, e);
	}
}