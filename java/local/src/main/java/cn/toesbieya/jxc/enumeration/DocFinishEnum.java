package cn.toesbieya.jxc.enumeration;

public enum DocFinishEnum {
    /**
     * 业务文档的完成进度状态
     * */
    TO_BE_STARTED(0), UNDERWAY(1), FINISHED(2);

    private final int code;

    DocFinishEnum(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
