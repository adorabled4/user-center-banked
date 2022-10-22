package pers.dhx_.usercenter.utils;

import pers.dhx_.usercenter.common.BaseResponse;
import pers.dhx_.usercenter.common.ErrorCode;

/**
 * @author Dhx_
 * @className ResultUtils
 * @description TODO 返回工具類
 * @date 2022/9/27 20:05
 */
public class ResultUtils {
    /**
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok","");
    }

    /**
     * 出现错误
     * @param errorCode
     * @return
     */
    public static   BaseResponse error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }
}
