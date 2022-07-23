package com.bigdatastudio.nongyingc.config.mybatis.encrypt;


import com.bigdatastudio.nongyingc.config.mybatis.encrypt.annotation.EncryptField;
import com.bigdatastudio.nongyingc.config.mybatis.encrypt.annotation.EncryptClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * 加密拦截器：插入数据库之前对敏感数据加密
 * 暂时只做了简单的 MD5 加密
 * 场景：插入、更新时生效
 * 策略：
 *   - 在敏感字段所在实体类上添加@SensitiveData注解
 *   - 在敏感字段上添加@SensitiveField注解
 *
 * @author 成大事
 * @since 2022/7/22 17:10
 */
@Slf4j
@Component
//update包含insert、update、delete
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class EncryptInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        if (!"update".equalsIgnoreCase(methodName) && !"insert".equalsIgnoreCase(methodName)) {
            return invocation.proceed();
        }
        // 获取该sql语句放入的参数
        Object parameter = invocation.getArgs()[1];
        if (parameter instanceof ParameterMap) {
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) invocation.getArgs()[1];
            parameter = paramMap.get("param1");
        }

        if (parameter instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) invocation.getArgs()[1];
            try {
                parameter = paramMap.get("et");
            } catch (Exception e) {
                parameter = paramMap.get("param1");
            }
        }

        if (parameter != null && isSensitiveBean(parameter)) {
            // 对参数内含注解的字段进行加密
            encryptField(parameter);
        }
        return invocation.proceed();
    }

    /**
     * 判断是否带有敏感的实体类
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> boolean isSensitiveBean(T t) {
        Class object = null;
        try {
            object = (Class) t;
        } catch (Exception e) {
            object = t.getClass();
        }
        if (object != null && object.isAnnotationPresent(EncryptClass.class)) {
            return true;
        }
        return false;
    }

    /**
     * 加密类中的敏感字段
     *
     * @param t
     * @param <T>
     */
    private <T> void encryptField(T t) {
        Field[] declaredFields = t.getClass().getDeclaredFields();
        if (declaredFields != null && declaredFields.length > 0) {
            for (Field field : declaredFields) {
                // 查找当字段带有加密注解,并且字段类型为字符串类型
                if (field.isAnnotationPresent(EncryptField.class)) {
                    field.setAccessible(true);
                    String fieldValue = null;
                    try {
                        fieldValue = (String) field.get(t);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (!StringUtils.isEmpty(fieldValue)) {
                        try {
                            field.set(t, EncryptUtil.aesEncrypt(fieldValue));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}

