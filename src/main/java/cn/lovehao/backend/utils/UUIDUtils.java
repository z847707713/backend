package cn.lovehao.backend.utils;

import java.util.UUID;

/**
 * @author zh
 */
public class UUIDUtils {

    public static String getId(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

}
