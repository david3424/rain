package com.david.web.pppppp.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: liaom
 * Date: 2009-2-6
 * Time: 14:07:03
 * struts2 文件上传.
 */
public class FileSavedInfo{
    public List sourcenames = new ArrayList();
    public List filepaths = new ArrayList();
    public List simgpaths = new ArrayList();

    public void addSavedPath(String opath, String lpath, String spath){
         this.sourcenames.add(opath);
         this.filepaths.add(lpath);
         this.simgpaths.add(spath);
    }
    public void addSavedPath(String opath, String lpath){
        this.addSavedPath(opath, lpath, "");
    }

    public List getSourcenames() {
        return sourcenames;
    }

    public void setSourcenames(List sourcenames) {
        this.sourcenames = sourcenames;
    }

    public List getFilepaths() {
        return filepaths;
    }

    public void setFilepaths(List filepaths) {
        this.filepaths = filepaths;
    }

    public List getSimgpaths() {
        return simgpaths;
    }

    public void setSimgpaths(List simgpaths) {
        this.simgpaths = simgpaths;
    }

}

