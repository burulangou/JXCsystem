package cn.toesbieya.jxc.utils;

import cn.toesbieya.jxc.Pojo.entity.SysUser;
import cn.toesbieya.jxc.Pojo.vo.UserVo;
import cn.toesbieya.jxc.constant.SessionConstant;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    /**
     * 自定义的认证系统代替jwt令牌工作并提供了包含token，用户信息的获取和删除
     * */
    //设置token中的连接符
    private static final String tokenSeparator = "-*-";

    //移除相应token
    public static void remove(String token) {
        if (StringUtils.isEmpty(token)) return;

        RedisUtil.del(SessionConstant.REDIS_NAMESPACE + token);
    }

    //将user解析token并缓存输入redis里
    public static void save(UserVo user) {
        String token = user.getToken();

        if (StringUtils.isEmpty(token)) return;

        RedisUtil.set(SessionConstant.REDIS_NAMESPACE + token, user, SessionConstant.EXPIRE);
    }

    //从ThreadUtil方法获取uservo，如果从线程中没有获取到信息就将线程的HttpServletRequest发来再用同类方法实现解析
    public static UserVo get() {
        UserVo user = ThreadUtil.getUser();
        return user == null ? get(WebUtil.getRequest()) : user;
    }

    //从redis里获取对应token对象的UserVo所有属性
    public static UserVo get(String token) {
        if (StringUtils.isEmpty(token)) return null;

        JSONObject o = (JSONObject) RedisUtil.get(SessionConstant.REDIS_NAMESPACE + token);

        if (o == null) return null;

        return JSONObject.toJavaObject(o, UserVo.class);
    }

    //从servlet中获取到token的值再从redis中找到相应的用户数据
    public static UserVo get(HttpServletRequest request) {
        String token = request.getHeader(SessionConstant.TOKEN_KEY);
        return get(token);
    }

    //用户token的生成
    public static String generateToken(SysUser user) {
        return user.getId() + tokenSeparator + CommonUtil.UUID();
    }

    //获取用户id
    public static Integer getUidFromToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        String[] arr = token.split(tokenSeparator);

        return arr.length > 1 ? Integer.valueOf(arr[0]) : null;
    }
}
