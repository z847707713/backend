package cn.lovehao.backend.config.session;

import cn.lovehao.backend.entity.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class ServletRequestHandlerEventListener implements ApplicationListener<ServletRequestHandledEvent> {

    @Autowired
    SessionManager sessionManager;

    @Autowired
    UserSessionManager userSessionManager;

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        //获取sessionid
        String sessionId = event.getSessionId();
        //根据sessionID 获取session
        HttpSession session = sessionManager.get(sessionId);
        //添加用户session
        SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if(securityContext != null){
            Object principal = securityContext.getAuthentication().getPrincipal();
            if(principal != null && principal instanceof UserDetails){
                UserDetails userDetails = (UserDetails) principal;
                userSessionManager.add(userDetails.getId(),session);
            }
        }
    }


}
