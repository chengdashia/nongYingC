package com.bigdatastudio.nongyingc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bigdatastudio.nongyingc.config.mybatis.typehandler.Encrypt;
import com.bigdatastudio.nongyingc.domain.User;
import com.bigdatastudio.nongyingc.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
class NongYingCApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        //for (int i = 0; i < 10; i++) {
        //    String userName = String.valueOf(RandomUtil.randomInt(10,15));
        //    String userPwd = String.valueOf(RandomUtil.randomInt(10,15));
        //    log.info("userName原始：  "+userName);
        //    log.info("userName加密：  "+Aes.encrypt(userName));
        //    log.info(" ============================ ");
        //    log.info("userPwd原始：  "+userPwd);
        //    log.info("userPwd加密：  "+Aes.encrypt(userPwd));
        //    User user = User.builder()
        //            .userName(userName)
        //            .userPwd(userPwd)
        //            .build();
        //    userMapper.insert(user);
        //    System.out.println("************************************************");
        //}
        System.out.println(userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getUserPwd, new Encrypt("222"))));
        //
        //    User user = User.builder()
        //            .userName("111")
        //            .userPwd("222")
        //            .build();
        //    userMapper.insert(user);
    }

}
