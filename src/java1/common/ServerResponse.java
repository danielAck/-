package java1.common;
import java.io.Serializable;

/**
 * Created by ÕÅ°Øèë on 2018/3/1.
 */
public class ServerResponse<T> {

    private String msg;
    private Integer status;
    private T data;

    public ServerResponse(){
        this.msg = null;
        this.status = null;
        this.data = null;
    }

    ServerResponse(String msg, Integer status, T data){
        this.msg = msg; this.status = status; this.data = data;
    }

    ServerResponse(String msg, Integer status){
        this.msg = msg; this.status = status;
    }

    ServerResponse(Integer status){
        this.status = status;
    }

    public boolean isSuccess(){
        return this.status == 1;
    }

    public static <T> ServerResponse<T> createBySuccessMsg(String msg){
        return new ServerResponse<T>(msg, 1);
    }

    public static <T> ServerResponse<T> createBySuccessMsgAndData(String msg, T data){
        return new ServerResponse(msg, 1, data);
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse(1);
    }

    public static <T> ServerResponse<T> createByErrorMsg(String msg){
        return new ServerResponse<T>(msg, 0);
    }

    public static <T> ServerResponse<T> createByErrorMsgAndData(String msg, T data){
        return new ServerResponse(msg, 0, data);
    }

    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse(0);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
