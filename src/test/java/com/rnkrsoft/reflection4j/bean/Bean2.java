package com.rnkrsoft.reflection4j.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by rnkrsoft.com on 2018/10/20.
 */
@Data
public class Bean2 implements Serializable{
    Bean3 bean3;
    String name2;
    int age2 = 2;
}
