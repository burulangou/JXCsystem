package cn.toesbieya.jxc.utils;

import cn.toesbieya.jxc.ResponseFormat.Result;
import com.alibaba.fastjson.JSON;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class WebUtil {
    /**
     * 字符编码设置：UTF-8确保中文正确显示
     * 内容类型设置：application/json告诉浏览器这是JSON数据
     * JSON序列化：使用FastJSON将对象转换为JSON字符串
     * 响应输出：直接写入响应流
     * */
    public static void responseJson(HttpServletResponse response, Result result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(JSON.toJSONString(result));
    }

    public static HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attributes != null ? attributes.getRequest() : null;
        } catch (Exception e) {
            // 记录日志但不抛出异常
            return null;
        }
    }
}
