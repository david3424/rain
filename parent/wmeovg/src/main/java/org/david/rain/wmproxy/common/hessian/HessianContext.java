package org.david.rain.wmproxy.common.hessian;

import javax.servlet.ServletRequest;

public class HessianContext {

    private ServletRequest _request;
    private static final ThreadLocal<HessianContext> _localContext 
                                  = new ThreadLocal<HessianContext>() {
        @Override
        public HessianContext initialValue() {
            return new HessianContext();
        }
    };

    private HessianContext() {
    }

    public static void setRequest(ServletRequest request) {
        _localContext.get()._request = request;
    }

    public static ServletRequest getRequest() {
        return _localContext.get()._request;
    }

    public static void clear() {
        _localContext.get()._request = null;
    }
}
