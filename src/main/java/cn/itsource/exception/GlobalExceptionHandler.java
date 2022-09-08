package cn.itsource.exception;

import cn.itsource.result.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理类
@RestControllerAdvice//它就是@ControllerAdvice和@ResponseBody的合并。此注解通过对异常的拦截实现的统一异常返回处理
public class GlobalExceptionHandler {

    //捕获并处理自定义异常
    @ExceptionHandler(MyException.class)
    public JSONResult MyException(MyException e){
        return JSONResult.error(e.getMessage());
    }

    //捕获并处理算术异常
    @ExceptionHandler(ArithmeticException.class)
    public JSONResult methodArithmeticException(ArithmeticException e){
        return JSONResult.error("发生算术异常，请稍后再试");
    }

    /**
     * 如果发生上面没有捕获的异常，那么统一走这个异常捕获，相当于是最大的一个范围
     */
    @ExceptionHandler(Exception.class)
    public JSONResult exceptionHandler(Exception e){
        e.printStackTrace();
        return JSONResult.error("系统异常，请稍后再试");
    }
}
