package cn.lovehao.backend.config.mybatis;

import cn.lovehao.backend.utils.UUIDUtils;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;


/**
 * id 生成器
 */
@Component
public class MyIdGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        return null;
    }

    @Override
    public String nextUUID(Object entity) {
        return UUIDUtils.getId();
    }
}
