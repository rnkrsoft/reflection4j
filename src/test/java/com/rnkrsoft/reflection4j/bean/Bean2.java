package com.rnkrsoft.reflection4j.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by woate on 2018/10/20.
 */
@Data
public class Bean2 implements Serializable{
    public Bean2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    String name = "name3";
    int age = 3;
}
