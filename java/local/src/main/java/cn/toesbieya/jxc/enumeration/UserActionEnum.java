package cn.toesbieya.jxc.enumeration;

public enum UserActionEnum {
    /**
     * 用户行为成败枚举
     * */
    FAIL(0), SUCCESS(1);

    private final int code;

    UserActionEnum(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
