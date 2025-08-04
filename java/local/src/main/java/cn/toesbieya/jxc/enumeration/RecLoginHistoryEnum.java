package cn.toesbieya.jxc.enumeration;

public enum RecLoginHistoryEnum {
    /**
     * 用户登录状态枚举
     * */
    LOGOUT(0), LOGIN(1);

    private final int code;

    RecLoginHistoryEnum(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
