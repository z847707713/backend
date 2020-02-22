package cn.lovehao.backend.config.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  session 管理，保存所有session,提供基本的添加删除和查找
 */
@Component
@Slf4j
public class SessionManager {

    private ConcurrentHashMap<String, HttpSession> sessions = new ConcurrentHashMap<>();

    /**
     * 添加session
     * @param session
     */
    public void add(HttpSession session){
        log.info("添加session:{}" , session.getId());
        sessions.put(session.getId(),session);
    }

    public void remove(HttpSession session){
        remove(session != null ? session.getId() : null);
    }

    public void remove(String id){
        if(sessions.contains(id)){
            sessions.remove(id);
        }
    }

    public HttpSession get(String sessionId){
        return sessions.containsKey(sessionId) ? sessions.get(sessionId) : null;
    }

}
