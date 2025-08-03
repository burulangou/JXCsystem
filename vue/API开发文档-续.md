# JXC管理系统API开发文档（续）

## 4. 销售订单管理

### 4.1 根据ID获取销售订单
- **接口**: `GET /doc/sell/order/getById`
- **描述**: 根据ID获取销售订单详情
- **请求参数**:
```json
{
  "id": "销售订单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "id": "销售订单ID",
    "orderNo": "订单编号",
    "customerId": "客户ID",
    "customerName": "客户名称",
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

### 4.2 根据ID获取销售订单明细
- **接口**: `GET /doc/sell/order/getSubById`
- **描述**: 根据ID获取销售订单明细
- **请求参数**:
```json
{
  "id": "销售订单ID"
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
      "remainNum": "剩余未出库数量"
    }
  ]
}
```

### 4.3 搜索销售订单
- **接口**: `POST /doc/sell/order/search`
- **描述**: 搜索销售订单列表
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "customerId": "客户ID",
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
        "id": "销售订单ID",
        "orderNo": "订单编号",
        "customerName": "客户名称",
        "status": "订单状态",
        "totalAmount": "总金额",
        "ctime": "创建时间"
      }
    ],
    "total": 100
  }
}
```

### 4.4 导出销售订单
- **接口**: `POST /doc/sell/order/export`
- **描述**: 导出销售订单数据
- **请求参数**:
```json
{
  "keyword": "搜索关键词",
  "customerId": "客户ID",
  "status": "订单状态",
  "startTime": "开始时间",
  "endTime": "结束时间"
}
```
- **响应数据**: Excel文件流

### 4.5 添加销售订单
- **接口**: `POST /doc/sell/order/add`
- **描述**: 添加新销售订单
- **请求参数**:
```json
{
  "customerId": "客户ID",
  "customerName": "客户名称",
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

### 4.6 更新销售订单
- **接口**: `POST /doc/sell/order/update`
- **描述**: 更新销售订单
- **请求参数**:
```json
{
  "id": "销售订单ID",
  "customerId": "客户ID",
  "customerName": "客户名称",
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

### 4.7 提交销售订单
- **接口**: `POST /doc/sell/order/commit`
- **描述**: 提交销售订单（状态变为待审核）
- **请求参数**:
```json
{
  "id": "销售订单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "提交成功"
}
```

### 4.8 撤回销售订单
- **接口**: `POST /doc/sell/order/withdraw`
- **描述**: 撤回销售订单（状态变为拟定）
- **请求参数**:
```json
{
  "id": "销售订单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "撤回成功"
}
```

### 4.9 审核通过销售订单
- **接口**: `POST /doc/sell/order/pass`
- **描述**: 审核通过销售订单
- **请求参数**:
```json
{
  "id": "销售订单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "审核通过"
}
```

### 4.10 审核拒绝销售订单
- **接口**: `POST /doc/sell/order/reject`
- **描述**: 审核拒绝销售订单
- **请求参数**:
```json
{
  "id": "销售订单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "审核拒绝"
}
```

### 4.11 删除销售订单
- **接口**: `GET /doc/sell/order/del`
- **描述**: 删除销售订单
- **请求参数**:
```json
{
  "id": "销售订单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
```

## 5. 销售出库管理

### 5.1 根据ID获取销售出库单
- **接口**: `GET /doc/sell/outbound/getById`
- **描述**: 根据ID获取销售出库单详情
- **请求参数**:
```json
{
  "id": "销售出库单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "id": "销售出库单ID",
    "outboundNo": "出库单编号",
    "orderId": "销售订单ID",
    "orderNo": "销售订单编号",
    "customerId": "客户ID",
    "customerName": "客户名称",
    "status": "出库单状态",
    "totalAmount": "总金额",
    "remark": "备注",
    "ctime": "创建时间",
    "data": [
      {
        "id": "明细ID",
        "categoryId": "分类ID",
        "categoryName": "分类名称",
        "num": "出库数量",
        "price": "单价",
        "amount": "金额"
      }
    ]
  }
}
```

### 5.2 根据ID获取销售出库单明细
- **接口**: `GET /doc/sell/outbound/getSubById`
- **描述**: 根据ID获取销售出库单明细
- **请求参数**:
```json
{
  "id": "销售出库单ID"
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
      "num": "出库数量",
      "price": "单价",
      "amount": "金额"
    }
  ]
}
```

### 5.3 搜索销售出库单
- **接口**: `POST /doc/sell/outbound/search`
- **描述**: 搜索销售出库单列表
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "customerId": "客户ID",
  "status": "出库单状态",
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
        "id": "销售出库单ID",
        "outboundNo": "出库单编号",
        "orderNo": "销售订单编号",
        "customerName": "客户名称",
        "status": "出库单状态",
        "totalAmount": "总金额",
        "ctime": "创建时间"
      }
    ],
    "total": 100
  }
}
```

### 5.4 导出销售出库单
- **接口**: `POST /doc/sell/outbound/export`
- **描述**: 导出销售出库单数据
- **请求参数**:
```json
{
  "keyword": "搜索关键词",
  "customerId": "客户ID",
  "status": "出库单状态",
  "startTime": "开始时间",
  "endTime": "结束时间"
}
```
- **响应数据**: Excel文件流

### 5.5 添加销售出库单
- **接口**: `POST /doc/sell/outbound/add`
- **描述**: 添加新销售出库单
- **请求参数**:
```json
{
  "orderId": "销售订单ID",
  "customerId": "客户ID",
  "customerName": "客户名称",
  "remark": "备注",
  "data": [
    {
      "categoryId": "分类ID",
      "categoryName": "分类名称",
      "num": "出库数量",
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

### 5.6 更新销售出库单
- **接口**: `POST /doc/sell/outbound/update`
- **描述**: 更新销售出库单
- **请求参数**:
```json
{
  "id": "销售出库单ID",
  "customerId": "客户ID",
  "customerName": "客户名称",
  "remark": "备注",
  "data": [
    {
      "id": "明细ID",
      "categoryId": "分类ID",
      "categoryName": "分类名称",
      "num": "出库数量",
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

### 5.7 提交销售出库单
- **接口**: `POST /doc/sell/outbound/commit`
- **描述**: 提交销售出库单（状态变为待审核）
- **请求参数**:
```json
{
  "id": "销售出库单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "提交成功"
}
```

### 5.8 撤回销售出库单
- **接口**: `POST /doc/sell/outbound/withdraw`
- **描述**: 撤回销售出库单（状态变为拟定）
- **请求参数**:
```json
{
  "id": "销售出库单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "撤回成功"
}
```

### 5.9 审核通过销售出库单
- **接口**: `POST /doc/sell/outbound/pass`
- **描述**: 审核通过销售出库单
- **请求参数**:
```json
{
  "id": "销售出库单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "审核通过"
}
```

### 5.10 审核拒绝销售出库单
- **接口**: `POST /doc/sell/outbound/reject`
- **描述**: 审核拒绝销售出库单
- **请求参数**:
```json
{
  "id": "销售出库单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "审核拒绝"
}
```

### 5.11 删除销售出库单
- **接口**: `GET /doc/sell/outbound/del`
- **描述**: 删除销售出库单
- **请求参数**:
```json
{
  "id": "销售出库单ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
```

## 6. 消息管理API

### 6.1 消息管理

#### 6.1.1 搜索消息
- **接口**: `POST /message/manage/search`
- **描述**: 搜索消息列表
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "status": "消息状态",
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
        "id": "消息ID",
        "title": "消息标题",
        "content": "消息内容",
        "status": "消息状态",
        "recipient": "接收人",
        "ctime": "创建时间"
      }
    ],
    "total": 100
  }
}
```

#### 6.1.2 添加消息
- **接口**: `POST /message/manage/add`
- **描述**: 添加新消息
- **请求参数**:
```json
{
  "title": "消息标题",
  "content": "消息内容",
  "recipient": "接收人ID列表",
  "status": "消息状态"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "添加成功"
}
```

#### 6.1.3 更新消息
- **接口**: `POST /message/manage/update`
- **描述**: 更新消息信息
- **请求参数**:
```json
{
  "id": "消息ID",
  "title": "消息标题",
  "content": "消息内容",
  "recipient": "接收人ID列表",
  "status": "消息状态"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "更新成功"
}
```

#### 6.1.4 发布消息
- **接口**: `POST /message/manage/publish`
- **描述**: 发布消息
- **请求参数**:
```json
{
  "id": "消息ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "发布成功"
}
```

#### 6.1.5 撤回消息
- **接口**: `POST /message/manage/withdraw`
- **描述**: 撤回消息
- **请求参数**:
```json
{
  "id": "消息ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "撤回成功"
}
```

#### 6.1.6 删除消息
- **接口**: `GET /message/manage/del`
- **描述**: 删除消息
- **请求参数**:
```json
{
  "id": "消息ID",
  "title": "消息标题"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
```

### 6.2 用户消息

#### 6.2.1 搜索用户消息
- **接口**: `POST /message/user/search`
- **描述**: 搜索用户消息列表
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "read": true,
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
        "id": "消息ID",
        "title": "消息标题",
        "content": "消息内容",
        "read": true,
        "ctime": "创建时间"
      }
    ],
    "total": 100
  }
}
```

#### 6.2.2 标记消息已读
- **接口**: `GET /message/user/read`
- **描述**: 标记消息为已读
- **请求参数**:
```json
{
  "id": "消息ID"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "标记成功"
}
```

#### 6.2.3 标记所有消息已读
- **接口**: `GET /message/user/readAll`
- **描述**: 标记所有消息为已读
- **请求参数**: 无
- **响应数据**:
```json
{
  "status": 200,
  "msg": "标记成功"
}
```

## 7. 库存管理API

### 7.1 搜索库存
- **接口**: `POST /stock/current/search`
- **描述**: 搜索库存列表
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "cids": "分类ID列表",
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
        "cid": "分类ID",
        "cname": "分类名称",
        "totalNum": "库存总数",
        "totalPrice": "库存总值",
        "ctime": "最后入库时间"
      }
    ],
    "total": 100
  }
}
```

### 7.2 获取库存详情
- **接口**: `GET /stock/current/getDetail`
- **描述**: 根据分类ID获取库存详情
- **请求参数**:
```json
{
  "cids": "分类ID列表"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": [
    {
      "id": "库存记录ID",
      "cid": "分类ID",
      "cname": "分类名称",
      "totalNum": "库存总数",
      "totalPrice": "库存总值",
      "cgddid": "采购订单号",
      "cgPrice": "采购单价",
      "cgNum": "采购数量",
      "cgrkid": "采购入库单号",
      "ctime": "入库时间",
      "rkNum": "入库数量"
    }
  ]
}
```

### 7.3 根据ID获取库存详情
- **接口**: `GET /stock/current/getDetailById`
- **描述**: 根据ID获取库存详情
- **请求参数**:
```json
{
  "ids": "库存记录ID列表"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "data": [
    {
      "id": "库存记录ID",
      "cid": "分类ID",
      "cname": "分类名称",
      "totalNum": "库存总数",
      "totalPrice": "库存总值",
      "cgddid": "采购订单号",
      "cgPrice": "采购单价",
      "cgNum": "采购数量",
      "cgrkid": "采购入库单号",
      "ctime": "入库时间",
      "rkNum": "入库数量"
    }
  ]
}
```

## 8. 统计API

### 8.1 获取四个数据块
- **接口**: `GET /statistic/getFourBlock`
- **描述**: 获取首页四个数据块统计
- **请求参数**: 无
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "totalOrder": "总订单数",
    "totalAmount": "总金额",
    "totalCustomer": "总客户数",
    "totalSupplier": "总供应商数"
  }
}
```

### 8.2 获取每日利润统计
- **接口**: `GET /statistic/getDailyProfitStat`
- **描述**: 获取每日利润统计数据
- **请求参数**: 无
- **响应数据**:
```json
{
  "status": 200,
  "data": [
    {
      "date": "日期",
      "profit": "利润金额"
    }
  ]
}
```

### 8.3 获取每日完成订单统计
- **接口**: `GET /statistic/getDailyFinishOrder`
- **描述**: 获取每日完成订单统计数据
- **请求参数**: 无
- **响应数据**:
```json
{
  "status": 200,
  "data": [
    {
      "date": "日期",
      "orderCount": "订单数量"
    }
  ]
}
```

### 8.4 获取总利润商品统计
- **接口**: `GET /statistic/getTotalProfitGoods`
- **描述**: 获取总利润商品统计数据
- **请求参数**: 无
- **响应数据**:
```json
{
  "status": 200,
  "data": [
    {
      "categoryName": "商品分类名称",
      "profit": "利润金额",
      "orderCount": "订单数量"
    }
  ]
}
```

## 9. 文件管理API

### 9.1 获取上传Token
- **接口**: `GET /file/getToken`
- **描述**: 获取文件上传Token
- **请求参数**: 无
- **响应数据**:
```json
{
  "status": 200,
  "data": {
    "token": "上传Token",
    "domain": "域名",
    "bucket": "存储桶"
  }
}
```

### 9.2 删除上传文件
- **接口**: `GET /file/delete`
- **描述**: 删除上传的文件
- **请求参数**:
```json
{
  "url": "文件URL"
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
```

### 9.3 批量删除上传文件
- **接口**: `POST /file/deleteBatch`
- **描述**: 批量删除上传的文件
- **请求参数**:
```json
{
  "urls": ["文件URL列表"]
}
```
- **响应数据**:
```json
{
  "status": 200,
  "msg": "删除成功"
}
```

## 10. 记录API

### 10.1 搜索登录历史
- **接口**: `POST /record/searchLoginHistory`
- **描述**: 搜索用户登录历史记录
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
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
        "id": "记录ID",
        "userId": "用户ID",
        "userName": "用户名",
        "loginTime": "登录时间",
        "ip": "登录IP",
        "device": "登录设备"
      }
    ],
    "total": 100
  }
}
```

### 10.2 搜索用户操作记录
- **接口**: `POST /record/searchUserAction`
- **描述**: 搜索用户操作记录
- **请求参数**:
```json
{
  "page": 1,
  "size": 10,
  "keyword": "搜索关键词",
  "userId": "用户ID",
  "action": "操作类型",
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
        "id": "记录ID",
        "userId": "用户ID",
        "userName": "用户名",
        "action": "操作类型",
        "description": "操作描述",
        "ip": "操作IP",
        "ctime": "操作时间"
      }
    ],
    "total": 100
  }
}
```

## 总结

本文档涵盖了JXC管理系统的所有主要API接口，包括：

1. **账户管理** - 用户登录、注册、密码管理等
2. **系统管理** - 用户、分类、客户、供应商、部门、角色、资源管理
3. **文档管理** - 采购订单、采购入库、销售订单、销售出库管理
4. **消息管理** - 消息发布、用户消息查看
5. **库存管理** - 库存查询、详情查看
6. **统计功能** - 各种统计数据
7. **文件管理** - 文件上传、删除
8. **记录管理** - 登录历史、操作记录

所有API都遵循统一的响应格式，包含状态码、数据和消息。错误处理机制完善，支持401未登录、403无权限、500服务器错误等状态码。 