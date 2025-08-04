package cn.toesbieya.jxc.enumeration;

public enum DocStatusEnum {
    /**
     * 文档的当前状态
     * */
    DRAFT(0), WAIT_VERIFY(1), VERIFIED(2);

    private final int code;

    DocStatusEnum(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
