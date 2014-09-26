package org.david.rain.security.tool;

public interface UrlMatcher {

	Object compile(String urlPattern);

	boolean pathMathesUrl(Object compiledUrlPattern, String url);

	String getUniversalMatchPattern();

	boolean requiresLowerCaseUrl();
}
