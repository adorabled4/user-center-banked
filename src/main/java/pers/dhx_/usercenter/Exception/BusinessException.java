package pers.dhx_.usercenter.Exception;

import pers.dhx_.usercenter.common.ErrorCode;

/**
 * @author Dhx_
 * @className BusinessException  自定义业务异常类
 * @description TODO 支持更多字段,  更加灵活便捷
 * @date 2022/9/27 20:41
 */
public class BusinessException extends RuntimeException{

    private final int code; // 错误码

    private final String description; // 描述


    public BusinessException(int code, String description,String message){
        super(message);// 错误信息
        this.code=code;
        this.description=description;
    }

    public BusinessException(ErrorCode errorCode,String description){
        super(errorCode.getMessage());// 错误信息
        this.code=errorCode.getCode();
        this.description= description;
    }
    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());// 错误信息
        this.code=errorCode.getCode();
        this.description= errorCode.getDescription();
    }
}
