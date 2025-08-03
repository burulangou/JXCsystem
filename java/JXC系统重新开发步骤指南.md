# JXC进销存系统重新开发步骤指南

## 项目概述

本指南详细描述了如何从零开始重新创建一个完整的JXC进销存管理系统，采用SpringBoot + MyBatis Plus + Redis技术栈。

## 开发阶段规划

### 第一阶段：项目基础搭建 (1-2天)

#### 1.1 创建SpringBoot项目
- 使用Spring Initializr创建新项目
- 或者手动创建Maven项目结构
- 确定项目包名和基础配置

#### 1.2 配置项目依赖
- 添加Spring Boot Web启动器
- 添加Spring Boot AOP启动器
- 添加Spring Boot Data Redis启动器  
- 添加MySQL连接器
- 添加MyBatis Plus启动器
- 添加PageHelper分页插件
- 添加Lombok工具类
- 添加FastJSON JSON处理库
- 添加EasyExcel Excel处理库
- 添加Knife4j API文档工具
- 添加Swagger注解库

#### 1.3 配置应用配置文件
- 配置服务器端口
- 配置数据库连接信息
- 配置Redis连接信息
- 配置MyBatis Plus设置
- 配置PageHelper分页设置
- 配置文件上传大小限制
- 配置AOP代理设置

#### 1.4 创建启动类
- 添加@SpringBootApplication注解
- 添加@EnableScheduling注解启用定时任务
- 添加@MapperScan注解扫描Mapper接口
- 创建main方法启动应用

### 第二阶段：核心基础设施 (2-3天)

#### 2.1 创建常量类
- 创建SessionConstant会话常量类
- 创建MsgConstant消息常量类
- 创建DocConstant文档常量类
- 创建SocketConstant WebSocket常量类

#### 2.2 创建枚举类
- 创建GeneralStatusEnum通用状态枚举
- 创建DocStatusEnum文档状态枚举
- 创建DocFinishEnum文档完成状态枚举
- 创建DocHistoryEnum文档历史枚举
- 创建ResourceTypeEnum资源类型枚举
- 创建UserActionEnum用户操作枚举
- 创建DataScopeEnum数据范围枚举
- 创建RecLoginHistoryEnum登录历史枚举

#### 2.3 创建统一响应格式
- 创建R<T>统一响应类
- 实现success()成功响应方法
- 实现fail()失败响应方法
- 实现requireLogin()需要登录响应方法
- 实现noPermission()无权限响应方法

#### 2.4 创建核心工具类
- 创建SessionUtil会话工具类
- 创建ThreadUtil线程工具类
- 创建IpUtil IP地址工具类
- 创建WebUtil Web工具类
- 创建DateUtil日期工具类
- 创建Util通用工具类

### 第三阶段：配置类 (1天)

#### 3.1 创建Web配置类
- 配置FastJSON消息转换器
- 移除默认的Jackson转换器
- 配置Swagger API文档
- 配置拦截器注册
- 配置跨域处理

#### 3.2 创建Redis配置类
- 配置RedisTemplate序列化方式
- 配置Key序列化器为StringRedisSerializer
- 配置Value序列化器为GenericJackson2JsonRedisSerializer
- 配置HashKey和HashValue序列化器

#### 3.3 创建其他配置类
- 创建ExecutorConfig线程池配置
- 创建FastJsonConfigFactory FastJSON配置工厂
- 创建SocketConfig WebSocket配置
- 创建AliyunConfig阿里云配置

### 第四阶段：实体类和VO (2-3天)

#### 4.1 创建系统管理实体类
- 创建SysUser用户实体类
- 创建SysRole角色实体类
- 创建SysDepartment部门实体类
- 创建SysResource资源实体类
- 创建SysSupplier供应商实体类
- 创建SysCustomer客户实体类
- 创建SysCategory分类实体类
- 创建SysRegion地区实体类

#### 4.2 创建业务实体类
- 创建BizPurchaseOrder采购订单实体类
- 创建BizPurchaseOrderSub采购订单明细实体类
- 创建BizPurchaseInbound采购入库实体类
- 创建BizPurchaseInboundSub采购入库明细实体类
- 创建BizSellOrder销售订单实体类
- 创建BizSellOrderSub销售订单明细实体类
- 创建BizSellOutbound销售出库实体类
- 创建BizSellOutboundSub销售出库明细实体类
- 创建BizStock库存实体类
- 创建BizDoc文档基础实体类
- 创建BizDocSub文档明细基础实体类
- 创建BizDocHistory文档历史实体类

#### 4.3 创建记录实体类
- 创建RecLoginHistory登录历史实体类
- 创建RecUserAction用户操作记录实体类
- 创建RecAttachment附件记录实体类

#### 4.4 创建消息实体类
- 创建Msg消息实体类
- 创建MsgState消息状态实体类

#### 4.5 创建统计实体类
- 创建StatFinishOrder完成订单统计实体类
- 创建StatProfitGoods商品利润统计实体类
- 创建StatProfitTotal总利润统计实体类

#### 4.6 创建VO类
- 创建UserVo用户VO类
- 创建LoginParam登录参数VO类
- 创建RegisterParam注册参数VO类
- 创建PasswordUpdateParam密码更新参数VO类
- 创建LoginSuccessInfo登录成功信息VO类
- 创建DepartmentVo部门VO类
- 创建SupplierVo供应商VO类
- 创建CustomerVo客户VO类
- 创建PurchaseOrderVo采购订单VO类
- 创建SellOrderVo销售订单VO类
- 创建PurchaseInboundVo采购入库VO类
- 创建SellOutboundVo销售出库VO类
- 创建StockSearchResult库存搜索结果VO类
- 创建PageResult分页结果VO类
- 创建RegionValueResult地区值结果VO类

#### 4.7 创建搜索参数类
- 创建BaseSearch基础搜索类
- 创建UserSearch用户搜索类
- 创建RoleSearch角色搜索类
- 创建CategorySearch分类搜索类
- 创建CustomerSearch客户搜索类
- 创建SupplierSearch供应商搜索类
- 创建PurchaseOrderSearch采购订单搜索类
- 创建SellOrderSearch销售订单搜索类
- 创建PurchaseInboundSearch采购入库搜索类
- 创建SellOutboundSearch销售出库搜索类
- 创建StockSearch库存搜索类
- 创建DocSearch文档搜索类
- 创建DocHistorySearch文档历史搜索类
- 创建LoginHistorySearch登录历史搜索类
- 创建UserActionSearch用户操作搜索类
- 创建MsgSearch消息搜索类
- 创建MsgPersonalSearch个人消息搜索类

#### 4.8 创建更新参数类
- 创建DocStatusUpdate文档状态更新类
- 创建UserUpdatePwd用户密码更新类

#### 4.9 创建统计VO类
- 创建FourBlockStat四块统计VO类

#### 4.10 创建导出VO类
- 创建PurchaseOrderExport采购订单导出类
- 创建SellOrderExport销售订单导出类
- 创建PurchaseInboundExport采购入库导出类
- 创建SellOutboundExport销售出库导出类
- 创建StockExport库存导出类

#### 4.11 创建WebSocket VO类
- 创建SocketEventVo WebSocket事件VO类
- 创建SocketOfflineVo WebSocket离线VO类

### 第五阶段：数据访问层 (2-3天)

#### 5.1 创建系统管理Mapper接口
- 创建SysUserMapper用户数据访问接口
- 创建SysRoleMapper角色数据访问接口
- 创建SysDepartmentMapper部门数据访问接口
- 创建SysResourceMapper资源数据访问接口
- 创建SysSupplierMapper供应商数据访问接口
- 创建SysCustomerMapper客户数据访问接口
- 创建SysCategoryMapper分类数据访问接口
- 创建SysRegionMapper地区数据访问接口

#### 5.2 创建业务Mapper接口
- 创建BizPurchaseOrderMapper采购订单数据访问接口
- 创建BizPurchaseOrderSubMapper采购订单明细数据访问接口
- 创建BizPurchaseInboundMapper采购入库数据访问接口
- 创建BizPurchaseInboundSubMapper采购入库明细数据访问接口
- 创建BizSellOrderMapper销售订单数据访问接口
- 创建BizSellOrderSubMapper销售订单明细数据访问接口
- 创建BizSellOutboundMapper销售出库数据访问接口
- 创建BizSellOutboundSubMapper销售出库明细数据访问接口
- 创建BizStockMapper库存数据访问接口
- 创建BizDocHistoryMapper文档历史数据访问接口

#### 5.3 创建记录Mapper接口
- 创建RecLoginHistoryMapper登录历史数据访问接口
- 创建RecUserActionMapper用户操作记录数据访问接口
- 创建RecAttachmentMapper附件记录数据访问接口

#### 5.4 创建消息Mapper接口
- 创建MsgMapper消息数据访问接口
- 创建MsgStateMapper消息状态数据访问接口

#### 5.5 创建统计Mapper接口
- 创建StatisticMapper统计数据访问接口

#### 5.6 创建XML映射文件
- 为每个Mapper接口创建对应的XML映射文件
- 配置基础的CRUD操作SQL
- 配置复杂的查询SQL
- 配置统计查询SQL

### 第六阶段：服务层 (3-4天)

#### 6.1 创建账户服务
- 创建AccountService账户服务类
- 实现用户登录功能
- 实现用户登出功能
- 实现用户注册功能
- 实现密码修改功能
- 实现头像更新功能
- 实现登录名检查功能
- 实现昵称检查功能

#### 6.2 创建系统管理服务
- 创建SysUserService用户管理服务类
- 创建SysRoleService角色管理服务类
- 创建SysDepartmentService部门管理服务类
- 创建SysResourceService资源管理服务类
- 创建SysSupplierService供应商管理服务类
- 创建SysCustomerService客户管理服务类
- 创建SysCategoryService分类管理服务类

#### 6.3 创建业务服务
- 创建BizPurchaseOrderService采购订单服务类
- 创建BizPurchaseInboundService采购入库服务类
- 创建BizSellOrderService销售订单服务类
- 创建BizSellOutboundService销售出库服务类
- 创建BizStockService库存服务类
- 创建BizDocHistoryService文档历史服务类

#### 6.4 创建记录服务
- 创建RecService记录服务类
- 实现登录历史记录功能
- 实现用户操作记录功能
- 实现附件记录功能

#### 6.5 创建消息服务
- 创建MsgService消息服务类
- 创建MsgUserService消息用户服务类
- 实现系统消息功能
- 实现个人消息功能
- 实现消息状态管理功能

#### 6.6 创建统计服务
- 创建StatisticService统计服务类
- 实现销售统计功能
- 实现利润统计功能
- 实现订单统计功能
- 实现库存统计功能

#### 6.7 创建文件服务
- 创建FileService文件服务类
- 实现文件上传功能
- 实现文件下载功能
- 实现文件删除功能
- 实现阿里云OSS集成功能

### 第七阶段：控制器层 (2-3天)

#### 7.1 创建账户控制器
- 创建AccountController账户控制器类
- 实现登录接口
- 实现登出接口
- 实现注册接口
- 实现密码修改接口
- 实现头像更新接口
- 实现密码校验接口
- 实现登录名检查接口
- 实现昵称检查接口

#### 7.2 创建系统管理控制器
- 创建UserController用户管理控制器类
- 创建RoleController角色管理控制器类
- 创建DepartmentController部门管理控制器类
- 创建ResourceController资源管理控制器类
- 创建SupplierController供应商管理控制器类
- 创建CustomerController客户管理控制器类
- 创建CategoryController分类管理控制器类

#### 7.3 创建业务控制器
- 创建PurchaseOrderController采购订单控制器类
- 创建PurchaseInboundController采购入库控制器类
- 创建SellOrderController销售订单控制器类
- 创建SellOutboundController销售出库控制器类
- 创建HistoryController文档历史控制器类

#### 7.4 创建库存控制器
- 创建BizStockController库存控制器类
- 实现库存查询功能
- 实现库存统计功能

#### 7.5 创建消息控制器
- 创建ManageController消息管理控制器类
- 创建UserController消息用户控制器类

#### 7.6 创建统计控制器
- 创建StatisticController统计控制器类
- 实现各种统计接口

#### 7.7 创建文件控制器
- 创建FileController文件控制器类
- 实现文件上传下载接口

#### 7.8 创建记录控制器
- 创建RecController记录控制器类
- 实现登录历史查询接口
- 实现用户操作记录查询接口

### 第八阶段：拦截器和权限控制 (1-2天)

#### 8.1 创建安全拦截器
- 创建SecurityInterceptor安全拦截器类
- 实现用户登录检查功能
- 实现用户权限检查功能
- 实现线程用户信息设置功能
- 实现线程信息清理功能

#### 8.2 创建权限模块
- 创建PermissionModule权限模块类
- 实现权限验证功能
- 实现数据范围控制功能

#### 8.3 创建AOP切面
- 创建UserActionAspect用户操作切面类
- 创建TimeCostAspect时间成本切面类
- 创建LockAspect锁切面类
- 创建RestExceptionAspect异常处理切面类

#### 8.4 创建自定义注解
- 创建UserAction用户操作注解
- 创建TimeCost时间成本注解
- 创建Lock锁注解

### 第九阶段：业务功能实现 (4-5天)

#### 9.1 实现采购管理功能
- 实现采购订单的增删改查功能
- 实现采购订单状态管理功能
- 实现采购入库功能
- 实现采购入库明细管理功能
- 实现采购订单完成功能

#### 9.2 实现销售管理功能
- 实现销售订单的增删改查功能
- 实现销售订单状态管理功能
- 实现销售出库功能
- 实现销售出库明细管理功能
- 实现销售订单完成功能

#### 9.3 实现库存管理功能
- 实现库存查询功能
- 实现库存统计功能
- 实现库存预警功能
- 实现库存盘点功能

#### 9.4 实现消息管理功能
- 实现系统消息发送功能
- 实现个人消息管理功能
- 实现消息状态管理功能
- 实现消息推送功能

#### 9.5 实现统计报表功能
- 实现销售统计报表
- 实现利润统计报表
- 实现订单统计报表
- 实现库存统计报表

### 第十阶段：定时任务和扩展功能 (1-2天)

#### 10.1 创建定时任务
- 创建DocTask文档处理定时任务类
- 创建SessionTask会话管理定时任务类
- 创建StatisticTask统计定时任务类
- 实现过期订单处理功能
- 实现会话清理功能
- 实现统计报表生成功能

#### 10.2 创建WebSocket功能
- 创建WebSocketServer WebSocket服务器类
- 实现实时消息推送功能
- 实现在线用户管理功能
- 实现离线用户处理功能

#### 10.3 创建工具类
- 创建AliOssUtil阿里云OSS工具类
- 创建DocUtil文档工具类
- 创建ExcelUtil Excel工具类
- 创建RedisUtil Redis工具类
- 创建SecurityUtil安全工具类
- 创建SpringUtil Spring工具类
- 创建WebSocketUtil WebSocket工具类

#### 10.4 创建属性配置类
- 创建AliOssProperties阿里云OSS属性配置类
- 创建SocketProperty WebSocket属性配置类

## 实现优先级

### 优先级1 (必须首先实现)
1. **项目基础搭建** - 创建项目、配置依赖、配置文件
2. **核心基础设施** - 常量、枚举、统一响应格式、核心工具类
3. **配置类** - Web配置、Redis配置、其他配置类
4. **基础实体类** - 用户、角色、部门等系统实体类

### 优先级2 (核心功能)
5. **数据访问层** - Mapper接口和XML映射文件
6. **账户服务** - 登录、登出、注册等账户相关功能
7. **用户管理服务** - 用户的CRUD操作
8. **控制器层** - 账户和用户管理控制器
9. **拦截器** - 安全拦截和权限控制

### 优先级3 (业务功能)
10. **业务实体类** - 采购、销售、库存等业务实体类
11. **业务服务层** - 采购、销售、库存管理服务
12. **业务控制器** - 采购、销售、库存管理控制器

### 优先级4 (扩展功能)
13. **定时任务** - 自动处理任务和统计任务
14. **统计功能** - 数据统计和报表功能
15. **消息管理** - 系统消息和个人消息功能
16. **文件管理** - 文件上传下载功能
17. **WebSocket** - 实时通信功能

## 开发建议

### 1. 循序渐进
- 按照优先级顺序逐步实现
- 确保每个阶段的功能都完整可用
- 不要跳过基础功能直接实现复杂业务

### 2. 测试驱动
- 每个功能实现后都要进行充分测试
- 编写单元测试和集成测试
- 确保功能的正确性和稳定性

### 3. 代码规范
- 保持代码风格一致
- 添加必要的注释和文档
- 遵循命名规范和代码结构

### 4. 文档同步
- 及时更新API文档
- 维护开发文档和用户手册
- 记录重要的设计决策

### 5. 版本控制
- 使用Git进行版本管理
- 每个功能完成后提交代码
- 创建有意义的提交信息

### 6. 性能优化
- 注意数据库查询性能
- 合理使用缓存机制
- 优化大数据的处理逻辑

### 7. 安全考虑
- 实现完善的权限控制
- 防止SQL注入和XSS攻击
- 保护敏感数据的安全

## 预计开发时间

- **第一阶段**: 1-2天
- **第二阶段**: 2-3天
- **第三阶段**: 1天
- **第四阶段**: 2-3天
- **第五阶段**: 2-3天
- **第六阶段**: 3-4天
- **第七阶段**: 2-3天
- **第八阶段**: 1-2天
- **第九阶段**: 4-5天
- **第十阶段**: 1-2天

**总计**: 约20-30天

## 总结

本指南提供了一个完整的JXC进销存系统重新开发路线图，按照这个步骤可以系统性地构建一个功能完整的企业级管理系统。每个阶段都有明确的目标和交付物，确保开发过程的有序进行。 