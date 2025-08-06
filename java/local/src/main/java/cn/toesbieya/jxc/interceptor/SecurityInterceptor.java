package cn.toesbieya.jxc.interceptor;

import cn.toesbieya.jxc.Pojo.vo.UserVo;
import cn.toesbieya.jxc.ResponseFormat.Result;
import cn.toesbieya.jxc.module.PermissionModule;
import cn.toesbieya.jxc.utils.SessionUtil;
import cn.toesbieya.jxc.utils.ThreadUtil;
import cn.toesbieya.jxc.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        String url = request.getRequestURI();
        UserVo user = SessionUtil.get(request);

        if(user == null) {
            WebUtil.responseJson(response, Result.fail("当前用户未登录"));
            ThreadUtil.clearAll();
            return false;
        }

        if(!PermissionModule.authority(user,url)){
            WebUtil.responseJson(response, Result.noPermission());
            log.warn("权限拦截，访问路径：{}，用户：{}", url, user.getNickName());
            ThreadUtil.clearAll();
            return false;
        }
        ThreadUtil.setUser(user);
        ThreadUtil.quicklySetAction(request);

        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadUtil.clearAll();
    }
}
