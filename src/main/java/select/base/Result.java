package select.base;

import java.io.Serializable;

/**
 * @author yeyuting
 * @create 2021/1/25
 */
//前端返回对象
public class Result implements Serializable {
    private static final long serialVersionUID = 1430633339880116031L;

    //成功与否标志
    private boolean success = true ;

    //返回状态码 为空则默认200 还有例如403 404 500等
    private Integer status ;

    //编码
    private String code ;

    //相关消息
    private String msg ;

    //相关数据
    private  Object data ;

    public Result(){}

    public Result(boolean success){
        this.success = success ;
    }
    public Result(boolean success , Integer status) {
        this.success = success ;
        this.status = status ;
    }
    public Result(boolean success , String code , String msg) {
        this.status = status ;
        this.success = success ;
        this.msg = msg ;
    }
    public Result(boolean success , Integer status , String msg , String code){
        this.success = success;
        this.msg = msg ;
        this.status = status ;
        this.code = code ;
    }
    public Result(Boolean success , String msg , String code , Object data){
        this.success = success ;
        this.code = code ;
        this.data = data ;
        this.msg = msg ;
    }
    public boolean isSuccess(){
        return success ;
    }
    public void setSuccess(boolean success){
        this.success = success ;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
