package cn.itsource.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    //登录成功后重定向地址
    @PostMapping("/loginSuccess")
    public String loginSuccess(){
        return "登录成功";
    }
}