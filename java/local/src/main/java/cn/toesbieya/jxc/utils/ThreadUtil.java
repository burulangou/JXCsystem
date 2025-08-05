package cn.toesbieya.jxc.utils;

import cn.toesbieya.jxc.Pojo.entity.RecUserAction;
import cn.toesbieya.jxc.Pojo.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

public class ThreadUtil {
    private static final ThreadLocal<RecUserAction> THREAD_LOCAL_USER_ACTION = new ThreadLocal<>();
    private static final ThreadLocal<UserVo> THREAD_LOCAL_USER = new ThreadLocal<>();

    //通过从request解析出token再再redis中获取UerVo信息最后传给RecUerAction
    public static void quicklySetAction(HttpServletRequest request) {
        UserVo user = getUser();
        if (user == null) user = SessionUtil.get(request);
        if (user == null) return;

        RecUserAction userAction = RecUserAction.builder()
                .uid(user.getId())
                .uname(user.getNickName())
                .ip(IpUtil.getIp(request))
                .url(request.getServletPath())
                .build();

        setAction(userAction);
    }

    //从action线程map获取数据
    public static RecUserAction getAction() {
        return THREAD_LOCAL_USER_ACTION.get();
    }

    //给action线程map存入数据
    public static void setAction(RecUserAction action) {
        THREAD_LOCAL_USER_ACTION.set(action);
    }

    //从Vo线程map获取数据
    public static UserVo getUser() {
        return THREAD_LOCAL_USER.get();
    }

    //给Vo线程map存入数据
    public static void setUser(UserVo user) {
        THREAD_LOCAL_USER.set(user);
    }

    //清理
    public static void clearAll() {
        clearAction();
        clearUser();
    }

    //单独清理
    public static void clearAction() {
        THREAD_LOCAL_USER_ACTION.remove();
    }

    //单独清理
    public static void clearUser() {
        THREAD_LOCAL_USER.remove();
    }
}
