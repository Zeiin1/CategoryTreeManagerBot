package com.example.categorytreemanagerbot.entity;

import lombok.Data;

@Data
public class CategoryTreeRequest {
    private String parentName;
    private String name;
    public CategoryTreeRequest(String parentName,String name)
    {
        this.parentName = parentName;
        this.name = name;
    }
    public CategoryTreeRequest(){}
    public CategoryTreeRequest(String parentName)
    {
        this.parentName = parentName;
    }


}
