package com.download.models;

public class ShortFileInfo {

    private Integer id;
    private String fileName;

    public ShortFileInfo() {
    }
    public ShortFileInfo(Integer id, String fileName) {
        this.id = id;
        this.fileName = fileName;

    }
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
