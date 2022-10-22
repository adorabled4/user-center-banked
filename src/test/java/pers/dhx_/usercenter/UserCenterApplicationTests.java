package pers.dhx_.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.dhx_.usercenter.model.domain.User;
import pers.dhx_.usercenter.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class UserCenterApplicationTests {

    @Resource
    UserMapper usermapper;
    @Test
    public void mybatisTest(){
        List<User> users = usermapper.selectList(null);
        users.forEach(System.out::println);
    }

}
