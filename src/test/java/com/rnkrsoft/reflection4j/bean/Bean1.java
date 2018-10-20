package com.rnkrsoft.reflection4j.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by woate on 2018/10/20.
 */
@Data
public class Bean1 implements Serializable{
    Bean2 bean1bean2 = new Bean2("name3", 3);
    String name = "name2";
    int age = 2;
}
