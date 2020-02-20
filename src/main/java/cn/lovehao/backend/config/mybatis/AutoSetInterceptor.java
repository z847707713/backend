package cn.lovehao.backend.config.mybatis;

import cn.lovehao.backend.utils.SpringSecurityUtil;
import cn.lovehao.backend.utils.UUIDUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

/**
 * @author zh
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class AutoSetInterceptor implements Interceptor {

    private static final String ID = "id";

    private static final String CREATED_BY = "createBy";

    private static final String CREATED_TS = "createTime";

    private static final String UPDATED_BY = "updateBy";

    private static final String UPDATED_TS = "updateTime";


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];


        // 命令类型
        // 如果不是 INSERT H或者是 UPDATE 则直接跳过
        SqlCommandType commandType = ms.getSqlCommandType();
        if (commandType != SqlCommandType.INSERT && commandType != SqlCommandType.UPDATE) {
            return invocation.proceed();
        }

        Object paramObj = invocation.getArgs()[1];
        if (null == paramObj) {
            return invocation.proceed();
        }

        if (paramObj instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) paramObj;
            paramObj = paramMap.get("param1");
        }

        //如果param Obj 是集合类型

        if (paramObj instanceof Collection) {
            Collection<Object> collections = (Collection<Object>) paramObj;
            for (Object obj : collections) {
                doSet(commandType, obj);
            }
        }
        doSet(commandType, paramObj);

        return invocation.proceed();
    }

    private void doSet(SqlCommandType commandType, Object paramObj) throws IllegalAccessException {
        // 获取私有成员变量
        Class<?> clazz = paramObj.getClass();
        Class<?> superclass = clazz.getSuperclass();
        Field[] thisFields = clazz.getDeclaredFields();
        Field[] superFields = null;
        int len = thisFields.length;
        if (null != superclass) {
            superFields = superclass.getDeclaredFields();
            len += superFields.length;
        }


        Field[] totalFields = new Field[len];
        System.arraycopy(thisFields, 0, totalFields, 0, thisFields.length);
        if (null != superFields) {
            System.arraycopy(superFields, 0, totalFields, thisFields.length, superFields.length);
        }

        Date now = new Date();

        for (Field field : totalFields) {
            //允许在反射时访问私有变量
            field.setAccessible(true);
            Object value = field.get(paramObj);
            // 已赋值,直接跳过
            if (null != value) {
                continue;
            }

            if (SqlCommandType.INSERT == commandType) {
                // 根据不同的字段名做不同的 处理
                String uuid = UUIDUtils.getId();
                if (ID.equals(field.getName())) {
                    field.set(paramObj, uuid);
                } else if (CREATED_BY.equals(field.getName())) {
                        field.set(paramObj, SpringSecurityUtil.getCurrentUserId());
                } else if (CREATED_TS.equals(field.getName())) {
                    field.set(paramObj, now);
                }
            }


            if (UPDATED_BY.equals(field.getName())) {
                    field.set(paramObj, SpringSecurityUtil.getCurrentUserId());
            } else if (UPDATED_TS.equals(field.getName())) {
                field.set(paramObj, now);
            }

        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


}
