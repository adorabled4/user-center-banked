package pers.dhx_.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.dhx_.usercenter.mapper.UserMapper;
import pers.dhx_.usercenter.model.domain.User;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Dhx_
 * @className MybastisContectedTest
 * @description TODO
 * @date 2022/9/22 19:28
 */
@SpringBootTest
public class MybatisContectedTest {
    /*
    * Resource会默认按照Java的名称去注入属性，如果是Autowired话，只会按照类型去注入属性，所以一般用@Resource来自动注入
* */
    //在单元测试中，需要添加 @RunWith(SpringRunner.class) 才可完成测试
    @Resource
    UserMapper userMapper;
    @Test
    public void mybatisTest(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}
