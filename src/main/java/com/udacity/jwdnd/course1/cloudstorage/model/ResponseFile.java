package com.udacity.jwdnd.course1.cloudstorage.model;

public class ResponseFile {
    private Integer fileid;
    private String filename;
    private String dataURL;

    public ResponseFile(Integer fileid, String filename, String dataURL) {
        this.fileid = fileid;
        this.filename = filename;
        this.dataURL = dataURL;
    }

    public Integer getFileid() {
        return fileid;
    }

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    public String getDataURL() {
        return dataURL;
    }

    public void setDataURL(String dataURL) {
        this.dataURL = dataURL;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
