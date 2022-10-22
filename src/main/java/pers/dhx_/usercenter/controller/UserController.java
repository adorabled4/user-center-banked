package pers.dhx_.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.dhx_.usercenter.Exception.BusinessException;
import pers.dhx_.usercenter.common.BaseResponse;
import pers.dhx_.usercenter.common.ErrorCode;
import pers.dhx_.usercenter.contant.UserContant;
import pers.dhx_.usercenter.model.domain.User;
import pers.dhx_.usercenter.model.request.UserLoginRequest;
import pers.dhx_.usercenter.model.request.UserRegisterRequest;
import pers.dhx_.usercenter.service.UserService;
import pers.dhx_.usercenter.utils.ResultUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dhx_
 * @className LoginController
 * @description TODO用户接口
 * @date 2022/9/23 18:47
 */
@RestController() //适用于restful风格的API 所有返回值都是 json 格式
@RequestMapping("/user")
public class UserController {

    /*1校验用户账户和密码是否合法 (这都不对，就省去去数据库查询，节省资源)
        非空
        账户长度 不小于 4 位
        密码就 不小于 8 位吧
        账户不包含特殊字符
    2 校验密码是否输入正确，要和数据库中的密文密码去对比
    3用户信息脱敏，隐藏敏感信息，防止数据库中的字段泄露
    4 我们要记录用户的登录态（session），将其存到服务器上
        （用后端 SpringBoot 框架封装的服务器 tomcat 去记录）cookie
    5返回脱敏后的用户信息 */
    @Resource
    UserService userService;

    /**
     * @return 返回用户的ID
     */
    @PostMapping("/register")//@RequestBody
    public BaseResponse<Long> register( UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest==null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR); // 请求参数为null
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String plantCode = userRegisterRequest.getPlantCode();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return null;
        }
        Long result= userService.userRegister(userAccount,userPassword,checkPassword,plantCode);
        return ResultUtils.success(result);
        // 狀態碼 0 表示正常返回
    }

    /**
     * 登录接口
     * @param userLoginRequest
     * @return 返回脱敏后的User
     */
    @PostMapping("/login") //@RequestBody
    public BaseResponse<User> userLogin( UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest==null){ // 请求参数为NULL
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        User result =userService.userLogin(userAccount,userPassword,request);
        return ResultUtils.success(result);
    }

    /**
     * @param request
     * @return
     */
    @PostMapping("/logout") //@RequestBody
    public BaseResponse<Integer> userLogout( HttpServletRequest request){
        if(request==null){ // 请求参数为NULL
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        int result=userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前的 登录的账户
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj=request.getSession().getAttribute(UserContant.USER_LOGIN_STATE);
        User currentUser=(User)userObj;
        if(currentUser==null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
//        long userId=((User) userObj).getUserId(); // 不知道为什么根据id查不到用户
        String userAccount=((User) userObj).getUserAccount();
        //校验用户是否合法
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("user_account",userAccount);
        User user=userService.getBaseMapper().selectOne(wrapper);
        User safetyUser=userService.getSafetyUser(user); // 用戶脫敏
        return ResultUtils.success(safetyUser);
    }
    /**
     * 仅有管理员可以查询  ,注意不能直接把接口开放出去, 需要考虑安全,考虑代码的健壮性, 避免接口被恶意调用
     * @param userName
     * @param request 为了加入验证, 验证是否登录以及 身份(通欧冠保存在session中的 user)
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searcherUsers(String userName,HttpServletRequest request){
        if(!isAdmin(request)){
            return ResultUtils.error(ErrorCode.NO_AUTH);// 没有权限
        }
//         QueryWrapper<User> wrapper=new QueryWrapper<>();
//         if(StringUtils.isNotBlank(userName)){ // 模糊查询
//            wrapper.like("user_name",userName);
//         }
////         List<User> results= userService.searchUsers(userName);
//        List<User> results=userService.getBaseMapper().selectList(wrapper);
        List<User> results=userService.searchUsers(userName);
         return ResultUtils.success(results);
    }

    /**
     * 根据昵称删除
     * @param userName
     * @return 返回被删除的用户的编号, 如果删除失败 , 返回-1
     * 注意不能直接把接口开放出去, 需要考虑安全,考虑代码的健壮性, 避免接口被恶意调用
     */
    @PostMapping("/MyDelete")
    public BaseResponse<Long> deleteUser(String userName){
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("user_name",userName);
        User user=userService.getOne(wrapper);
        if(user==null){
            return ResultUtils.error(ErrorCode.NULL_ERROR);// 数据库中没有数据
        }
        Long userId = user.getUserId();
        userService.remove(wrapper);
        return ResultUtils.success(userId);
    }

    /**
     * 根据 id 删除
     * @param  userId
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(Long userId, HttpServletRequest request){
        if(!isAdmin(request)){
            return ResultUtils.error(ErrorCode.NO_AUTH);//权限不足
        }
        if(userId<=0){
            return ResultUtils.error(ErrorCode.NULL_ERROR);// 数据库中没有数据

        }
//        QueryWrapper<User> wrapper=new QueryWrapper<>();
//        wrapper.eq("user_id",userId);
//        return  userService.remove();
        /*
        mybatis会自动帮助我们转换成逻辑删除
        * */
        boolean result=userService.removeById(userId);
        return ResultUtils.success(result);
    }

    /**
     * 是否为管理员
     * @param request
     * @return
     */
    public boolean isAdmin(HttpServletRequest request){
        Object userObject= request.getSession().getAttribute(UserContant.USER_LOGIN_STATE);
        User user=(User)userObject;
        boolean result=userService.removeById(user==null||user.getUserRole()!= UserContant.ADMIN_ROLE);
//        if(!result){ //不是管理员 或者没有登录(登录会在session中保存user), 返回null
//            return false;
//        }
//        return true;
        return !result;
    }
}
