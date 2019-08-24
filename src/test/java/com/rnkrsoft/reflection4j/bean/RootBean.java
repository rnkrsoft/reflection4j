package com.rnkrsoft.reflection4j.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by rnkrsoft.com on 2018/10/20.
 */
@Data
public class RootBean implements Serializable{
    Bean1 bean1 = new Bean1();
    Bean2 bean2 = new Bean2();
}
