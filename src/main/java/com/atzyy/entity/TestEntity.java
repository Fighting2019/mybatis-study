package com.atzyy.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestEntity implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String firstName;
    private Integer growUp;
    private String growUpName;
    private String deptName;
}
