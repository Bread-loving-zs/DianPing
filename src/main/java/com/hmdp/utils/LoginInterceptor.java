package com.hmdp.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取Session
//        服务器先从当前请求（Cookie/URL 重写参数）里提取 JSESSIONID；
//        根据 JSESSIONID 去服务器内存 / 持久化容器查找对应的 HttpSession 对象；
//        找不到 / 没有 JSESSIONID：新建 Session，并返回新对象，同时给客户端下发带 JSESSIONID 的 Cookie。
//        HttpSession session = request.getSession();


        if(UserHolder.getUser() == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

}
