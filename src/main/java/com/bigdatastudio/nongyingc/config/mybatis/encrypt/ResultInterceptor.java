package com.bigdatastudio.nongyingc.config.mybatis.encrypt;

import com.bigdatastudio.nongyingc.config.mybatis.encrypt.annotation.EncryptField;
import com.bigdatastudio.nongyingc.config.mybatis.encrypt.annotation.EncryptClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 成大事
 * @since 2022/7/22 17:58
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
})
@Component
@Slf4j
public class ResultInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object returnValue = invocation.proceed();
        try {
            // 当返回值类型为数组集合时，就判断是否需要进行数据解密
            if (returnValue instanceof ArrayList<?>) {
                List<?> list = (List<?>) returnValue;
                if (CollectionUtils.isEmpty(list)) {
                    return returnValue;
                }
                Object object = list.get(0);
                // 判断第一个对象是否有解密注解
                if (object != null && isSensitiveBean(object)) {
                    for (Object o : list) {
                        decryptField(o);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnValue;
        }
        return returnValue;
    }

    /**
     * 解密
     *
     * @param t
     * @param <T>
     */
    public <T> void decryptField(T t) {
        Field[] declaredFields = t.getClass().getDeclaredFields();
        if (declaredFields != null && declaredFields.length > 0) {
            // 遍历这些字段
            for (Field field : declaredFields) {
                // 如果这个字段存在解密注解就进行解密
                if (field.isAnnotationPresent(EncryptField.class) && field.getType().toString().endsWith("String")) {
                    field.setAccessible(true);
                    try {
                        // 获取这个字段的值
                        String fieldValue = (String) field.get(t);
                        // 判断这个字段的数值是否不为空
                        if (!StringUtils.isEmpty(fieldValue)) {
                            if (EncryptUtil.isEncrypted(fieldValue)){
                                // 进行解密
                                String encryptData = EncryptUtil.aesDecrypt(fieldValue);
                                // 将值反射到对象中
                                field.set(t, encryptData);
                            }else {
                                log.info("该数据未被加密");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 判断是否带有敏感的实体类
     *
     * @param t
     * @param <T>
     * @return
     */
    public <T> boolean isSensitiveBean(T t) {
        Class object=null;
        try {
            object=(Class) t;
        }catch (Exception e){
            object=t.getClass();
        }
        if (object!=null&&object.isAnnotationPresent(EncryptClass.class)) {
            return true;
        }
        return false;
    }
}

