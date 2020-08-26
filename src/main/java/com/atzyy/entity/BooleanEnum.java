package com.atzyy.entity;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum BooleanEnum {
    BOOLEAN_TRUE(1,"是"),
    BOOLEAN_FALSE(0,"否"),
    ;

    private static final Map<Integer,BooleanEnum> lookup = new HashMap<>();
    private Integer code;
    private String name;

    BooleanEnum(Integer code,String name){
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    static {
        for (BooleanEnum booleanEnum: EnumSet.allOf(BooleanEnum.class)){
            lookup.put(booleanEnum.code,booleanEnum);
        }
    }

    public static final BooleanEnum resolve(Integer code){
        return lookup.get(code);
    }
}
