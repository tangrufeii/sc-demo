package cn.itsource.service.impl;

import cn.itsource.domain.User;
import cn.itsource.mapper.UserMapper;
import cn.itsource.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wujiangbo
 * @since 2021-11-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
