package cn.lovehao.backend.config.mybatis;

import cn.lovehao.backend.utils.SpringSecurityUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *  配置 mybatis plus 自动填充字段
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final String FIELD_CREATE_TIME = "createTime";

    private static final String FIELD_CREATE_BY = "createBy";

    private static final String FIELD_UPDATE_TIME = "updateTime";

    private static final String FIELD_UPDATE_BY = "updateBy";

    /**
     *  insert 时填充 实现
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        String userId = SpringSecurityUtil.getCurrentUserId();
        if (metaObject.hasSetter(MyMetaObjectHandler.FIELD_CREATE_TIME)) {
            metaObject.setValue(MyMetaObjectHandler.FIELD_CREATE_TIME,date);
        }
        if (metaObject.hasSetter(MyMetaObjectHandler.FIELD_CREATE_BY)) {
            metaObject.setValue(MyMetaObjectHandler.FIELD_CREATE_BY,userId);
        }
        update(metaObject, date, userId);
    }

    /**
     * update 时 填充实现
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Date date = new Date();
        String userId = SpringSecurityUtil.getCurrentUserId();
        update(metaObject, date, userId);
    }

    private void update(MetaObject metaObject, Date date, String userId) {
        if (metaObject.hasSetter(MyMetaObjectHandler.FIELD_UPDATE_TIME)) {
            metaObject.setValue(MyMetaObjectHandler.FIELD_UPDATE_TIME,date);
        }
        if (metaObject.hasSetter(MyMetaObjectHandler.FIELD_UPDATE_BY)) {
            metaObject.setValue(MyMetaObjectHandler.FIELD_UPDATE_BY,userId);
        }
    }
}
