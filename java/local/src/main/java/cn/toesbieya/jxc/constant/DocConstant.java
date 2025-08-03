package cn.toesbieya.jxc.constant;

public interface DocConstant {
    String SESSION_ID = "sessionId";
    String UPDATE_DOC_LOCK_KEY = "UPDATE_DOC";
    /**
     * DOC_TYPE: 文档类型数组，包含6种单据类型：
     * CGDD: 采购订单 (采购订单)
     * CGRK: 采购入库 (采购入库单)
     * CGTH: 采购退货 (采购退货单)
     * XSDD: 销售订单 (销售订单)
     * XSCK: 销售出库 (销售出库单)
     * XSTH: 销售退货 (销售退货单)
     */
    String[] DOC_TYPE = {"CGDD", "CGRK", "CGTH", "XSDD", "XSCK", "XSTH"};
    String DOC_TYPE_REDIS_KEY = "DOC_ID";
}
