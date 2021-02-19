package select.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;
import select.base.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yeyuting
 * @create 2021/1/26
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    TokenUtil tokenUtil ;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        String token = request.getHeader("token") ;
        Jedis jedis = new Jedis("localhost" , 6379) ;
        if(StringUtils.isEmpty(token)){
            response.sendRedirect("/sys/user/login");
            return false ;
        }
        System.out.println("redis中用户信息值为：" + jedis.get(token));

        String userName = jedis.get(token) ;
        System.out.println("token对应用户名字："+ userName );
        if(userName!=null && !userName.trim().equals("")){
            System.out.println("token匹配成功！");
            Long tokenBirthTime = Long.valueOf(jedis.get(userName+token)) ;
            Long diff = System.currentTimeMillis()-tokenBirthTime ;
            if(diff >  Constants.TOKEN_RESET_TIME){
                jedis.expire(userName , Constants.TOKEN_EXPIRED_TIME) ;
                jedis.expire(token , Constants.TOKEN_EXPIRED_TIME) ;
                System.out.println("有效时间更新！");
                Long newBirthTime = System.currentTimeMillis() ;
                jedis.set(userName+token , newBirthTime.toString()) ;
            }
            return true ;
        }

        response.sendRedirect("/sys/user/login");
        return false ;
    }



}
