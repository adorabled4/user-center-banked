package pers.dhx_.usercenter.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.dhx_.usercenter.model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author lenovo
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-09-23 18:48:34
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




