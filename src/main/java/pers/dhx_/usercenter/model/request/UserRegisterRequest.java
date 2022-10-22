package pers.dhx_.usercenter.model.request;


import lombok.Data;
import org.apache.ibatis.javassist.SerialVersionUID;

import java.io.Serializable;

/**
 * @author Dhx_
 * @className UserRegister
 * @description TODO 用户注册请求体 implements Serializable防止序列化冲突
 * @date 2022/9/24 17:38
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -7089675522966087515L;
    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String plantCode;
}
