package cn.itsource.mapper;

import cn.itsource.domain.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wujiangbo
 * @since 2021-11-20
 */
public interface UserMapper extends BaseMapper<User> {

    User selectByUsername(String username);
}
