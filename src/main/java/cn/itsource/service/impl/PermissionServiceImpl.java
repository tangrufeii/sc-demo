package cn.itsource.service.impl;

import cn.itsource.domain.Permission;
import cn.itsource.mapper.PermissionMapper;
import cn.itsource.service.IPermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
