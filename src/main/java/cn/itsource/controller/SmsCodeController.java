package cn.itsource.controller;


import cn.itsource.dto.SmsCodeDto;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

//发送短信验证码
@RestController
public class SmsCodeController {

    @GetMapping(value = "/sms/send/{phone}")
    public void sendSms(@PathVariable("phone") String phone, HttpServletRequest request){
        String code = UUID.randomUUID().toString().substring(0,4);
        request.getSession().setAttribute("loginSmsCode",new SmsCodeDto(phone,code,new Date().getTime()));
        System.out.println("手机验证码为："+code);
    }
}