package cn.lovehao.backend.dto;

public class ResponseEntity<T> {

    private T data;

    private String code;

    private String msg;

    public ResponseEntity(T data, String code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResponseEntity<T>  success(T data){
        return new ResponseEntity(data,"1","success");
    }

    public static <T> ResponseEntity<T>  error(T data){
        return new ResponseEntity(data,"1","error");
    }

    public static <T> ResponseEntity<T>  error(){
        return new ResponseEntity(null,"1","error");
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
}
