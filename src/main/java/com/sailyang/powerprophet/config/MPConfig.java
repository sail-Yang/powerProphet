package com.sailyang.powerprophet.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MPConfig {
    /* 开启MP的分页功能 */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        /* 创建MP的拦截器栈 */
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.MARIADB);
        paginationInnerInterceptor.setOverflow(true);
        /* 初始化一个分页拦截器，加到拦截器栈中*/
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
