package cn.toesbieya.jxc.enumeration;

public enum ResourceEnum {
    /**
     * 系统资源（菜单、权限）的类型枚举
     * */
    ROOT(0), FOLDER(1), LEAF(2), API(3);

    private final int code;

    ResourceEnum(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
