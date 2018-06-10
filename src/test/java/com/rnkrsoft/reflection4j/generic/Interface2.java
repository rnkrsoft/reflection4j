package com.rnkrsoft.reflection4j.generic;



public interface Interface2 extends Interface1<String,Integer> {
    Response2<String, Integer, Integer> work(Request2<String, Integer> request);
}
