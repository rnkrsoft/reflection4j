package com.devops4j.reflection.property;

import lombok.*;

import java.math.BigDecimal;

/**
 * Created by devops4j on 2017/7/13.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoBean {
    protected String name;
    protected int age;
    protected BigDecimal amt;
}
