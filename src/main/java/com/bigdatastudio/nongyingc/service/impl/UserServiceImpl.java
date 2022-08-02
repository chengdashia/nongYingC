package com.bigdatastudio.nongyingc.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bigdatastudio.nongyingc.common.result.R;
import com.bigdatastudio.nongyingc.common.result.ResultCode;
import com.bigdatastudio.nongyingc.model.domain.User;
import com.bigdatastudio.nongyingc.dao.UserDao;
import com.bigdatastudio.nongyingc.service.UserService;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 成大事
 * @since 2022-08-02 16:09:18
 */
@Service
public class UserServiceImpl extends MPJBaseServiceImpl<UserDao, User> implements UserService {


    /**
     * 登录
     * @param userName   用户名
     * @param password   密码
     * @return   登录状态
     */
    @Override
    public R<Object> login(String userName, String password) {
        //根据用户名查数据，查用户密码和用户id
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getUserId,User::getPassword)
                .eq(User::getUserName, userName));
        //如果用户不为null
        if(!ObjectUtils.isEmpty(user)){
            if(password.equals(user.getPassword())){
                //把用户id放到sa-token中
                StpUtil.login(user.getUserId());
                //返回token 给前端
                return R.ok(StpUtil.getTokenInfo());
            }
            return R.error(ResultCode.PWD_ERROR.getCode(),ResultCode.PWD_ERROR.getMessage());
        }
        return R.error(ResultCode.NOT_EXIST.getCode(),ResultCode.NOT_EXIST.getMessage());
    }

    /**
     * 注册
     * @param userName   用户名
     * @param password   密码
     * @return   是否注册成功
     */
    @Override
    public R<Object> register(String userName, String password) {
        //根据用户名查数据，查用户密码和用户id
        User user = this.baseMapper.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getUserId,User::getPassword)
                .eq(User::getUserName, userName));
        if(user == null){
            User user1 = new User()
                    .setUserName(userName)
                    .setPassword(password);
            this.baseMapper.insert(user1);
            return R.ok();
        }
        return R.error(ResultCode.REGISTERED.getCode(),ResultCode.REGISTERED.getMessage());
    }
}
