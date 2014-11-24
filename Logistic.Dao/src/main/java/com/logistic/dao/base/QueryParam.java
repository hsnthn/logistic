package com.logistic.dao.base;

public class QueryParam {

    private String name;

    private Object value;

    public QueryParam(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}