package com.hlz;

import com.hlz.service.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Administrator 2017-3-3
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Bean
    UserDetailsService customUserService(){
        return new CustomUserService();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserService());//添加自定义的user detail Service认证
    }
    //请求授权
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()//通过这个方法开始请求配置
                .antMatchers("/admin/**").hasRole("0")
                .anyRequest()
                .authenticated()//所有请求需要认证才能访问
                .and()
                .formLogin()//定制登录
                .loginPage("/login")
                .defaultSuccessUrl("/index")//登录成功后转向的界面
                .failureUrl("/login?error")
                .permitAll()
                .and()
                //注销的url以及注销成功后转向的页面
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .permitAll();
    }
}
