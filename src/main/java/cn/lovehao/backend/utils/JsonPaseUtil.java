package cn.lovehao.backend.utils;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author zh
 */
public class JsonPaseUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T>  T decode(String str,Class<T> clazz){
        T t = null;
        try {
           t = MAPPER.readValue(str,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T extends Object> String encode(T obj){
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> T decode(String str,Class<T> t,Class clazzK,Class clazzV){
        JavaType jvt = MAPPER.getTypeFactory().constructParametricType(t,clazzK,clazzV);
        try {
           return MAPPER.readValue(str, jvt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
