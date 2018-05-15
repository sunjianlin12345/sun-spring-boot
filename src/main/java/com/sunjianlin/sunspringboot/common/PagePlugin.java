package com.sunjianlin.sunspringboot.common;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by sunjianlin
 * 2018年04月20日 17:54:16
 */
//@Intercepts 注解: 为当前插件指定要拦截哪个对象的哪个方法,以及方法中的参数
@Intercepts({ @Signature(type = StatementHandler.class,
        method = "prepare", args = { Connection.class, Integer.class}) })
public class PagePlugin implements Interceptor {

    private  final Logger logger = LoggerFactory.getLogger(getClass());

    // 拦截目标对象中目标方法的执行
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        logger.info("mybatis intercept sql:{}", sql);
        //执行目标方法并返回
        return invocation.proceed();
    }

    //包装目标对象,即为目标对象创建一个代理对象
    @Override
    public Object plugin(Object o) {
        //借助 Plugin 的 wrap(Object target,Interceptor interceptor); 包装我们的目标对象
        // target: 目标对象, interceptor: 拦截器, this 表示使用当前拦截器
        return Plugin.wrap(o, this);
    }

    // 可以获取插件注册时,传入的property属性
    @Override
    public void setProperties(Properties properties) {
        String dialect = properties.getProperty("dialect");
        logger.info("mybatis intercept dialect:{}", dialect);
    }
}
