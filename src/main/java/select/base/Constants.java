package select.base;


/**
 * @author yeyuting
 * @create 2021/1/26
 */
public class Constants {
    //redis存储token的过期时间
    public static final Integer TOKEN_EXPIRED_TIME = 6000*2 ;
    //设置可以 设置token过期时间的时间界限
    public static final Integer TOKEN_RESET_TIME = 1000000*100 ;
}
