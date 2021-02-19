package select.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import select.base.Constants;
import select.system.dto.User;

import java.util.List;

/**
 * @author yeyuting
 * @create 2021/1/26
 */
@Component
public class JedisUtil {
    public void tokenToJedis(User user){
        Jedis jedis = getSource() ;
        jedis.set(user.getUserName() , user.getToken()) ;
        jedis.expire(user.getUserName() , Constants.TOKEN_EXPIRED_TIME) ;
        jedis.set(user.getToken() , user.getUserName()) ;
        jedis.expire(user.getToken() , Constants.TOKEN_EXPIRED_TIME) ;
        Long currentTime = System.currentTimeMillis() ;
        jedis.set(user.getUserName()+user.getToken() ,currentTime.toString()) ;

        jedis.close();
        System.out.println("---"+ jedis.get(user.getUserName()));
        System.out.println("--"+ jedis.get(user.getToken()));

    }

    public void delString(String key){
        try {
            Jedis jedis = getSource() ;
            ScanParams scanParams = new ScanParams() ;
            StringBuilder paramKey = new StringBuilder("*").append(key).append("*") ;
            scanParams.match(paramKey.toString()) ;
            scanParams.count(1000) ;
            ScanResult<String> sr = jedis.scan("0" , scanParams) ;
            List<String> list = sr.getResult() ;
            for(String delKey : list){
                jedis.del(delKey) ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public Jedis getSource(){
        return new Jedis("localhost" , 6379) ;
    }

}
