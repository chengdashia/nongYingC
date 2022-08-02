package com.bigdatastudio.nongyingc.service;

import com.bigdatastudio.nongyingc.common.result.R;
import com.bigdatastudio.nongyingc.model.domain.User;
import com.github.yulichang.base.MPJBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 成大事
 * @since 2022-08-02 16:09:18
 */
public interface UserService extends MPJBaseService<User> {

    /**
     * 登录
     * @param userName   用户名
     * @param password   密码
     * @return   登录状态
     */
    R<Object> login(String userName, String password);

    /**
     * 注册
     * @param userName   用户名
     * @param password   密码
     * @return   是否注册成功
     */
    R<Object> register(String userName, String password);
}
