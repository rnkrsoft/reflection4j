package com.devops4j.reflection.property;

import lombok.*;

/**
 * Created by devops4j on 2017/7/13.
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class DemoBean2 extends DemoBean{
    String target;
}
