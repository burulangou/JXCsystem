# JXC进销存系统开发文档

## 目录
- [项目概述](#项目概述)
- [技术栈](#技术栈)
- [项目结构](#项目结构)
- [核心配置](#核心配置)
- [控制器层](#控制器层)
- [服务层](#服务层)
- [拦截器](#拦截器)
- [工具类](#工具类)

## 项目概述

JXC进销存系统是一个基于Spring Boot的企业级管理系统，提供完整的进销存业务流程管理功能。

### 主要功能模块
- **账户管理**: 用户登录、注册、密码修改、头像更新
- **系统管理**: 用户、角色、部门、资源、供应商、客户管理
- **采购管理**: 采购订单、采购入库管理
- **销售管理**: 销售订单、销售出库管理
- **库存管理**: 库存查询、库存统计
- **消息管理**: 系统消息、个人消息
- **统计报表**: 销售统计、利润统计、订单统计

## 技术栈

### 核心框架
- **Spring Boot 2.2.7**: 主框架
- **MyBatis Plus 3.3.2**: ORM框架
- **Redis**: 缓存和会话管理
- **MySQL**: 数据库

### 开发工具
- **Lombok**: 代码简化
- **Swagger/Knife4j**: API文档
- **FastJSON**: JSON处理
- **EasyExcel**: Excel导入导出
- **Netty SocketIO**: WebSocket通信

## 项目结构

```
java/
├── src/main/java/cn/toesbieya/jxc/
│   ├── annoation/          # 自定义注解
│   ├── aspect/             # AOP切面
│   ├── config/             # 配置类
│   ├── constant/           # 常量定义
│   ├── controller/         # 控制器层
│   ├── enumeration/        # 枚举类
│   ├── exception/          # 异常处理
│   ├── interceptor/        # 拦截器
│   ├── mapper/             # 数据访问层
│   ├── model/              # 实体类和VO
│   ├── module/             # 核心模块
│   ├── properties/         # 属性配置
│   ├── schedule/           # 定时任务
│   ├── service/            # 服务层
│   ├── util/               # 工具类
│   └── MyApplication.java  # 启动类
```

## 核心配置

### 启动类配置

```java
@EnableScheduling
@MapperScan("cn.toesbieya.jxc.mapper")
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

### Web配置类

```java
@Configuration
@EnableSwagger2
public class WebConfig implements WebMvcConfigurer {
    
    // FastJSON消息转换器配置
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 移除Jackson转换器
        for (int i = converters.size() - 1; i >= 0; i--) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                converters.remove(i);
            }
        }

        // 配置FastJSON转换器
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setFastJsonConfig(FastJsonConfigFactory.defaultConfig());

        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);

        converters.add(fastJsonHttpMessageConverter);
    }

    // Swagger API文档配置
    @Bean
    public Docket docket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("进销存系统项目接口文档")
                .version("2.0")
                .description("进销存系统项目接口文档")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.toesbieya.jxc.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    // 拦截器配置
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] exclude = {
            "/test/**", 
            "/account/login", 
            "/account/logout", 
            "/account/register", 
            "/account/checkLoginName", 
            "/account/checkNickName", 
            "/error",
            "/doc.html",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/api-docs",
            "/v2/api-docs-ext",
            "/swagger-ui/**",
            "/doc.html/**",
            "/favicon.ico"
        };
        addInterceptor(registry, new SecurityInterceptor(), exclude);
    }
}
```

## 控制器层

## 账户控制器 (AccountController)

```java
@RestController
@RequestMapping("account")
@Api(tags = "账户管理")
public class AccountController {
    
    @Resource
    private AccountService service;

    @PostMapping("login")
    @ApiOperation("用户登录")
    public R login(HttpServletRequest request, @Valid @RequestBody LoginParam param) {
        return service.login(param, IpUtil.getIp(request));
    }

    @GetMapping("logout")
    @ApiOperation("用户登出")
    public R logout(HttpServletRequest request) {
        UserVo user = SessionUtil.get(request);
        return service.logout(user, IpUtil.getIp(request));
    }

    @PostMapping("register")
    @ApiOperation("用户注册")
    public R register(@Valid @RequestBody RegisterParam param) {
        return service.register(param);
    }

    @PostMapping("updatePwd")
    @ApiOperation("修改密码")
    public R updatePwd(@RequestBody PasswordUpdateParam param) {
        UserVo user = SessionUtil.get();
        param.setId(user.getId());
        String errMsg = validateUpdatePwdParam(param);
        if (errMsg != null) return R.fail(errMsg);
        return service.updatePwd(param);
    }

    @GetMapping("updateAvatar")
    @ApiOperation("更新头像")
    public R updateAvatar(@RequestParam String key) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(key)) return R.fail("参数错误");
        return service.updateAvatar(SessionUtil.get(), URLDecoder.decode(key, "utf-8"));
    }

    @GetMapping("validate")
    @ApiOperation("密码校验")
    public R validate(@RequestParam String pwd) {
        UserVo current = SessionUtil.get();
        if (!pwd.equals(current.getPwd())) {
            return R.fail("校验失败");
        }
        return R.success("校验通过");
    }

    @GetMapping("checkLoginName")
    @ApiOperation("检查登录名是否存在")
    public R checkLoginName(@RequestParam(required = false) Integer id, @RequestParam String name) {
        if (StringUtils.isEmpty(name)) {
            return R.success();
        }
        return R.success(service.isLoginNameExist(name, id) ? "该登录名已存在" : null);
    }

    @GetMapping("checkNickName")
    @ApiOperation("检查昵称是否存在")
    public R checkNickName(@RequestParam(required = false) Integer id, @RequestParam String name) {
        if (StringUtils.isEmpty(name)) {
            return R.success();
        }
        return R.success(service.isNickNameExist(name, id) ? "该昵称已存在" : null);
    }
}
```

### 系统管理控制器

#### 用户管理控制器 (SysUserController)
```java
@RestController
@RequestMapping("sys/user")
@Api(tags = "用户管理")
public class UserController {
    
    @Resource
    private SysUserService service;

    @GetMapping("list")
    @ApiOperation("用户列表")
    public R list(UserSearch search) {
        return R.success(service.getList(search));
    }

    @PostMapping("add")
    @ApiOperation("添加用户")
    public R add(@Valid @RequestBody UserVo vo) {
        return service.add(vo);
    }

    @PostMapping("update")
    @ApiOperation("更新用户")
    public R update(@Valid @RequestBody UserVo vo) {
        return service.update(vo);
    }

    @PostMapping("delete")
    @ApiOperation("删除用户")
    public R delete(@RequestBody Integer id) {
        return service.delete(id);
    }
}
```

### 业务控制器

#### 采购订单控制器 (BizPurchaseOrderController)
```java
@RestController
@RequestMapping("doc/purchaseOrder")
@Api(tags = "采购订单")
public class PurchaseOrderController {
    
    @Resource
    private BizPurchaseOrderService service;

    @GetMapping("list")
    @ApiOperation("采购订单列表")
    public R list(PurchaseOrderSearch search) {
        return R.success(service.getList(search));
    }

    @PostMapping("add")
    @ApiOperation("添加采购订单")
    public R add(@Valid @RequestBody PurchaseOrderVo vo) {
        return service.add(vo);
    }

    @PostMapping("update")
    @ApiOperation("更新采购订单")
    public R update(@Valid @RequestBody PurchaseOrderVo vo) {
        return service.update(vo);
    }

    @PostMapping("delete")
    @ApiOperation("删除采购订单")
    public R delete(@RequestBody Integer id) {
        return service.delete(id);
    }

    @PostMapping("finish")
    @ApiOperation("完成采购订单")
    public R finish(@RequestBody DocStatusUpdate vo) {
        return service.finish(vo);
    }
}
```

## 服务层

### 账户服务 (AccountService)

```java
@Service
public class AccountService {
    
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysResourceService resourceService;
    @Resource
    private RecService recService;
    @Resource
    private SysDepartmentService departmentService;
    @Resource
    private SysRoleService roleService;
    @Resource
    private FileService fileService;

    @TimeCost
    public R login(LoginParam param, String ip) {
        long now = System.currentTimeMillis();

        // 验证用户登录
        SysUser user = userMapper.selectOne(
                Wrappers.lambdaQuery(SysUser.class)
                        .eq(SysUser::getLoginName, param.getUsername())
                        .eq(SysUser::getPwd, param.getPassword())
        );

        if (user == null) {
            return R.fail("用户名或密码错误");
        }
        if (!user.isEnable()) {
            return R.fail("该用户已被禁用，请联系管理员");
        }
        Integer roleId = user.getRole();
        if (!user.isAdmin() && roleId == null) {
            return R.fail("该用户尚未被分配角色，请联系管理员");
        }

        // 生成token
        String token = SessionUtil.generateToken(user);

        // 构建用户信息
        UserVo userVo = new UserVo(user);
        userVo.setToken(token);

        LoginSuccessInfo info = new LoginSuccessInfo(user);
        info.setToken(token);

        // 设置用户权限
        Map<String, Integer> userResources = null;
        Set<Integer> userResourcesIdSet = null;

        if (!user.isAdmin()) {
            SysRole role = roleService.getRoleById(roleId);
            if (role != null) {
                String roleName = role.getName();
                userVo.setRoleName(roleName);
                info.setRoleName(roleName);

                List<SysResource> resources = resourceService.getResourceByRole(role);
                userResources = new HashMap<>(resources.size());
                userResourcesIdSet = new HashSet<>(resources.size());

                for (SysResource r : resources) {
                    if (r.isAdmin()) continue;
                    Integer resourceId = r.getId();
                    userResources.put(r.getPath(), r.getId());
                    if (r.getType().equals(ResourceTypeEnum.API.getCode())) {
                        userResourcesIdSet.add(resourceId);
                    }
                }
            }
        }

        // 设置部门信息
        Integer deptId = user.getDept();
        if (deptId != null) {
            DepartmentVo dept = departmentService.getById(deptId);
            if (dept != null) {
                String deptName = dept.getFullname();
                userVo.setDeptName(deptName);
                info.setDeptName(deptName);
            }
        }

        // 设置数据范围
        userVo.setDepartmentIds(departmentService.getUserDataScope(user));
        userVo.setResourceIds(userResourcesIdSet);
        info.setResources(userResources);

        // 保存用户信息到Redis
        SessionUtil.save(userVo);

        // 记录登录历史
        recService.insertLoginHistory(
                RecLoginHistory
                        .builder()
                        .uid(user.getId())
                        .uname(user.getNickName())
                        .ip(ip)
                        .login(true)
                        .time(now)
                        .build()
        );

        return R.success(info);
    }

    public R logout(UserVo user, String ip) {
        if (user != null) {
            recService.insertLoginHistory(
                    RecLoginHistory
                            .builder()
                            .uid(user.getId())
                            .uname(user.getNickName())
                            .ip(ip)
                            .login(false)
                            .time(System.currentTimeMillis())
                            .build()
            );
            SessionUtil.remove(user.getToken());
        }
        return R.success("登出成功");
    }

    @UserAction("'修改密码'")
    public R updatePwd(PasswordUpdateParam param) {
        int rows = userMapper.update(
                null,
                Wrappers.lambdaUpdate(SysUser.class)
                        .set(SysUser::getPwd, param.getNewPwd())
                        .eq(SysUser::getId, param.getId())
                        .eq(SysUser::getPwd, param.getOldPwd())
        );
        return rows > 0 ? R.success("修改成功") : R.fail("修改失败，请检查原密码是否正确");
    }

    @UserAction("'修改头像'")
    public R updateAvatar(UserVo user, String avatar) {
        int rows = userMapper.update(
                null,
                Wrappers.lambdaUpdate(SysUser.class)
                        .set(SysUser::getAvatar, avatar)
                        .eq(SysUser::getId, user.getId())
        );

        if (rows > 0) {
            String oldAvatar = user.getAvatar();
            if (!StringUtils.isEmpty(oldAvatar)) {
                fileService.delete(oldAvatar);
            }
            user.setAvatar(avatar);
            SessionUtil.save(user);
            return R.success("上传头像成功", avatar);
        }

        fileService.delete(avatar);
        return R.fail("上传头像失败");
    }
}
```

## 拦截器

### 安全拦截器 (SecurityInterceptor)

```java
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getServletPath();
        UserVo user = SessionUtil.get(request);

        // 检查用户是否登录
        if (user == null) {
            WebUtil.responseJson(response, R.requireLogin());
            ThreadUtil.clearAll();
            return false;
        }

        // 检查用户权限
        if (!PermissionModule.authority(user, url)) {
            WebUtil.responseJson(response, R.noPermission());
            log.warn("权限拦截，访问路径：{}，用户：{}", url, user.getNickName());
            ThreadUtil.clearAll();
            return false;
        }

        // 设置线程用户信息
        ThreadUtil.setUser(user);
        ThreadUtil.quicklySetAction(request);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理线程信息
        ThreadUtil.clearAll();
    }
}
```

## 工具类

### 会话工具类 (SessionUtil)

```java
@Component
public class SessionUtil {
    
    private static final String TOKEN_PREFIX = "user:";
    private static final int TOKEN_EXPIRE = 7200; // 2小时
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    
    /**
     * 生成token
     */
    public static String generateToken(SysUser user) {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 保存用户信息到Redis
     */
    public void save(UserVo user) {
        String key = TOKEN_PREFIX + user.getToken();
        redisTemplate.opsForValue().set(key, user, TOKEN_EXPIRE, TimeUnit.SECONDS);
    }
    
    /**
     * 从Redis获取用户信息
     */
    public UserVo get(String token) {
        String key = TOKEN_PREFIX + token;
        return (UserVo) redisTemplate.opsForValue().get(key);
    }
    
    /**
     * 从请求中获取用户信息
     */
    public static UserVo get(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return SpringUtil.getBean(SessionUtil.class).get(token);
    }
    
    /**
     * 从当前线程获取用户信息
     */
    public static UserVo get() {
        return ThreadUtil.getUser();
    }
    
    /**
     * 删除用户信息
     */
    public void remove(String token) {
        String key = TOKEN_PREFIX + token;
        redisTemplate.delete(key);
    }
}
```

### IP工具类 (IpUtil)

```java
public class IpUtil {
    
    /**
     * 获取客户端IP地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
```

### 线程工具类 (ThreadUtil)

```java
public class ThreadUtil {
    
    private static final ThreadLocal<UserVo> USER_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<String> ACTION_THREAD_LOCAL = new ThreadLocal<>();
    
    /**
     * 设置用户信息到线程
     */
    public static void setUser(UserVo user) {
        USER_THREAD_LOCAL.set(user);
    }
    
    /**
     * 获取线程中的用户信息
     */
    public static UserVo getUser() {
        return USER_THREAD_LOCAL.get();
    }
    
    /**
     * 设置操作信息到线程
     */
    public static void setAction(String action) {
        ACTION_THREAD_LOCAL.set(action);
    }
    
    /**
     * 获取线程中的操作信息
     */
    public static String getAction() {
        return ACTION_THREAD_LOCAL.get();
    }
    
    /**
     * 快速设置操作信息
     */
    public static void quicklySetAction(HttpServletRequest request) {
        String url = request.getServletPath();
        String method = request.getMethod();
        setAction(method + " " + url);
    }
    
    /**
     * 清理所有线程信息
     */
    public static void clearAll() {
        USER_THREAD_LOCAL.remove();
        ACTION_THREAD_LOCAL.remove();
    }
}
```

## 统一响应格式

```java
@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;
    
    public static <T> R<T> success() {
        return success(null);
    }
    
    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.setCode(200);
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }
    
    public static <T> R<T> success(String msg, T data) {
        R<T> r = new R<>();
        r.setCode(200);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
    
    public static <T> R<T> fail(String msg) {
        R<T> r = new R<>();
        r.setCode(500);
        r.setMsg(msg);
        return r;
    }
    
    public static <T> R<T> requireLogin() {
        R<T> r = new R<>();
        r.setCode(401);
        r.setMsg("请先登录");
        return r;
    }
    
    public static <T> R<T> noPermission() {
        R<T> r = new R<>();
        r.setCode(403);
        r.setMsg("权限不足");
        return r;
    }
}
```

## 主要API接口

### 账户管理接口
- `POST /account/login` - 用户登录
- `GET /account/logout` - 用户登出
- `POST /account/register` - 用户注册
- `POST /account/updatePwd` - 修改密码
- `GET /account/updateAvatar` - 更新头像
- `GET /account/validate` - 密码校验
- `GET /account/checkLoginName` - 检查登录名
- `GET /account/checkNickName` - 检查昵称

### 系统管理接口
- `GET /sys/user/list` - 用户列表
- `POST /sys/user/add` - 添加用户
- `POST /sys/user/update` - 更新用户
- `POST /sys/user/delete` - 删除用户
- `GET /sys/role/list` - 角色列表
- `POST /sys/role/add` - 添加角色
- `POST /sys/role/update` - 更新角色
- `POST /sys/role/delete` - 删除角色

### 业务管理接口
- `GET /doc/purchaseOrder/list` - 采购订单列表
- `POST /doc/purchaseOrder/add` - 添加采购订单
- `POST /doc/purchaseOrder/update` - 更新采购订单
- `POST /doc/purchaseOrder/delete` - 删除采购订单
- `POST /doc/purchaseOrder/finish` - 完成采购订单
- `GET /doc/sellOrder/list` - 销售订单列表
- `POST /doc/sellOrder/add` - 添加销售订单
- `POST /doc/sellOrder/update` - 更新销售订单
- `POST /doc/sellOrder/delete` - 删除销售订单
- `POST /doc/sellOrder/finish` - 完成销售订单

## 部署说明

### 环境要求
- JDK 1.8+
- MySQL 5.7+
- Redis 3.0+
- Maven 3.6+

### 配置文件
```yaml
# application.yml
spring:
  profiles:
    active: local
  
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/jxc?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
  
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0
    timeout: 3000ms
    lettuce:
      pool:
        max-active: 8
        max-wait: -1ms
        max-idle: 8
        min-idle: 0

mybatis-plus:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: cn.toesbieya.jxc.model.entity
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

pagehelper:
  helper-dialect: mysql
  reasonable: true
  support-methods-arguments: true
  params: count=countSql
```

### 启动命令
```bash
# 编译打包
mvn clean package -Dmaven.test.skip=true

# 启动应用
java -jar target/my.jar --spring.profiles.active=prod
```

## 总结

本开发文档详细介绍了JXC进销存系统的完整架构和实现细节，包括：

1. **项目架构**: 基于Spring Boot的分层架构，包含控制器层、服务层、数据访问层
2. **核心功能**: 用户管理、权限控制、采购管理、销售管理、库存管理等
3. **技术栈**: Spring Boot、MyBatis Plus、Redis、MySQL等主流技术
4. **安全机制**: 基于拦截器的权限控制和会话管理
5. **API设计**: RESTful风格的API接口设计
6. **部署方案**: 完整的部署配置和启动说明

该系统具有良好的扩展性和维护性，可以作为企业级进销存管理系统的基础框架。 