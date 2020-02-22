package cn.lovehao.backend.config.session;

import cn.lovehao.backend.entity.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  用户session 管理
 *  方便对指定用户进行session 相关处理
 */
@Component
@Slf4j
public class UserSessionManager {

    @Autowired
    SessionManager sessionManager;

    //存储用户session,一个用户可能有多个session，多个地方登录
    private ConcurrentHashMap<String,List<HttpSession>> userSessions = new ConcurrentHashMap<>();

    /**
     *  为指定用户列表 添加 session
     * @param id
     * @param session
     */
    public void add(String id,HttpSession session){
       if(contains(id,session)){
           return;
       }
       if(!userSessions.containsKey(id)){
          userSessions.put(id, Collections.synchronizedList(new ArrayList<>()));
       }
       log.info("添加userSession:[{}:{}]",id,session.getId());
       userSessions.get(id).add(session);
    }

    /**
     * 根据用户id 获取 该用户的 session 列表
     * @param userId
     * @return
     */
    public List<HttpSession> get(String userId){
        return userSessions.containsKey(userId) ? userSessions.get(userId) : null;
    }

    /**
     *  指定用户是否包含 该session
     * @param id
     * @param session
     * @return
     */
    public boolean contains(String id,HttpSession session){
        if(!userSessions.containsKey(id)){
            return false;
        }
        return userSessions.get(id).contains(session);
    }

    /**
     *  清除集合中的 session
     * @param session
     * @return
     */
    public boolean remove(HttpSession session){
        if(session != null){
            SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
            if(securityContext != null){
                Object principal = securityContext.getAuthentication().getPrincipal();
                if(principal != null && principal instanceof UserDetails){
                    UserDetails userDetails = (UserDetails) principal;
                    List<HttpSession> sessions = get(userDetails.getId());
                    if(sessions != null){
                        log.info("成功销毁 session:[{}:{}]",userDetails.getId(),session.getId());
                        return sessions.remove(session);
                    }
                }
            }
        }
        return false;
    }

    /**
     * 刷新在线用户权限
     * @param id
     */
    public void reloadUserAuthority(String id){

        // 根据用户id 查询该用户的权限
        List<GrantedAuthority> authorityList = new ArrayList<>();

        //授权
        List<HttpSession> curentUserSessions = get(id);

        for(HttpSession session :curentUserSessions){
            SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
            Authentication authentication = securityContext.getAuthentication();
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            // 重新new一个token，因为Authentication中的权限是不可变的.
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                    principal.getUsername(), authentication.getCredentials(),
                    authorityList);
            result.setDetails(authentication.getDetails());
            securityContext.setAuthentication(result);
        }

    }

    /**
     *  销毁指定用户的所有session
     * @param userId
     */
    public void destroy(String userId){
        if(userSessions.containsKey(userId)){
            List<HttpSession> sessions = userSessions.get(userId);
            for(HttpSession session : sessions){
                //从sessionManager 中移除该 session
                sessionManager.remove(session);
                //销毁
                session.invalidate();
            }
            userSessions.remove(userId);
        }
    }


}
