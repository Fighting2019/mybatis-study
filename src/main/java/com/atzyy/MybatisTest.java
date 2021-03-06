package com.atzyy;

import com.atzyy.entity.TestEntity;
import com.atzyy.mapper.TestMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {

    /**
     * 直接使用mybatis提供的api执行查询
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        //从 xml 配置文件中构建 SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            //statement 已经映射的 sql
            TestEntity entity = sqlSession.selectOne("com.atzyy.mapper.TestMapper.selectTest", 1);
            System.out.println(entity.toString());
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 总结：
     *  1、根据配置文件（全局，sql 映射）初始化 Configuration 对象
     *  2、创建一个 DefaultSqlSession 对象
     *      它里面包含 Configuration 以及 Transaction（事务）和 Executor（根据全局配置文件的 defaultExecutorType 创建出对应的 Executor）
     *  3、DefaultSqlSession.getMapper()，拿到 Mapper 接口对应的 MapperProxy；
     *  4、MapperProxy 里面有 DefaultSqlSession
     *  5、执行增删改查方法：
     *      1）、调用 DefaultSqlSession 的增删改查（其实就是调用其中 Executor 的增删改查），
     *      2）、会创建一个 StatementHandler 对象（根据 sql 映射文件中配置的 statementType 属性）。
     *      （同时也会创建出 ParameterHandler 和 ResultSetHandler 对象）
     *      3）、调用 StatementHandler 预编译参数及设置参数值。使用 ParameterHandler 来给 sql 设置参数
     *      4）、调用 StatementHandler 的增删改查方法。
     *      5）、使用ResultSetHandler 封装结果（根据TypeHandler）
     *
     *
     */
    @Test
    public void selectTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 通过配置文件构建 SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        /**
         * 开启 SqlSession
         * 1、SqlSession 代表和数据库一次会话，用完必须关闭
         * 2、SqlSession 和 jdbc Connection 一样都是非线程安全的，每次使用都应该获取新的对象
         * 3、mapper 接口没有实现类，但是 mybatis 会为这个接口生成一个代理对象
         * 4、两个重要的配置文件：
         *      4.1、mybatis 的全局配置文件，包含数据库连接池信息，事务管理器信息等。。系统运行环境信息
         *      4.2、sql 映射文件，保存了每一个 sql 语句的映射信息
         */
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            // 从 SqlSession 中获取 Mapper 接口
            TestMapper mapper = sqlSession.getMapper(TestMapper.class);
            TestEntity entity = mapper.selectTest(1L);
            System.out.println(entity);

            TestEntity testEntity = mapper.selectTest(1L);
            System.out.println(entity == testEntity);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void insertTest() throws IOException {
        TestEntity entity = new TestEntity();
        entity.setName("mamba");
        entity.setAge(41);
        entity.setFirstName("kobe");
        entity.setGrowUp(1);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            TestMapper mapper = sqlSession.getMapper(TestMapper.class);
            Integer integer = mapper.addTest(entity);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
        Long id = entity.getId();
        System.out.println(id);
    }

    @Test
    public void paramTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            TestMapper mapper = sqlSession.getMapper(TestMapper.class);
            TestEntity entity = mapper.selectByParams(1L, "jay", 10);
            System.out.println(entity);
        } finally {
            sqlSession.close();
        }
    }
}
