package cn.itsource.security.handler;

import cn.itsource.result.JSONResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author tangrufei
 * @date 2022-09-08 09:18
 */
@Component //处理认证成功类
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    //只有当认证成功时才会调用该方法
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest Request,
            HttpServletResponse Response,
            Authentication authentication) throws IOException, ServletException {
        Response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = Response.getWriter();
        writer.write(JSONObject.toJSONString(JSONResult.success(authentication)));
    }

    @Override  /**
     * @desc desc 当认证失败时调用
     * @author tangrufei
     * @date 2022/9/8 9:58
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     */
    public void onAuthenticationFailure(
            HttpServletRequest Request,
            HttpServletResponse Response,
            AuthenticationException e
    ) throws IOException, ServletException {
        Response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = Response.getWriter();
        writer.write(JSONObject.toJSONString(JSONResult.error(e.getMessage())));
    }
}
