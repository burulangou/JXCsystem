package cn.toesbieya.jxc.enumeration;

public enum GeneralStatusEnum {
    /**
     * 通用状态枚举
     * */
    DISABLED(0), ENABLED(1);

    private final int code;

    GeneralStatusEnum(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
