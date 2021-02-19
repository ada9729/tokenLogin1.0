package select.constants;

import select.base.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yeyuting
 * @create 2021/1/25
 */
public enum  BaseEnums implements BaseEnum<String , String > {

    SUCCESS("request.success", "请求成功"),
    FAILURE("request.failure", "请求失败"),

    OPERATION_SUCCESS("operation.success", "操作成功"),

    OPERATION_FAILURE("operation.failure", "操作失败"),

    ERROR("system.error", "系统异常"),

    NOT_FOUND("not_found", "请求资源不存在"),

    FORBIDDEN("forbidden", "无权限访问") ;


    private String code;

    private String desc;


    BaseEnums(String code , String desc ){
        this.code = code ;
        this.desc = desc ;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String desc() {
        return desc;
    }
}
