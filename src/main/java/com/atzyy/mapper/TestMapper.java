package com.atzyy.mapper;

import com.atzyy.entity.TestEntity;
import org.junit.jupiter.api.Test;

public interface TestMapper {
    TestEntity selectTest(Long id);

    Integer addTest(TestEntity entity);
}
