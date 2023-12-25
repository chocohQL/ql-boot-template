# ql-boot-template

> 失手扑空蓝色的蜻蛉
> 
> Author: [chocohQL](https://github.com/chocohQL)
> 
> GitHub: [https://github.com/chocohQL/ql-boot-template](https://github.com/chocohQL/ql-boot-template)

## 项目介绍

ql-boot-template 是一个简洁且优雅的后台管理系统模板项目，聚焦于最通用常用的核心代码，即删即用。

## 技术选型

+ JDK 8
+ SpringBoot 2.5.15
+ SpringSecurity 2.5.15
+ MyBatisPlus 3.5.3.1
+ MySQL 8.0.31
+ Redis 7.0.12

## 项目结构

```
ql-boot-template  
├── ql-common                       // 通用模块
│       └── annotation                  // 注解
│       └── constant                    // 常量
│       └── exception                   // 异常类
│       └── pojo                        // 实体类
│       └── utils                       // 工具类
├── ql-framework                    // 核心模块
│       └── accept                      // 切面
│       └── config                      // 全局配置
│       └── controller                  // 控制层
│       └── exception                   // 异常处理
│       └── mapper                      // 持久层
│       └── service                     // 业务层
├── ql-security                     // 安全模块
│       └── filter                      // 过滤器
│       └── handler                     // 处理器
│       └── service                     // 认证业务
├── ql-vue-template                 // 前端模板
```

## 快速开始

### 按需创建数据库表

`sql/ql_boot_template.sql` 

创建数据库执行 sql 文件，将数据库名替换为你自己的数据库名。数据库仅会生成一个 user 表，对应 User 类。可在其基础上扩充字段。

```java
public class User {
    private Long id;
    private String username;
    private String password;
    private String role;
}
```

### 修改application.yml文件

`ql-framework/src/main/resources/application.yml`

修改数据 MySQL 和 Redis 配置

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

### 修改客户端配置

修改 OSS 和 Email 配置。

```yaml
email:
  host: smtp.qq.com
  from: change@qq.com
  password: change
  type: text/html;charset=UTF-8
  username: change@qq.com

oss:
  endpoint: change
  url: change
  bucket-name: chocoh
  access-key-id: change
  access-key-secret: change
  file-dir: ql/avatar/
```

不需要使用 OSS 或 Email 则可删除配置，同时删除 ClientConfig 中配置的bean。

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

### 测试启动

启动项目，默认实现了登录和退出登录接口。

### 启动前端（可选）

进入 ql-vue-template 前端模板项目，安装依赖并启动。

```shell
npm install
```
```shell
npm run dev
```

### 后续开发

+ 按需修改项目名、模块名、包名等信息
+ 删除一些不需要的工具类等
+ 在 ql-framework 模块中编写业务，在 ql-common 模块中编写实体类和工具类等。

## 了解更多

## 超级响应类

`com.chocoh.ql.common.pojo.model.Response`

+ 类名调用直接响应基本的 {msg, code, data} 结构。
+ 继承至 HashMap，内置了 DataMap 内部类，方便链式调用实现各种复杂的响应结构。

### 常用结构

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
### 通过链式调用实现各种复杂结构

```java
Response ok = Response.dataMap()
        .put("1", "参数一")
        .put("2", "参数二")
        .getMap("3")
        .put("3.1", System.currentTimeMillis())
        .put("3.2", new Random().nextInt(8))
        .getMap("3.3")
        .put("3.3.1", Constants.HEADER_TOKEN)
        .put("3.3.2", new User(1L, "chocoh", "123123", "admin"))
        .ok()
        .put("其他外层参数", "...");
JsonPrinter.printJson(ok);
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
			"3.1":1703315612421,
			"3.2":1,
			"3.3":{
				"3.3.2":{
					"password":"123123",
					"role":"admin",
					"id":1,
					"username":"chocoh"
				},
				"3.3.1":"Authorization"
			}
		}
	}
}
```

### 其他结构

如果不想使用经典的 {msg, code, data} 结构，那么可以直接使用无参构造创建一个空 Response 类再链式调用构造内容。

## 授权认证

...
