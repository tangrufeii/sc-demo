package cn.itsource.security.handler;

import cn.itsource.result.JSONResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * AuthenticationEntryPoint:处理匿名用户授权鼠标
 * @author tangrufei
 * @date 2022-09-08 10:06
 */
@Component
        //授权失败结果处理类
public class MyAccessDeniedHandler implements AccessDeniedHandler, AuthenticationEntryPoint {

    @Override
    //匿名用户授权失败处理方法
    public void commence(
            HttpServletRequest Request,
            HttpServletResponse Response,
            AuthenticationException e) throws IOException, ServletException {
        Response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = Response.getWriter();
        writer.write(JSONObject.toJSONString(JSONResult.error("请先登录")));
    }
    //已登录用户授权失败
    @Override
    public void handle(HttpServletRequest Request, HttpServletResponse Response, AccessDeniedException e) {

        try {
            Response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = null;
            writer = Response.getWriter();
            writer.write(JSONObject.toJSONString(JSONResult.error("权限不足")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
