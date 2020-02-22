package cn.lovehao.backend.config.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HttpSessionCreatedEventListener implements ApplicationListener<HttpSessionCreatedEvent> {

    @Autowired
    SessionManager sessionManager;

    //监听Session 创建事件
    @Override
    public void onApplicationEvent(HttpSessionCreatedEvent httpSessionCreatedEvent) {
        log.info("新建session:{}",httpSessionCreatedEvent.getSession().getId());
        //userSessionManager.add(httpSessionCreatedEvent.getSession());
        sessionManager.add(httpSessionCreatedEvent.getSession());
    }
}
