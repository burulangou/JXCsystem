# JXC管理系统API开发文档

## 概述

本文档描述了JXC管理系统的所有后端API接口，包括请求参数、响应格式和业务逻辑。

### 通用信息

#### 基础URL
```
baseURL: apiPrefix (从配置文件读取)
```

#### 请求头
```
X-Token: 用户登录后的token
Content-Type: application/json
```

#### 响应格式
```json
{
  "status": 200,  // 状态码：200成功，401未登录，403无权限，500服务器错误
  "data": {},     // 响应数据
  "msg": "操作成功"  // 响应消息
}
```

#### 错误处理
- **401**: 未登录，自动跳转到登录页
- **403**: 无权限，显示错误消息
- **500**: 服务器错误，显示错误消息

## 1. 账户管理API

### 1.1 用户登录
- **接口**: `POST /account/login`
- **描述**: 用户登录验证
- **请求参数**:
```json
{
  "username": "用户名",
  "password": "MD5加密的密码"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "id": "用户ID",
    "loginName": "登录名",
    "nickName": "昵称",
    "avatar": "头像URL",
    "token": "登录token",
    "admin": true,
    "roleName": "角色名称",
    "resources": {}
  },
  "msg": "登录成功"
}
```

### 1.2 用户登出
- **接口**: `GET /account/logout`
- **描述**: 用户登出
- **请求参数**: 无
- **响应数据**:
```json
{
  "status": 200,
  "msg": "登出成功"
}
```

### 1.3 用户注册
- **接口**: `POST /account/register`
- **描述**: 新用户注册
- **请求参数**:
```json
{
  "username": "用户名",
  "password": "MD5加密的密码",
  "nickName": "昵称"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "注册成功"
}
```

### 1.4 修改密码
- **接口**: `POST /account/updatePwd`
- **描述**: 修改用户密码
- **请求参数**:
```json
{
  "oldPassword": "旧密码",
  "newPassword": "新密码"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "密码修改成功"
}
```

### 1.5 更新头像
- **接口**: `GET /account/updateAvatar`
- **描述**: 更新用户头像
- **请求参数**:
```json
{
  "key": "头像文件key"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "头像更新成功"
}
```

### 1.6 验证密码
- **接口**: `GET /account/validate`
- **描述**: 验证用户密码
- **请求参数**:
```json
{
  "pwd": "要验证的密码"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": true/false
}
```

### 1.7 检查登录名
- **接口**: `GET /account/checkLoginName`
- **描述**: 检查登录名是否已存在
- **请求参数**:
```json
{
  "name": "登录名",
  "id": "用户ID（编辑时传）"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": true/false
}
```

### 1.8 检查昵称
- **接口**: `GET /account/checkNickName`
- **描述**: 检查昵称是否已存在
- **请求参数**:
```json
{
  "name": "昵称",
  "id": "用户ID（编辑时传）"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": true/false
}
```

## 2. 系统管理API

### 2.1 用户管理

#### 2.1.1 搜索用户
- **接口**: `POST /system/user/search`
- **描述**: 搜索用户列表
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "role": "角色ID",
  "dept": "部门ID",
  "enable": true
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "list": [
      {
        "id": "用户ID",
        "loginName": "登录名",
        "nickName": "昵称",
        "roleName": "角色名称",
        "deptName": "部门名称",
        "enable": true,
        "ctime": "创建时间"
      }
    ],
    "total": 100
  }
}
```

#### 2.1.2 添加用户
- **接口**: `POST /system/user/add`
- **描述**: 添加新用户
- **请求参数**:
```json
{
  "loginName": "登录名",
  "nickName": "昵称",
  "role": "角色ID",
  "dept": "部门ID",
  "enable": true
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "添加成功"
}
```

#### 2.1.3 更新用户
- **接口**: `POST /system/user/update`
- **描述**: 更新用户信息
- **请求参数**:
```json
{
  "id": "用户ID",
  "loginName": "登录名",
  "nickName": "昵称",
  "role": "角色ID",
  "dept": "部门ID",
  "enable": true
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "更新成功"
}
```

#### 2.1.4 删除用户
- **接口**: `POST /system/user/del`
- **描述**: 删除用户
- **请求参数**:
```json
{
  "id": "用户ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
```

#### 2.1.5 重置密码
- **接口**: `POST /system/user/resetPwd`
- **描述**: 重置用户密码
- **请求参数**:
```json
{
  "id": "用户ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "密码重置成功"
}
```

#### 2.1.6 踢出用户
- **接口**: `POST /system/user/kick`
- **描述**: 强制用户下线
- **请求参数**:
```json
{
  "id": "用户ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "操作成功"
}
```

### 2.2 分类管理

#### 2.2.1 搜索分类
- **接口**: `POST /system/category/search`
- **描述**: 搜索分类列表
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "enable": true
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "list": [
      {
        "id": "分类ID",
        "name": "分类名称",
        "enable": true,
        "ctime": "创建时间"
      }
    ],
    "total": 50
  }
}
```

#### 2.2.2 获取所有分类
- **接口**: `GET /system/category/getAll`
- **描述**: 获取所有启用的分类
- **请求参数**: 无
- **响应数据**:
```json
{
  "status": 200,
  "data": [
    {
      "id": "分类ID",
      "name": "分类名称"
    }
  ]
}
```

#### 2.2.3 添加分类
- **接口**: `POST /system/category/add`
- **描述**: 添加新分类
- **请求参数**:
```json
{
  "name": "分类名称",
  "enable": true
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "添加成功"
}
```

#### 2.2.4 更新分类
- **接口**: `POST /system/category/update`
- **描述**: 更新分类信息
- **请求参数**:
```json
{
  "id": "分类ID",
  "name": "分类名称",
  "enable": true
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "更新成功"
}
```

#### 2.2.5 删除分类
- **接口**: `POST /system/category/del`
- **描述**: 删除分类
- **请求参数**:
```json
{
  "id": "分类ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
```

### 2.3 客户管理

#### 2.3.1 获取限制区域
- **接口**: `GET /system/customer/getLimitRegion`
- **描述**: 获取客户可选择的行政区域
- **请求参数**: 无
- **响应数据**:
```json
{
  "status": 200,
  "data": [
    {
      "id": "区域ID",
      "name": "区域名称",
      "fullname": "完整区域名称"
    }
  ]
}
```

#### 2.3.2 搜索客户
- **接口**: `POST /system/customer/search`
- **描述**: 搜索客户列表
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "region": "区域ID",
  "enable": true,
  "startTime": "开始时间",
  "endTime": "结束时间"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "list": [
      {
        "id": "客户ID",
        "name": "客户名称",
        "address": "地址",
        "linkman": "联系人",
        "linkphone": "联系电话",
        "regionName": "行政区域",
        "enable": true,
        "remark": "备注",
        "ctime": "创建时间"
      }
    ],
    "total": 100
  }
}
```

#### 2.3.3 添加客户
- **接口**: `POST /system/customer/add`
- **描述**: 添加新客户
- **请求参数**:
```json
{
  "name": "客户名称",
  "address": "地址",
  "linkman": "联系人",
  "linkphone": "联系电话",
  "region": "区域ID",
  "regionName": "行政区域名称",
  "enable": true,
  "remark": "备注"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "添加成功"
}
```

#### 2.3.4 更新客户
- **接口**: `POST /system/customer/update`
- **描述**: 更新客户信息
- **请求参数**:
```json
{
  "id": "客户ID",
  "name": "客户名称",
  "address": "地址",
  "linkman": "联系人",
  "linkphone": "联系电话",
  "region": "区域ID",
  "regionName": "行政区域名称",
  "enable": true,
  "remark": "备注"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "更新成功"
}
```

#### 2.3.5 删除客户
- **接口**: `POST /system/customer/del`
- **描述**: 删除客户
- **请求参数**:
```json
{
  "id": "客户ID",
  "name": "客户名称"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
```

### 2.4 供应商管理

#### 2.4.1 获取限制区域
- **接口**: `GET /system/supplier/getLimitRegion`
- **描述**: 获取供应商可选择的行政区域
- **请求参数**: 无
- **响应数据**:
```json
{
  "status": 200,
  "data": [
    {
      "id": "区域ID",
      "name": "区域名称",
      "fullname": "完整区域名称"
    }
  ]
}
```

#### 2.4.2 搜索供应商
- **接口**: `POST /system/supplier/search`
- **描述**: 搜索供应商列表
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "region": "区域ID",
  "enable": true,
  "startTime": "开始时间",
  "endTime": "结束时间"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "list": [
      {
        "id": "供应商ID",
        "name": "供应商名称",
        "address": "地址",
        "linkman": "联系人",
        "linkphone": "联系电话",
        "regionName": "行政区域",
        "enable": true,
        "remark": "备注",
        "ctime": "创建时间"
      }
    ],
    "total": 100
  }
}
```

#### 2.4.3 添加供应商
- **接口**: `POST /system/supplier/add`
- **描述**: 添加新供应商
- **请求参数**:
```json
{
  "name": "供应商名称",
  "address": "地址",
  "linkman": "联系人",
  "linkphone": "联系电话",
  "region": "区域ID",
  "regionName": "行政区域名称",
  "enable": true,
  "remark": "备注"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "添加成功"
}
```

#### 2.4.4 更新供应商
- **接口**: `POST /system/supplier/update`
- **描述**: 更新供应商信息
- **请求参数**:
```json
{
  "id": "供应商ID",
  "name": "供应商名称",
  "address": "地址",
  "linkman": "联系人",
  "linkphone": "联系电话",
  "region": "区域ID",
  "regionName": "行政区域名称",
  "enable": true,
  "remark": "备注"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "更新成功"
}
```

#### 2.4.5 删除供应商
- **接口**: `POST /system/supplier/del`
- **描述**: 删除供应商
- **请求参数**:
```json
{
  "id": "供应商ID",
  "name": "供应商名称"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
```

### 2.5 部门管理

#### 2.5.1 获取部门
- **接口**: `GET /system/department/get`
- **描述**: 获取部门树形结构
- **请求参数**:
```json
{
  "all": true  // 是否包含禁用的部门
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": [
    {
      "id": "部门ID",
      "name": "部门名称",
      "pid": "父部门ID",
      "enable": true,
      "children": []
    }
  ]
}
```

#### 2.5.2 添加部门
- **接口**: `POST /system/department/add`
- **描述**: 添加新部门
- **请求参数**:
```json
{
  "name": "部门名称",
  "pid": "父部门ID",
  "enable": true
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "添加成功"
}
```

#### 2.5.3 更新部门
- **接口**: `POST /system/department/update`
- **描述**: 更新部门信息
- **请求参数**:
```json
{
  "id": "部门ID",
  "name": "部门名称",
  "pid": "父部门ID",
  "enable": true
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "更新成功"
}
```

#### 2.5.4 删除部门
- **接口**: `POST /system/department/del`
- **描述**: 删除部门
- **请求参数**:
```json
{
  "id": "部门ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
```

### 2.6 角色管理

#### 2.6.1 搜索角色
- **接口**: `POST /system/role/search`
- **描述**: 搜索角色列表
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "enable": true
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "list": [
      {
        "id": "角色ID",
        "name": "角色名称",
        "enable": true,
        "ctime": "创建时间"
      }
    ],
    "total": 50
  }
}
```

#### 2.6.2 获取角色
- **接口**: `GET /system/role/get`
- **描述**: 获取所有启用的角色
- **请求参数**: 无
- **响应数据**:
```json
{
  "status": 200,
  "data": [
    {
      "id": "角色ID",
      "name": "角色名称"
    }
  ]
}
```

#### 2.6.3 添加角色
- **接口**: `POST /system/role/add`
- **描述**: 添加新角色
- **请求参数**:
```json
{
  "name": "角色名称",
  "enable": true
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "添加成功"
}
```

#### 2.6.4 更新角色
- **接口**: `POST /system/role/update`
- **描述**: 更新角色信息
- **请求参数**:
```json
{
  "id": "角色ID",
  "name": "角色名称",
  "enable": true
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "更新成功"
}
```

#### 2.6.5 删除角色
- **接口**: `POST /system/role/del`
- **描述**: 删除角色
- **请求参数**:
```json
{
  "id": "角色ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
```

### 2.7 资源管理

#### 2.7.1 获取所有资源
- **接口**: `GET /system/resource/getAll`
- **描述**: 获取所有资源列表
- **请求参数**: 无
- **响应数据**:
```json
{
  "status": 200,
  "data": [
    {
      "id": "资源ID",
      "name": "资源名称",
      "path": "资源路径",
      "type": "资源类型"
    }
  ]
}
```

#### 2.7.2 添加资源
- **接口**: `POST /system/resource/add`
- **描述**: 添加新资源
- **请求参数**:
```json
{
  "name": "资源名称",
  "path": "资源路径",
  "type": "资源类型"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "添加成功"
}
```

#### 2.7.3 更新资源
- **接口**: `POST /system/resource/update`
- **描述**: 更新资源信息
- **请求参数**:
```json
{
  "id": "资源ID",
  "name": "资源名称",
  "path": "资源路径",
  "type": "资源类型"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "更新成功"
}
```

#### 2.7.4 删除资源
- **接口**: `GET /system/resource/del`
- **描述**: 删除资源
- **请求参数**:
```json
{
  "id": "资源ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
```

## 3. 文档管理API

### 3.1 文档历史

#### 3.1.1 搜索文档历史
- **接口**: `POST /doc/history/search`
- **描述**: 搜索文档历史记录
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "type": "文档类型",
  "startTime": "开始时间",
  "endTime": "结束时间"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "list": [
      {
        "id": "历史记录ID",
        "docId": "文档ID",
        "docType": "文档类型",
        "action": "操作类型",
        "operator": "操作人",
        "ctime": "操作时间",
        "remark": "备注"
      }
    ],
    "total": 100
  }
}
```

### 3.2 采购订单管理

#### 3.2.1 根据ID获取采购订单
- **接口**: `GET /doc/purchase/order/getById`
- **描述**: 根据ID获取采购订单详情
- **请求参数**:
```json
{
  "id": "采购订单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "id": "采购订单ID",
    "orderNo": "订单编号",
    "supplierId": "供应商ID",
    "supplierName": "供应商名称",
    "status": "订单状态",
    "totalAmount": "总金额",
    "remark": "备注",
    "ctime": "创建时间",
    "data": [
      {
        "id": "明细ID",
        "categoryId": "分类ID",
        "categoryName": "分类名称",
        "num": "数量",
        "price": "单价",
        "amount": "金额"
      }
    ]
  }
}
```

#### 3.2.2 根据ID获取采购订单明细
- **接口**: `GET /doc/purchase/order/getSubById`
- **描述**: 根据ID获取采购订单明细
- **请求参数**:
```json
{
  "id": "采购订单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": [
    {
      "id": "明细ID",
      "categoryId": "分类ID",
      "categoryName": "分类名称",
      "num": "数量",
      "price": "单价",
      "amount": "金额",
      "remainNum": "剩余未入库数量"
    }
  ]
}
```

#### 3.2.3 搜索采购订单
- **接口**: `POST /doc/purchase/order/search`
- **描述**: 搜索采购订单列表
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "supplierId": "供应商ID",
  "status": "订单状态",
  "startTime": "开始时间",
  "endTime": "结束时间"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "list": [
      {
        "id": "采购订单ID",
        "orderNo": "订单编号",
        "supplierName": "供应商名称",
        "status": "订单状态",
        "totalAmount": "总金额",
        "ctime": "创建时间"
      }
    ],
    "total": 100
  }
}
```

#### 3.2.4 导出采购订单
- **接口**: `POST /doc/purchase/order/export`
- **描述**: 导出采购订单数据
- **请求参数**:
```json
{
  "keyword": "搜索关键词",
  "supplierId": "供应商ID",
  "status": "订单状态",
  "startTime": "开始时间",
  "endTime": "结束时间"
}
```
- **响应数据**: Excel文件流

#### 3.2.5 添加采购订单
- **接口**: `POST /doc/purchase/order/add`
- **描述**: 添加新采购订单
- **请求参数**:
```json
{
  "supplierId": "供应商ID",
  "supplierName": "供应商名称",
  "remark": "备注",
  "data": [
    {
      "categoryId": "分类ID",
      "categoryName": "分类名称",
      "num": "数量",
      "price": "单价"
    }
  ]
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "添加成功"
}
```

#### 3.2.6 更新采购订单
- **接口**: `POST /doc/purchase/order/update`
- **描述**: 更新采购订单
- **请求参数**:
```json
{
  "id": "采购订单ID",
  "supplierId": "供应商ID",
  "supplierName": "供应商名称",
  "remark": "备注",
  "data": [
    {
      "id": "明细ID",
      "categoryId": "分类ID",
      "categoryName": "分类名称",
      "num": "数量",
      "price": "单价"
    }
  ]
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "更新成功"
}
```

#### 3.2.7 提交采购订单
- **接口**: `POST /doc/purchase/order/commit`
- **描述**: 提交采购订单（状态变为待审核）
- **请求参数**:
```json
{
  "id": "采购订单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "提交成功"
}
```

#### 3.2.8 撤回采购订单
- **接口**: `POST /doc/purchase/order/withdraw`
- **描述**: 撤回采购订单（状态变为拟定）
- **请求参数**:
```json
{
  "id": "采购订单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "撤回成功"
}
```

#### 3.2.9 审核通过采购订单
- **接口**: `POST /doc/purchase/order/pass`
- **描述**: 审核通过采购订单
- **请求参数**:
```json
{
  "id": "采购订单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "审核通过"
}
```

#### 3.2.10 审核拒绝采购订单
- **接口**: `POST /doc/purchase/order/reject`
- **描述**: 审核拒绝采购订单
- **请求参数**:
```json
{
  "id": "采购订单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "审核拒绝"
}
```

#### 3.2.11 删除采购订单
- **接口**: `GET /doc/purchase/order/del`
- **描述**: 删除采购订单
- **请求参数**:
```json
{
  "id": "采购订单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
```

### 3.3 采购入库管理

#### 3.3.1 根据ID获取采购入库单
- **接口**: `GET /doc/purchase/inbound/getById`
- **描述**: 根据ID获取采购入库单详情
- **请求参数**:
```json
{
  "id": "采购入库单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "id": "采购入库单ID",
    "inboundNo": "入库单编号",
    "orderId": "采购订单ID",
    "orderNo": "采购订单编号",
    "supplierId": "供应商ID",
    "supplierName": "供应商名称",
    "status": "入库单状态",
    "totalAmount": "总金额",
    "remark": "备注",
    "ctime": "创建时间",
    "data": [
      {
        "id": "明细ID",
        "categoryId": "分类ID",
        "categoryName": "分类名称",
        "num": "入库数量",
        "price": "单价",
        "amount": "金额"
      }
    ]
  }
}
```

#### 3.3.2 根据ID获取采购入库单明细
- **接口**: `GET /doc/purchase/inbound/getSubById`
- **描述**: 根据ID获取采购入库单明细
- **请求参数**:
```json
{
  "id": "采购入库单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": [
    {
      "id": "明细ID",
      "categoryId": "分类ID",
      "categoryName": "分类名称",
      "num": "入库数量",
      "price": "单价",
      "amount": "金额"
    }
  ]
}
```

#### 3.3.3 搜索采购入库单
- **接口**: `POST /doc/purchase/inbound/search`
- **描述**: 搜索采购入库单列表
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "supplierId": "供应商ID",
  "status": "入库单状态",
  "startTime": "开始时间",
  "endTime": "结束时间"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "list": [
      {
        "id": "采购入库单ID",
        "inboundNo": "入库单编号",
        "orderNo": "采购订单编号",
        "supplierName": "供应商名称",
        "status": "入库单状态",
        "totalAmount": "总金额",
        "ctime": "创建时间"
      }
    ],
    "total": 100
  }
}
```

#### 3.3.4 导出采购入库单
- **接口**: `POST /doc/purchase/inbound/export`
- **描述**: 导出采购入库单数据
- **请求参数**:
```json
{
  "keyword": "搜索关键词",
  "supplierId": "供应商ID",
  "status": "入库单状态",
  "startTime": "开始时间",
  "endTime": "结束时间"
}
```
- **响应数据**: Excel文件流

#### 3.3.5 添加采购入库单
- **接口**: `POST /doc/purchase/inbound/add`
- **描述**: 添加新采购入库单
- **请求参数**:
```json
{
  "orderId": "采购订单ID",
  "supplierId": "供应商ID",
  "supplierName": "供应商名称",
  "remark": "备注",
  "data": [
    {
      "categoryId": "分类ID",
      "categoryName": "分类名称",
      "num": "入库数量",
      "price": "单价"
    }
  ]
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "添加成功"
}
```

#### 3.3.6 更新采购入库单
- **接口**: `POST /doc/purchase/inbound/update`
- **描述**: 更新采购入库单
- **请求参数**:
```json
{
  "id": "采购入库单ID",
  "supplierId": "供应商ID",
  "supplierName": "供应商名称",
  "remark": "备注",
  "data": [
    {
      "id": "明细ID",
      "categoryId": "分类ID",
      "categoryName": "分类名称",
      "num": "入库数量",
      "price": "单价"
    }
  ]
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "更新成功"
}
```

#### 3.3.7 提交采购入库单
- **接口**: `POST /doc/purchase/inbound/commit`
- **描述**: 提交采购入库单（状态变为待审核）
- **请求参数**:
```json
{
  "id": "采购入库单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "提交成功"
}
```

#### 3.3.8 撤回采购入库单
- **接口**: `POST /doc/purchase/inbound/withdraw`
- **描述**: 撤回采购入库单（状态变为拟定）
- **请求参数**:
```json
{
  "id": "采购入库单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "撤回成功"
}
```

#### 3.3.9 审核通过采购入库单
- **接口**: `POST /doc/purchase/inbound/pass`
- **描述**: 审核通过采购入库单
- **请求参数**:
```json
{
  "id": "采购入库单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "审核通过"
}
```

#### 3.3.10 审核拒绝采购入库单
- **接口**: `POST /doc/purchase/inbound/reject`
- **描述**: 审核拒绝采购入库单
- **请求参数**:
```json
{
  "id": "采购入库单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "审核拒绝"
}
```

#### 3.3.11 删除采购入库单
- **接口**: `GET /doc/purchase/inbound/del`
- **描述**: 删除采购入库单
- **请求参数**:
```json
{
  "id": "采购入库单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
``` 