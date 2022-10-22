package pers.dhx_.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import pers.dhx_.usercenter.Exception.BusinessException;
import pers.dhx_.usercenter.common.ErrorCode;
import pers.dhx_.usercenter.contant.UserContant;
import pers.dhx_.usercenter.model.domain.User;
import pers.dhx_.usercenter.service.UserService;
import pers.dhx_.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
//org.apache.commons.lang3.StringUtils
/**
* @author lenovo
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-09-23 18:48:34
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    UserMapper userMapper;


    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 校验, 减少数据库的查询次数,直接查询数据库会造成浪费
        if(StringUtils.isAnyBlank(userAccount, userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户密码不能为空");//参数错误
        }
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名过短");//参数错误

        }
        if(userPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码过短");//参数错误
        }
        String regExp = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher= Pattern.compile(regExp).matcher(userAccount);
        if(matcher.find()) { // 用户名不符合正则
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名不符合规范");//参数错误
        }
        //2.加密
        String handledPassword= DigestUtils.md5DigestAsHex((UserContant.SALT + userPassword).getBytes());// md5加密密码
        // 查询账户是否存在
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        log.info(handledPassword);
        wrapper.eq("user_password",handledPassword);// 设置查询条件
        wrapper.eq("user_account",userAccount);// 设置查询条件
        User user=userMapper.selectOne(wrapper); // 查询
        // 可能用户不存在 or 密码错了
        if(user==null){
            log.info("user login field , userAccount can't match userPassword");
            throw new BusinessException(ErrorCode.NULL_ERROR,"用户未注册");//数据库中没有数据
        }
        // 查询登录次数, 限制同个ip 多次登录 (以后再说)
        //3.用户脱敏(新生成一个对象, 只返回希望返回给前端的值)
        User safetyUser=getSafetyUser(user);

        //4. 记录用户的登录态
        HttpSession session = request.getSession();
        session.setAttribute(UserContant.USER_LOGIN_STATE,safetyUser);
        return safetyUser;
    }

    /**
     * 用户脱敏方法
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser){
        if(originUser==null){ // 參數為空就提前返回
            throw new BusinessException(ErrorCode.PARAMS_ERROR);//参数为空
        }
        User safetyUser = new User();
        safetyUser.setUserId(originUser.getUserId());
        safetyUser.setUserName(originUser.getUserName());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPlanetCode(originUser.getPlanetCode()); // 星球编号
//        safetyUser.setUserPassword();
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUserRole(originUser.getUserRole());
//        safetyUser.setUpdateTime(new Date());
//        safetyUser.setIsDelete(0);
        return safetyUser;
    }
    /**
     *
     * @param userAccount      用户账号
     * @param userPassword    注册的密码
     * @param checkPassword  校验密码
     * @return 先让 不正确的注册返回-1
     */
    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode) {
        // 1.校验 : 前端以及后端都需要校验, 因为有的情况可以直接绕过前端
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入的密码不同");//参数错误
        }
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名过短");//参数错误

        }
        if(userPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码过短");//参数错误
        }
        if(planetCode.length()>6){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号过长");//参数错误
        }
        // 账户不能重复
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("user_account",userAccount);
        long count=userMapper.selectCount(wrapper);
        if(count>0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户名不能重复");//参数错误
        }
        String regExp = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher= Pattern.compile(regExp).matcher(userAccount);
        if(matcher.find()) { // 用户名不符合正则
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名不符合规范");//参数错误
        }
        //2.加密
        String handledPassword= DigestUtils.md5DigestAsHex((UserContant.SALT + userPassword).getBytes());// md5加密密码
        //3.保存用户数据
        User user=new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(handledPassword);
        user.setPlanetCode(handledPassword);
        boolean saveResult=this.save(user);
        if(!saveResult){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户注册失败");// 数据库中没有数据
        }
        return user.getUserId(); // 返账保存的用户的ID
    }

    @Override
    public List<User> searchUsers(String userName) {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        if(StringUtils.isNoneBlank(userName)){
//            wrapper.eq("user_name",userName); 如果加上这个 , 就是必须要等于 userName
            wrapper.like("user_name",userName);
        }
        List<User> users=this.getBaseMapper().selectList(wrapper);
        return  users.stream().map(user->{
            user=getSafetyUser(user);// 注意不要直接返回带有密码的用户数据
            return user;
        }).collect(Collectors.toList());
    }


    /**
     * 用户注销
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(UserContant.USER_LOGIN_STATE);
        return 1;
    }
}




