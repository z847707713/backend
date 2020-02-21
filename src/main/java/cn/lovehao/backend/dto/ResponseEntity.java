package cn.lovehao.backend.dto;

public class ResponseEntity<T> {

    private T data;

    private String code;

    private String msg;

    private Boolean status;

    public ResponseEntity(T data, String code, String msg,Boolean status) {
        this.data = data;
        this.code = code;
        this.msg = msg;
        this.status = status;
    }

    public static <T> ResponseEntity<T>  success(T data){
        return new ResponseEntity<T>(data,"1","success",true);
    }

    public static <T> ResponseEntity<T>  success(){
        return new ResponseEntity<T>(null,"1","success",true);
    }

    public static <T> ResponseEntity<T>  error(T data){
        return new ResponseEntity<T>(data,"1","error",false);
    }

    public static <T> ResponseEntity<T>  error(){
        return new ResponseEntity<T>(null,"1","error",false);
    }

    public static <T> ResponseEntity<T>  error(String msg){
        return new ResponseEntity<T>(null,"1",msg,false);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
