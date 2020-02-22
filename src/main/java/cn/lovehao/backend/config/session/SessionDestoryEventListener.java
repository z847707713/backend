package cn.lovehao.backend.config.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SessionDestoryEventListener implements ApplicationListener<HttpSessionDestroyedEvent> {

    @Autowired
    SessionManager sessionManager;

    @Autowired
    UserSessionManager userSessionManager;

    /**
     * 监听session 销毁事件
     * @param httpSessionDestroyedEvent
     */
    @Override
    public void onApplicationEvent(HttpSessionDestroyedEvent httpSessionDestroyedEvent) {
        String id = httpSessionDestroyedEvent.getSession().getId();
        sessionManager.remove(id);
        userSessionManager.remove(httpSessionDestroyedEvent.getSession());
    }

}
