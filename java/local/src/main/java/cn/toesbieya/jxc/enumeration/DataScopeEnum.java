package cn.toesbieya.jxc.enumeration;

public enum DataScopeEnum {
    /**
     * 用户数据访问权限范围
     * */
    ALL(1), SELF(2), SPECIFIC(3);

    private final int code;

    DataScopeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
