package com.devops4j.reflection4j.generic;



public interface Interface1<T, K> extends Interface0<T, K, String> {
    Response1<Integer> doing(Request1<String> request);
}
