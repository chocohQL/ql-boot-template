# ql-boot-template

> 失手扑空蓝色的蜻蛉
> 
> GitHub: [chocohQL](https://github.com/chocohQL)

## 项目介绍

ql-boot-template 是一个简洁且优雅的后台管理系统模板项目，即删即用。

部分代码借鉴了若依的写法，但相比之下做了很多减法，聚焦于最通用常用的核心代码。

## 项目技术

- SpringBoot
- SpringSecurity
- MyBatis-Plus
- Redis

## 快速开始

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

项目有独立的认证授权模块，通过对 SpringSecurtiy 的二次封装，用尽量少的代码实现最常用的功能且便于扩展。

### 基本实现

### 扩展逻辑