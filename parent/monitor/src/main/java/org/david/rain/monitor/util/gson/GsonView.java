package org.david.rain.monitor.util.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * A View that renders its model as a JSON object.
 * 
 */
public class GsonView extends AbstractView {
    /**
     * datePattern
     */
    private String datePattern = "yyyy-MM-dd HH:mm:ss";
    /**
     * jsonObjectName
     */
    private String jsonObjectName;

    /**
     * responseStatus
     */
    private int responseStatus = HttpStatus.OK.value();

    /**
     * excludeStrategy
     */
    private ExclusionStrategy excludeStrategy;

    /**
     * GsonView constructor
     */
    public GsonView() {
        super();
    }

    /**
     * GsonView constructor
     * 
     * @param jsonObjectName 参考说明
     * @param excludeStrategy 参考说明
     */
    public GsonView(String jsonObjectName, ExclusionStrategy excludeStrategy) {
        super();
        this.jsonObjectName = jsonObjectName;
        this.excludeStrategy = excludeStrategy;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setStatus(getResponseStatus());
        response.setContentType(getContentType());
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(datePattern);
        if (excludeStrategy != null) {
            gsonBuilder.setExclusionStrategies(excludeStrategy);
        }
        Gson gson = gsonBuilder.create();
        // modified by chenqi
        if (null != model.keySet() && model.keySet().size() == 1) {
            gson.toJson(model.values().iterator().next(), response.getWriter());
        } else {
            gson.toJson(jsonObjectName == null ? model : model.get(jsonObjectName), response.getWriter());
        }
    }

    /**
     * @return the objectName in model
     */
    public String getJsonObjectName() {
        return jsonObjectName;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param pattern 参考说明
     */
    public void setDatePattern(String pattern) {
        this.datePattern = pattern;
    }

    /**
     * @param objectName the objectName in model that will be convert to json, if not set, then convert the hole model
     *            to json
     */
    public void setJsonObjectName(String objectName) {
        this.jsonObjectName = objectName;
    }

    /**
     * @return the excludeStrategy
     */
    public ExclusionStrategy getExcludeStrategy() {
        return excludeStrategy;
    }

    /**
     * @param excludeStrategy the excludeStrategy to set
     */
    public void setExcludeStrategy(ExclusionStrategy excludeStrategy) {
        this.excludeStrategy = excludeStrategy;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.view.AbstractView#getContentType()
     */
    @Override
    public String getContentType() {
        return "text/html;charset=utf-8";
    }

    /**
     * @return the responseStatus
     */
    public int getResponseStatus() {
        return responseStatus;
    }

    /**
     * @param responseStatus the responseStatus to set
     */
    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

}
