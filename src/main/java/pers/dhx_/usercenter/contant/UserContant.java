package pers.dhx_.usercenter.contant;

/**
 * @author Dhx_
 * @className UserContant
 * @description TODO  常量类使用interface ,因为接口中的属性默认是 public static
 * @date 2022/9/24 20:56
 */
public interface UserContant {

    String USER_LOGIN_STATE="userLoginState";

    String SALT="dhx"; // 用于混淆密码

    //-------------- 权限常量
    //默认权限
    int DEFAULT_ROLE=0;
    // 管理员默认权限
    int ADMIN_ROLE=1;
}
