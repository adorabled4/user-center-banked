package pers.dhx_.usercenter.model.request;

import lombok.Data;
import org.apache.ibatis.javassist.SerialVersionUID;

import java.io.Serializable;

/**
 * @author Dhx_
 * @className UserLoginRequest
 * @description TODO
 * @date 2022/9/24 18:43
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 4952290868836068800L;
    private String userAccount;
    private String userPassword;
}
