package cn.lovehao.backend.config.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.authentication.session.SessionFixationProtectionEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class SessionFixationProtectionEventListener implements ApplicationListener<SessionFixationProtectionEvent> {

    @Autowired
    SessionManager sessionManager;

    @Override
    public void onApplicationEvent(SessionFixationProtectionEvent sessionFixationProtectionEvent) {
        String oldId = sessionFixationProtectionEvent.getOldSessionId();
        String newId = sessionFixationProtectionEvent.getNewSessionId();
        HttpSession httpSession = sessionManager.get(oldId);
        sessionManager.remove(oldId);
        sessionManager.add(httpSession);
    }
}
