# ql-boot-template

> 失手扑空蓝色的蜻蛉
> 
> Author: [chocohQL](https://github.com/chocohQL)
> 
> GitHub: [https://github.com/chocohQL/ql-boot-template](https://github.com/chocohQL/ql-boot-template)

## 项目介绍

ql-boot-template 是一个多模块SpringBoot模板项目，已配置好基本框架和通用代码，用于快速搭建一个中小型的后端项目。

## 项目架构

![](https://fastly.jsdelivr.net/gh/chocohQL/ql-file@main/assets/github/ql-boot-template-%E6%9E%B6%E6%9E%84.svg)

项目主要模块:
+ 核心模块(ql-framework)：编写核心业务、全局配置等。
+ 数据模块(ql-dal)：定义实体类和数据库映射。
+ 安全模块(ql-security)：授权认证相关代码。
+ 通用模块(ql-common)：工具类、常量类等通用代码。

授权认证流程：

![](https://fastly.jsdelivr.net/gh/chocohQL/ql-file@main/assets/github/ql-security.svg)