# ql-boot-template

> 失手扑空蓝色的蜻蛉
> 
> Author: [chocohQL](https://github.com/chocohQL)
> 
> GitHub: [https://github.com/chocohQL/ql-boot-template](https://github.com/chocohQL/ql-boot-template)

## 项目介绍

ql-boot-template 是一个简洁且优雅的后端模板项目，聚焦于最通用常用的核心代码，即删即用。
+ 后端项目基于 SpirngBoot + SpringSecuity 搭建
+ 内置自定义 spring-boot-starter 模板
+ 内置简易 vue 前端测试项目

## 技术选型

+ JDK 8
+ SpringBoot 2.5.15
+ SpringSecurity 2.5.15
+ MyBatisPlus 3.5.3.1
+ MySQL 8.0.31
+ Redis 7.0.12

## 项目架构

![](https://fastly.jsdelivr.net/gh/chocohQL/ql-file@main/assets/github/ql-boot-template-%E6%9E%B6%E6%9E%84.svg)

项目主要分为4个模块:
+ **核心框架(ql-framework)**：主要用于编写核心业务、全局配置、异常处理等。
+ **数据访模块(ql-dal)**：用于定义实体类和数据库映射。
+ **安全模块(ql-security)**：用于编写安全相关的代码，如安全配置、认证授权逻辑等。
+ **通用模块(ql-common)**：用于编写通用工具类、常量类、异常类等。

其余模块非核心模块，如spring-boot-starter模板(ql-spring-boot)、前端模板(ql-vue-template)。

## 项目结构

```
ql-boot-template  
├── ql-common                           // 通用模块
│       └── annotation                      // 注解
│       └── constant                        // 常量
│       └── exception                       // 异常类
│       └── model                           // 数据模型
│       └── utils                           // 工具类
├── ql-framework                        // 核心模块
│       └── ascept                          // 切面
│       └── config                          // 全局配置
│       └── controller                      // 控制层
│       └── exception                       // 异常处理
│       └── service                         // 业务层
├── ql-security                         // 安全模块
│       └── filter                          // 过滤器
│       └── handler                         // 处理器
│       └── service                         // 认证业务
├── ql-security                         // 安全模块
│       └── config                          // 配置
│       └── filter                          // 过滤器
│       └── handler                         // 处理器
│       └── service                         // 认证业务
├── ql-dal                              // 数据持久层
│       └── domain                          // 数据层
│       └── mapper                          // 持久层
├── ql-spring-boot                      // spring-boot-starter模板
│       └── ql-spring-boot-autoconfigure    // starter自动配置
│       └── ql-spring-boot-starter          // starter入口
├── ql-vue-template                     // 前端模板
```

## 快速开始

### 创建数据库表

`sql/ql_boot_template.sql` 

创建数据库执行sql文件，仅生成一个 user 表，对应 User 类，可按需扩充字段。

### 修改application.yml文件

`ql-framework/src/main/resources/application.yml`

修改 MySQL 和 Redis 连接配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ql_boot_template?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false
    username: root
    password: yourPassword
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver

  redis:
    host: localhost
    port: 6379
    password: yourPassword
```

### 测试启动

启动项目，默认实现了登录和退出登录接口。

### 启动前端（可选）

进入 ql-vue-template 前端模板项目，安装依赖并启动。

```shell
npm install

npm run dev
```

sql中生成的测试用户 -> 用户名:chocoh，密码:123123。

## 了解更多

### 授权认证

使用SpringSecurity授权认证流程

![](https://fastly.jsdelivr.net/gh/chocohQL/ql-file@main/assets/github/ql-security.svg)

ql-security 模块对 SpringSecurity 进行了封装，在 SecurityConfig 中同一配置：
+ TokenService 中封装了对jwt的操作和在Redis保存用户信息的逻辑，可在此集中修改封装基于token的一系列逻辑。
+ UserDetailsServiceImpl 中实现了登录查询用户信息的逻辑。AuthenticationService 中定义了用户密码的校验逻辑，可在次基础上进一步修改角色管理逻辑。
+ handler 包主要定义了一些处理逻辑，如退出登录、认证失败等。

### 客户端

### 修改客户端配置

项目提供了多种现成客户端工具：
+ EmailClient：用于邮箱发送
+ OssClient：用于阿里云OSS存储

配置使用客户端：

1. 修改配置文件

```yaml
email:
  host: smtp.qq.com
  from: change@qq.com
  password: change
  username: change@qq.com

oss:
  endpoint: change
  url: change
  bucket-name: chocoh
  access-key-id: change
  access-key-secret: change
  file-dir: ql/avatar/
```

2. 注册客户端为bean

`ql-framework/src/main/java/com/chocoh/ql/framework/config/ClientConfig.java`

```java
@Configuration
public class ClientConfig {
    @Bean
    public EmailClient emailClient() {
        return new EmailClient();
    }

    @Bean
    public OssClient ossClient() {
        return new OssClient();
    }
}
```

### 超级响应类

`com.chocoh.ql.common.model.Response`

+ 类名调用直接响应基本的 {msg, code, data} 结构。
+ 继承 HashMap ，内置了 DataMap 内部类，方便链式调用生成各种复杂的响应结构。

#### 常用结构

```java
return Response.success(new ArrayList<>());
```
```json
{
        "msg":"操作成功",
        "code":200,
        "data":[]
}
```
#### 通过链式调用实现各种复杂结构

```java
Response response = Response.dataMap()
        .put("1", "参数一")
        .put("2", "参数二")
        .getMap("3")
        .put("3.1", System.currentTimeMillis())
        .put("3.2", new Random().nextInt(8))
        .getMap("3.3")
        .put("4.1", Constants.HEADER_TOKEN)
        .put("4.2", new User(1L, "chocoh", "123123", "admin"))
        .put("4.3", new Response.DataMap().put("5.1", true).put("5.2", ""))
        .ok()
        .put("其他外层参数", "...");
QlApplicationTest.printJson(response);
```

```json
{
  "msg":"操作成功",
  "其他外层参数":"...",
  "code":200,
  "data":{
    "1":"参数一",
    "2":"参数二",
    "3":{
      "3.1":1703938143220,
      "3.2":5,
      "3.3":{
        "4.1":"Authorization",
        "4.2":{
          "password":"123123",
          "role":"admin",
          "id":1,
          "username":"chocoh"
        },
        "4.3":{
          "5.1":true,
          "5.2":""
        }
      }
    }
  }
}
```

#### 其他结构

如果不想使用 {msg, code, data} 结构，可以直接使用无参构造创建一个空 Response 对象，通过链式调用定制内容。

