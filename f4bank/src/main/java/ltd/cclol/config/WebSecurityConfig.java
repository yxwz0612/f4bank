package ltd.cclol.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ltd.cclol.aop.JwtTokenFilter;
import ltd.cclol.common.R;
import ltd.cclol.common.ResultCode;
import ltd.cclol.utils.MyJwt;
import ltd.cclol.utils.MyMd5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 *  Spring security的主要配置文件
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyJwt myJwt;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JwtTokenFilter jwtTokenFilter;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    MyMd5 myMd5;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()//csrf跨站防御配置
                .disable();//关闭

       /* // ↓关闭权限系统
       http.authorizeRequests()
                .anyRequest().permitAll();*/

        // ↓开启权限系统
        http.formLogin()//设置为表单登录模式
                .usernameParameter("username")//前端表单name属性值:username
                .passwordParameter("password")//                password
                .loginProcessingUrl("/form")//登陆表单提交地址
                .successHandler(new AuthenticationSuccessHandler() {//用户登录成功后执行的自定义操作
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        String username = SecurityContextHolder.getContext().getAuthentication().getName();//获得用户的用户名
                       // System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());//打印登录用户的权限
                        String token = myJwt.createToken(username);//生成一个自定义token
                        String r = objectMapper.writeValueAsString(new R(token));//这里直接操作servlet需把要返回的结果格式化为json
                        httpServletResponse.setContentType("application/json;charset=UTF-8");//response body的编码格式设置为json utf-8
                        httpServletResponse.getWriter().write(r);//response body中返回生成的json
                      //httpServletResponse.addHeader("token",token)//response header中直接放入token
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {//用户登录失败后进行的自定义操作
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=UTF-8");//response body的编码格式设置为json utf-8
                        String r = objectMapper.writeValueAsString(new R(new ResultCode("用户名或密码错误")));//这里直接操作servlet需把要返回的结果格式化为json
                        httpServletResponse.getWriter().write(r);//response body中返回生成的json
                    }
                })
                .permitAll();//以上任何权限都可以访问

        http.authorizeRequests()//配置权限
                .antMatchers("/banks/**").hasAnyRole("admin")//所有bank请求的url 需要admin权限
                .antMatchers("/users/**").hasAnyRole("user")//所有user请求的url  需要user权限
                .antMatchers("/").permitAll()//主页所有人可以登陆
                .anyRequest().authenticated();//任何url请求都需要登录

        http.logout()//注销配置
                .logoutUrl("/logout")//注销url
                .logoutSuccessUrl("/bye")//注销成功后执行url
                .invalidateHttpSession(true);//清除session

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);//把自定义jwt验证放在默认的username验证之前

        //关闭默认基于session的登录管理,如果用户request header中没有合法token就需要登录
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()//设置无需经过权限过滤的内容
                .antMatchers("/static")
                .antMatchers("/css/**", "/js/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence charSequence) {
                        //好像没啥用
                        return null;
                    }

                    @Override
                    public boolean matches(CharSequence charSequence, String s) {
                       /* charSequence:用户提交输入的密码
                        * s:根据用户名所查询到的实体的密码
                        * 这个方法的返回值决定了用户登录操作成功还是失败
                        */
                        if (s != null && !s.isEmpty()){
                            return s.equals(myMd5.toMd5WithSalt(charSequence.toString()));
                        }
                        return false;
                    }
                });
    }
}