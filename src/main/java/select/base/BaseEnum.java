package select.base;

/**
 * @author yeyuting
 * @create 2021/1/25
 */
public interface BaseEnum<K , V> {
    //获取编码
    K code() ;

    //获取描述
    K desc() ;

}
