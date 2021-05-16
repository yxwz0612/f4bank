package ltd.cclol.aop;

import ltd.cclol.utils.MyJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    MyJwt myJwt;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (token != null && myJwt.checkTokenFromToken(token)){
            String username = myJwt.getUserNameFromToken(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                userDetailsService.loadUserByUsername(username);//在此轮验证中放入该username的userDetail
            }
        }
        filterChain.doFilter(request,response);
    }
}
