package cn.itsource.config;


import cn.itsource.dto.SmsCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//用来校验验证码的filter
@Component
public class SmsCodeCheckFilter extends OncePerRequestFilter {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
   // private MyAuthSuccessHandler myAuthSuccessHandler;

    /**
     1. 判断请求URL是不是验证码登录请求
     2. 如果是，则从请求中获取用户输入的短信验证码
     3. 从session中拿到短信验证码
     4. 进行比对，如果验证码错误，则走认证失败的Handler
     5. 如果验证码正确，则继续后面过滤器的执行
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //1. 判断请求URL是不是验证码登录请求
        if(!antPathMatcher.match("/smslogin", request.getRequestURI())){
            //说明不是短信验证码登录，那么直接放行，执行后面的过滤器
            filterChain.doFilter(request, response);
            return;
        }

        //2. 如果是，则从请求中获取用户输入的短信验证码
        String codeFromPage = request.getParameter("code");
        if(!StringUtils.hasLength(codeFromPage)){
            //说明前端传来的验证码为空，则走我们之前写的认证失败处理器类
            //myAuthSuccessHandler.onAuthenticationFailure(request, response, new UsernameNotFoundException("验证码不可为空"));
            return;
        }
        String phone = request.getParameter("phone");
        if(!StringUtils.hasLength(phone)){
           // myAuthSuccessHandler.onAuthenticationFailure(request, response, new UsernameNotFoundException("手机号不可为空"));
            return;
        }

        //3. 从session中拿到短信验证码
        SmsCodeDto smsCodeDto = (SmsCodeDto) request.getSession().getAttribute("loginSmsCode");
        if(null == smsCodeDto){
            //说明没有发送过验证码或者过期了
           // myAuthSuccessHandler.onAuthenticationFailure(request, response, new UsernameNotFoundException("验证码无效或过期，请重试"));
            return;
        }

        //4. 进行比对，如果验证码错误，则走认证失败的Handler
        if(!smsCodeDto.getCode().equals(codeFromPage)){
            //验证码比对错误
           // myAuthSuccessHandler.onAuthenticationFailure(request, response, new UsernameNotFoundException("验证码错误"));
            return;
        }

        //5. 如果验证码正确，则继续后面过滤器的执行
        filterChain.doFilter(request, response);
    }
}