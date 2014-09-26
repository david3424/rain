package org.david.rain.utils;

import com.google.common.net.MediaType;
import org.springframework.http.HttpHeaders;

public final class HttpHeadersFactory {
	
	private static final String CONTENT_TYPE = "Content-Type";

	private HttpHeadersFactory(){
	}
	
	public static HttpHeaders createJsonHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(CONTENT_TYPE, MediaType.JSON_UTF_8.toString());
		return headers;
	}
	
	public static HttpHeaders createTextHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(CONTENT_TYPE, MediaType.PLAIN_TEXT_UTF_8.toString());
		return headers;
	}
}
