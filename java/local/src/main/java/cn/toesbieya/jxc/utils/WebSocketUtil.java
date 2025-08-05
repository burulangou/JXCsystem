package cn.toesbieya.jxc.utils;

import cn.toesbieya.jxc.Pojo.vo.SocketEventVo;
import cn.toesbieya.jxc.constant.SocketConstant;
import org.apache.tomcat.util.net.SocketEvent;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WebSocketUtil {
    //查找在线用户数量
    public static long getOnlineUserNumber(){
        return RedisUtil.scard(SocketConstant.REDIS_ONLINE_USER);
    }

    //获取所有在线用户的id集合
    public static Set<Integer> getOnlineUserIds(){
        Set<Object> OnlineUsers = RedisUtil.smembers(SocketConstant.REDIS_ONLINE_USER);
        return OnlineUsers.stream()
                .filter(obj -> obj instanceof Integer)
                .map(obj -> (Integer) obj)
                .collect(Collectors.toSet());
    }

    //发送消息
    public static void sendEvent(SocketEventVo event){
        RedisUtil.getRedisTemplate().convertAndSend(SocketConstant.EVENT_NEW_MESSAGE,event);
    }

    //登录消息
    public static void sendLoginEvent(List<Integer> to, String msg){
        SocketEventVo event = new SocketEventVo();
        event.setEvent(SocketConstant.EVENT_LOGIN);
        event.setTo(to);
        event.setData(msg);
        sendEvent(event);
    }

    //登出消息
    public static void sendLogOutEvent(List<Integer> to, String msg){
        SocketEventVo event = new SocketEventVo();
        event.setEvent(SocketConstant.EVENT_LOGOUT);
        event.setTo(to);
        event.setData(msg);
        sendEvent(event);
    }
}
