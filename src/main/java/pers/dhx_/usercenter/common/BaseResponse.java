package pers.dhx_.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Dhx_
 * @className BaseResponse
 * @description TODO
 * @date 2022/9/27 19:57
 */
@Data
public class BaseResponse<T> implements Serializable {

    private  int code;
    private T data; //  controller 中的不同的方法返回值的類型不同
    private String message;
    private String description;
    public BaseResponse(int code, T data, String message,String description) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
    public BaseResponse(int code, T data ,String description ) {
        this(code,data,"",null);
    }

    public BaseResponse(ErrorCode errorCode){
        this(errorCode.code,null,errorCode.message, errorCode.description);
    }
}
