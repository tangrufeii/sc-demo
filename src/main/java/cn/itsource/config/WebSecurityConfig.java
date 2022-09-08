 package cn.itsource.config;

import cn.itsource.domain.Permission;
import cn.itsource.mapper.PermissionMapper;
import cn.itsource.security.handler.MyAccessDeniedHandler;
import cn.itsource.security.handler.MyAuthenticationSuccessHandler;
import cn.itsource.security.userdetails.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;
import java.util.List;

 @Configuration
@EnableWebSecurity
 @EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
     //开始配置记住我功能代码
     @Autowired
     private DataSource dataSource;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private MyAuthenticationSuccessHandler resultHandler;
    @Autowired
    private MyAccessDeniedHandler deniedHandler;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    //提供用户信息，这里没有从数据库查询用户信息，在内存中模拟
/*    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("zs").password("123456").authorities("admin").build());
        return inMemoryUserDetailsManager;
    }*/

     @Bean
     public JdbcTokenRepositoryImpl jdbcTokenRepository(){
         JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
         repository.setDataSource(dataSource);
         repository.setCreateTableOnStartup(true);//启动时会自动创建表
         return repository;
     }
    //密码编码器：不加密
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }




    //授权规则配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
         //设置remember
        http.rememberMe()
                        .tokenRepository(jdbcTokenRepository())
                                .tokenValiditySeconds(60*60)
                .userDetailsService(myUserDetailsService);
       //web授权
/*        List<Permission> permissionsList = permissionMapper.selectList(null);
        for (int i = 0; i < permissionsList.size(); i++) {
            Permission permission = permissionsList.get(i);
            //告诉security 资源路径和权限字符串的关系
            http.authorizeRequests()
                    .antMatchers(permission.getResource())//匹配资源路径
                    .hasAnyAuthority(permission.getExpression());//制定该资源路径下需要什么样的权限才能访问
        }*/

        //方法授权直接走异常

        //web授权才能走登录权限不足方法
        //处理授权失败结果处理
        http.exceptionHandling()
                .accessDeniedHandler(deniedHandler)
                .authenticationEntryPoint(deniedHandler)
                .configure(http.authorizeRequests().antMatchers("/login","/login.html").permitAll()
                        .anyRequest().authenticated()                   //其他路径都要认证之后才能访问
                        .and().formLogin()
                        //允许表单登录
                        .loginPage("/login.html")
                        .loginProcessingUrl("/abc")
                        .successHandler(resultHandler)
                        .failureHandler(resultHandler)
                        //.successForwardUrl("/loginSuccess")             // 设置登陆成功页
                        .and().logout().permitAll()                    //登出路径放行 /logout
                        .and().csrf().disable());                        //关闭跨域伪造检查);
                ;
/*        http.authorizeRequests()                                //授权配置
                .antMatchers("/login").permitAll()  //登录路径放行
                .anyRequest().authenticated()                   //其他路径都要认证之后才能访问
                .and().formLogin()
                //允许表单登录
                .successHandler(resultHandler)
                .failureHandler(resultHandler)
                //.successForwardUrl("/loginSuccess")             // 设置登陆成功页
                .and().logout().permitAll()                    //登出路径放行 /logout
                .and().csrf().disable();                        //关闭跨域伪造检查
    }*/
    }
}