package com.bigdatastudio.nongyingc.service.impl;

import com.bigdatastudio.nongyingc.domain.User;
import com.bigdatastudio.nongyingc.mapper.UserMapper;
import com.bigdatastudio.nongyingc.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 成大事
 * @since 2022-07-21 16:01:26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
