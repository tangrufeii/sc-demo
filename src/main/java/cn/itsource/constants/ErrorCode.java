package cn.itsource.constants;

//系统错误码
public enum ErrorCode {

    SYSTEM_SUCCESS("0000", "操作成功"),
    SYSTEM_ERROR("9999", "系统内部错误");

    //错误码
    private String code;

    //错误信息
    private String message;

    ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
