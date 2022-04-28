package com.download.models;

import org.checkerframework.checker.signature.qual.Identifier;

public class FileInfo {
    private Integer id;
    private String name;
    private String type;

    private byte[] data;
    public FileInfo() {
    }
    public FileInfo(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public FileInfo(Integer id, String name, String type, byte[] data) {
       this(name,type,data);
       this.id = id;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
}