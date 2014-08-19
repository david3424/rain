rain
====

all gains in one

==================================
maven 架构
rain 为主项目，包括parent模块，parent是所有子项目的父模块，
jar包引入，插件等都在这里，还可以定义dependentmanager，方便子项目调用。

common 模块，parent的子模块，是项目基础代码放置的地方。主要包括
组件包，里面有服务器xml解析和组件异常
实体类entity，数据连接层repository，hdtable注解，用dbutils组件操作mysql数据库
lang包，search包主要是分页以及tuple类，用于传递2种不同的数据类型。
servlet是验证码
util工具包，包括memcached、ip获取，服务器列表、时间操作，

