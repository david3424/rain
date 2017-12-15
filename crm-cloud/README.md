crm-cloud
====

##普通目录:
  docs: 存放文档资料, 例如数据库脚本等.
##公共模块:
+  apis: api模块公共父模块.
+  common: 服务模块公共父模块, 存放微服务共同依赖的逻辑, 例如事件处理, 定时任务等.
+  utils: 工具类模块.
##基础服务模块:
+  eureka: eureka服务. 提供服务注册与服务发现. 
+  config: config服务. 提供配置管理服务. 
##服务模块:
+  zuul: 网关(路由).
+  user: 用户入口.
     - api: api接口模块. 其他依赖account服务的服务会依赖这个模块.
     - core: account服务实现模块.
     - api和core模块内容都是标准的maven项目结构, 其中core模块主要有这么一些子目录:
     - context: 存放Spring Boot启动类.
     - dao: DAO层.
     - domain: Model层. 
     - service: Service层.
     - web: 存放Spring MVC Controller.
+  account: 账户服务.
+  order: 订单服务.
+  product: 产品服务.
