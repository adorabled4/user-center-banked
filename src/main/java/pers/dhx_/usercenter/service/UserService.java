package pers.dhx_.usercenter.service;

import pers.dhx_.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author lenovo
* @description 针对表【user】的数据库操作Service
* @createDate 2022-09-23 18:48:34
*/
public interface UserService extends IService<User> {
    // 用户登录态键

    /**
     *
     * @param userAccount 账户
     * @param userPassword  密码
     * @param request 请求 :用于保存session
     * @return
     */
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) ;

    /**
     *
     * @param userAccount 用户账户
     * @param userPassword 密码
     * @param checkPassword 校验密码
     * @return
     */
    Long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode);

    /**
     *
     */
    List<User> searchUsers(String userName);
    User getSafetyUser(User originUser);  // 用戶脫敏

    /**
     * 退出登录
     * @param request
     * @return
     */
    public int userLogout(HttpServletRequest request);

}
