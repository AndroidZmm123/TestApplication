package com.example.zongm.testapplication;

import javax.inject.Inject;

/**
 * @author zongm on 2018/6/20
 */
public class Product {
    private  int id;
    private  String name="默认值";

    @Inject
    public Product(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
