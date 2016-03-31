/**
 * console APPLIANCE CHAINS.
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package org.david.rain.monitor.util.gson;

/**
 * 
 * SimpleJsonTree
 * 
 * @author chenqi
 */
public class SimpleJsonTree {
    /**
     * id
     */
    private String id;
    /**
     * pId
     */
    private String pId;
    /**
     * name
     */
    private String name;
    /**
     * open
     */
    private Boolean open;
    /**
     * url
     */
    private String url;
    /**
     * target
     */
    private String target;
    /**
     * isParent
     */
    private Boolean isParent;
    /**
     * isLeaf
     */
    private Boolean isLeaf;

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the target
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return the isParent
     */
    public Boolean getIsParent() {
        return isParent;
    }

    /**
     * @param isParent the isParent to set
     */
    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    /**
     * @return the isLeaf
     */
    public Boolean getIsLeaf() {
        return isLeaf;
    }

    /**
     * @param isLeaf the isLeaf to set
     */
    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * @return the open
     */
    public Boolean getOpen() {
        return open;
    }

    /**
     * @param open the open to set
     */
    public void setOpen(Boolean open) {
        this.open = open;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @return String 返回值
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param id 参考说明
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @return String 返回值
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param name 参考说明
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @return String 返回值
     */
    public String getpId() {
        return pId;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param pId 参考说明
     */
    public void setpId(String pId) {
        this.pId = pId;
    }

}
