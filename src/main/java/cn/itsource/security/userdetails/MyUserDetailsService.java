package cn.itsource.security.userdetails;


import cn.itsource.domain.Permission;
import cn.itsource.domain.User;
import cn.itsource.mapper.PermissionMapper;
import cn.itsource.mapper.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.username基本判断
        if(!StringUtils.hasLength(username)){
            throw new UsernameNotFoundException("用户名不可为空");
        }
        //2.根据username查询mysql中的认证信息
        User userFromMysql = userMapper.selectByUsername(username);
        if(userFromMysql == null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        //3.加载用户的权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Permission> permissions = permissionMapper.selectPermissionsByUserId(userFromMysql.getId());
        for (Permission permission : permissions){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission.getExpression());
            System.out.println("加载用户 [" + username + "] 的权限信息："+permission.getExpression());
            authorities.add(simpleGrantedAuthority);
        }

        //4.把认证信息和权限信息封装成UserDetails返回
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(username,userFromMysql.getPassword(),authorities);

        return userDetails;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        System.out.println(bc.encode("123456"));
        System.out.println(bc.encode("123456"));
        System.out.println(bc.encode("123456"));
        System.out.println(bc.encode("123456"));
        System.out.println(bc.encode("123456"));
        System.out.println(bc.encode("123456"));
        System.out.println(bc.encode("123456"));
    }

}