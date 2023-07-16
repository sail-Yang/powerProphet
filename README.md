# 配置说明

主配置文件为`application.yaml`，在里面需要配置数据库服务、邮箱服务、redis服务以及端口，示例如下：

```yaml
spring:
  datasource:
    name: druidDataSource
    # 配置驱动类名
    driver-class-name: org.mariadb.jdbc.Driver
    # 配置数据库用户名、密码以及url
    username: yzu
    password: 
    type: com.alibaba.druid.pool.DruidDataSource
    url: jdbc:mariadb://127.0.0.1:3306/powerProphet?characterEncoding=utf-8&useSSL=false&useTimezone=true&serverTimezone=GMT%2B8&rewriteBatchedStatements=true&allowMultiQueries=true
    # 配置邮箱服务
  mail:
    host: smtp.qq.com
    protocol: smtp
    default-encoding: UTF-8
    # 配置发送邮箱，密码获取方式参考网上教程
    username: xxxxxxxxxx@qq.com
    password: 
    properties:
      mail:
        smtp:
          ssl:
            enable: true
            required: false
      # 配置redis服务
  data:
    redis:
    # 配置IP地址以及端口号
      host: 127.0.0.1
      port: 6379
      # 填写密码
      password: 
      database: 0
      lettuce:
        pool:
          max-idle: 16
          max-active: 32
          min-idle: 8
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: auto
server:
# 配置端口
  port: 8081
#  port: 8099
```

## 第三方接口配置

本后端服务会使用`RestTemplate`调用第三方Flask后端服务，以访问算法服务，因此需要配置接口地址，在`src/main/java/com/sailyang/powerprophet/controller/PredictController.java`文件中，配置下列部分：

```java
// 第55行 调用深度学习模型，填写实时预测模块接口IP端口地址
String url = "http://118.195.146.68:5001/realtime";

//第99行 调用深度学习模型，填写自定义预测模块接口IP端口地址
String url = "http://118.195.146.68:5001/period";
```

## 数据库创建

运行目录下的`powerProphet.sql`即可。



