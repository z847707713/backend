package cn.lovehao.backend.utils;

import cn.lovehao.backend.entity.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.UnsupportedEncodingException;

public class SpringSecurityUtil {

    public static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return "";
        }
        return authentication.getName();
    }

    public static String getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
           UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getId();
        }
        return "";
    }



}
