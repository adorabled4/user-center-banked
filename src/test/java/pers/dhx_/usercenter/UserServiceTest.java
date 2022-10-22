package pers.dhx_.usercenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.dhx_.usercenter.model.domain.User;
import pers.dhx_.usercenter.service.UserService;

import javax.annotation.Resource;

/**
 * @author Dhx_
 * @className UserviceTesst
 * @description TODO
 * @date 2022/9/23 18:50
 */
@SpringBootTest
public class UserServiceTest {


    @Resource
    UserService userService;
    @Test
    void testUserService(){ // 测试save
        User user=new User();
        user.setUserName("dhx_");
        user.setUserAccount("12423");
        user.setUserPassword("qwer");
        user.setGender(0);
        boolean result=userService.save(user); // save返回是否添加成功
        Assertions.assertEquals(true,result);
        System.out.println(user.getUserId());
    }

//    @Test
//    void RegisterTest(){// 测试注册功能
//        long result;
//        String checkPassword;
//        User user=new User();
//        // 测试密码不重复
//        user.setUserAccount("dhxTest");
//        user.setUserPassword("123");
//        checkPassword="123476867";
//        result=userService.userRegister(user.getUserAccount(),user.getUserPassword(),checkPassword);
//        Assertions.assertEquals(-1,result);
//        // 测试用户已经存在
//        user.setUserAccount("dhx_");
//        user.setUserPassword("123");
//        checkPassword="123";
//        result=userService.userRegister(user.getUserAccount(),user.getUserPassword(),checkPassword);
//        Assertions.assertEquals(-1,result);
//        //测试特殊字符
//        user.setUserAccount("256^i{{}|{*%(!@)(_");
//        user.setUserPassword("123");
//        checkPassword="123";
//        result=userService.userRegister(user.getUserAccount(),user.getUserPassword(),checkPassword);
//        Assertions.assertEquals(-1,result);
//        // 测试注册功能
//        user.setUserAccount("26844654");
//        user.setUserPassword("woshidhx123");
//        checkPassword="woshidhx123";
//        result=userService.userRegister(user.getUserAccount(),user.getUserPassword(),checkPassword);
//        Assertions.assertTrue(result>0);
//    }
}
