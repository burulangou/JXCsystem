package cn.toesbieya.jxc.ResponseFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Result {
    /**
     * 返回响应值200为响应成功，其余表示失败
     * */
    private int status;
    /**
     * 成功或失败时返回的错误信息
     */
    private String msg;
    /**
     * 成功时返回的数据信息
     */
    private Object data;

    public Result(int status){
        this.status = status;
    }

    public Result(int status,String msg){
        this.status = status;
        this.msg = msg;
    }


    public Result(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    public static Result success(){
        return new Result(200);
    }

    public static Result success(String msg){
        return new Result(200 ,msg);
    }

    public static Result success(Object data){
        return new Result(200 ,data);
    }

    public static Result success(String msg,Object data){
        return new Result(200 ,msg,data);
    }

    public static Result fail(String msg){
        return new Result(500 ,msg);
    }

    public static Result notFound(){
        return new Result(404 ,"not found");
    }

    public static Result unLogin(){
        return new Result(401 ,"UnLogin");
    }

    public static Result noPermission(){
        return new Result(403 ,"No Permission");
    }

    public static Result overload(){
        return new Result(503 ,"Overload");
    }

    public static Result overRequest(){
        return new Result(503 ,"OverRequest");
    }

    public boolean isSuccess(){
        return this.status == 200;
    }
}
