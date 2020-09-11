package com.atzyy.mapper;

import com.atzyy.entity.TestEntity;
import org.apache.ibatis.annotations.Param;

public interface TestMapper {
    TestEntity selectTest(Long id);

    Integer addTest(TestEntity entity);

    TestEntity selectByParams(@Param("id") Long id, @Param("name") String name,Integer age);
}
