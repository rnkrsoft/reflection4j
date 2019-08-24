package com.rnkrsoft.reflection4j.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by rnkrsoft.com on 2018/10/20.
 */
@Data
public class Bean1 implements Serializable{
    Bean2 bean2;
    String name1;
    int age1;
}
