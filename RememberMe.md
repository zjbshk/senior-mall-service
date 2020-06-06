#### 后端服务器
属性|规格
:-:|:-:
系统|Centos（版本7以上） 64位
运行内存| 4G（或以上）
存储空间| 40G（或以上）
公网IP| 有
带宽| 10M(以上)

#### 其他参考文档
[JAVA开发规范整理篇](https://www.jianshu.com/p/857a880efe0a)
[项目开发用到的工具或平台](https://www.jianshu.com/p/402560f11b79)

#### 结对编程的思想
为了保证开发产品的质量，项目采用两人结对编程思想。
>结对编程互相补充自身的不足，每个人对编程都有自己的理解，有着自己的思路以及风格有时自己编程显得非常冗余啰嗦，两人可以相互借鉴，在与人讲解自己的代码思路的时候也别有一番体会，对两个编程能力差不多的人来说真的收获很大。

> 同时开发和测试形成对抗体系

#### 后端技术架构
```
- 基础框架：Spring Boot 

- 持久层框架：Mybatis-plus

- 安全框架：Apache Shiro ，Jwt 

- 数据库连接池：阿里巴巴Druid

- 缓存框架：redis

- 搜索引擎：elasticsearch

- 日志打印：logback

- 其他：fastjson，quartz, lombok（简化代码）等。
```
#### 项目结构
###### 清晰明确的项目结构可防止团队内部编写代码逻辑混乱、重复造轮子等。
```
src                               源码目录
|-- common                            各个项目的通用类库
|-- config                            项目的配置信息
|-- constant                          全局公共常量
|-- handler                           全局处理器
|-- interceptor                       全局拦截器
|-- listener                          全局监听器
|-- modules                           各个业务
|-- |--- user                             用户模块
|-- |--- merchant                         商家模块
|-- |--- login                            登录模块
|-- third                             三方服务，比如redis, oss，微信sdk等等
|-- util                              全局工具类
|-- Application.java                  启动类


------------------------------------------
|-- common 各个项目的通用类库
|-- |--- anno          通用注解，比如权限，登录等等
|-- |--- constant      通用常量，比如 ResponseCodeConst
|-- |--- domain        全局的 javabean，比如 BaseEntity,PageParamDTO 等
|-- |--- exception     全局异常，如 BusinessException
|-- |--- json          json 类库，如 LongJsonDeserializer，LongJsonSerializer
|-- |--- validator     适合各个项目的通用 validator，如 CheckEnum，CheckBigDecimal 等


------------------------------------------
config 结构如下：
|-- config                            项目的所有配置信息
|-- |--- MvcConfig                    mvc的相关配置，如interceptor,filter等
|-- |--- DataSourceConfig             数据库连接池的配置
|-- |--- MybatisConfig                mybatis的配置
|-- |--- ....                         其他


------------------------------------------
constant 结构如下：
|-- constant              常量


------------------------------------------
模块下结构如下：
|-- user
|-- |--- domain           领域类
|-- |--- |--- dto           数据传输对象(Data Transfer Object)
|-- |--- |--- vo            返回值，用于定义接口的返回数据格式
|-- |--- |--- entity        实体类，用于永久化或是封装查询结果
|-- |--- constant         常量
|-- |--- api              api接口（control）
|-- |--- service          服务层
|-- |--- mapper           mapper层
|-- |--- dao              dao层
```

#### 整体架构 `Controller`,`Service`,`Mapper` ,`DAO`
整体架构并不是`Controller`,`Service`,`DAO`三层，因为这样我们为了不将压力压到数据库上，那我们必须将逻辑提到`Service`层中，这会导致`Service`层会非常厚，不宜维护。所以这里增加`Mapper`层。  

**`Mapper` 层的作用如下：**
- 对第三方平台封装的层，预处理返回结果及转化异常信息；
- 对 `Service` 层通用能力的下沉，如缓存方案、中间件通用处理；
- 与 `DAO` 层交互，对多个`DAO` 的组合复用。

#### 代码提交规范
- 提交前应该冷静、仔细检查一下，确保没有忘记加入版本控制或不应该提交的文件。
- 提交前应该先编译一次，防止出现编译都报错的情况。
- 提交前先更新pull一次代码，提交前发生冲突要比提交后发生冲突容易解决的多。
- 提交前检查代码是否格式化，是否符合代码规范，无用的包引入、变量是否清除等等。
- 提交时检查注释是否准确简洁的表达出了本次提交的内容。