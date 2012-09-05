package com.memmee.theme.dto;

import java.io.Serializable;


public class Theme implements Serializable {

    private static final long serialVersionUID = 1706390233616761884L;

    private Long id;

    private String name;

    private String listName;

    private String stylePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getStylePath() {
        return stylePath;
    }

    public void setStylePath(String stylePath) {
        this.stylePath = stylePath;
    }

}
