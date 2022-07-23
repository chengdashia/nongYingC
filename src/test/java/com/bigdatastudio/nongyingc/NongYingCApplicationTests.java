package com.bigdatastudio.nongyingc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bigdatastudio.nongyingc.domain.User;
import com.bigdatastudio.nongyingc.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
class NongYingCApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() throws IllegalAccessException {
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
        //List<Map<String, Object>> maps = userMapper.selectMaps(new LambdaQueryWrapper<User>().eq(User::getUserPwd, "222"));
        //List<Map<String, Object>> decrypt = EncryptUtil.decrypt(maps);
        //System.out.println(decrypt);
        //
        //    User user = User.builder()
        //            .userName("1112")
        //            .userPwd("2222")
        //            .build();
        //    userMapper.insert(user);

        //User user1 = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, "1112"));
        //System.out.println(user1);
        //System.out.println(Aes.encrypt("222"));

        List<Map<String, Object>> maps = userMapper.selectMaps(new LambdaQueryWrapper<User>().eq(User::getUserName, "1112"));
        System.out.println(maps);
    }

}
