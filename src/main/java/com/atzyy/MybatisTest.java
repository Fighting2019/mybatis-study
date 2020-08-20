package com.atzyy;

import com.atzyy.entity.TestEntity;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {

    @Test
    public void test() throws IOException {
        //从 xml 配置文件中构建 SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            //statement 已经映射的 sql
            TestEntity entity = sqlSession.selectOne("com.atzyy.test.selectTest", 1);
            System.out.println(entity.toString());
        } finally {
            sqlSession.close();
        }
    }
}
