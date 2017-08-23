rain
====

all gains in one



maven 架构
rain 为主项目，包括parent模块，parent是所有子项目的父模块，
jar包引入，插件等都在这里，还可以定义dependentmanager，方便子项目调用。

common 模块，parent的子模块，是项目基础代码放置的地方。主要包括
组件包，里面有服务器xml解析和组件异常
实体类entity，数据连接层repository，hdtable注解，用dbutils组件操作mysql数据库
lang包，search包主要是分页以及tuple类，用于传递2种不同的数据类型。
servlet是验证码
util工具包，包括memcached、ip获取，服务器列表、时间操作，

====

bhhd模块
后端：
1.spring security
2.oracle转mysql
3.spring jdbctemplate
4.java-mail
5.freemarker
前端：
1.bootstrap2.2
2.jquery 分页插件
3.json数据传输
架构
1.单点登录
2.maven插件
3.log4j

====

activity模块 spring相关内容以及java基础

1. Spring相关
1.1 Spring Ioc 装配3总方式
单元测试
    测试数据库连接 dbutils/mybatis/jdbctemplate
    测试service层业务 事务管理

    测试javabase的一些东西
    加载顺序
    集合的特性

====

dubbox模块 引入dubbo功能

基础pom，加入所有依赖管理与插件管理
3个子模块
api，主要提供服务的接口文件
provider 生产者，这里用spring-mvc实现，应用服务器为tomcat
数据源bonecp，持久化可选择mybatis、springjdbctemplate、JPA+hibernate
consumer 消费者，架构一致（用于测试，实际中可为任意形式）

