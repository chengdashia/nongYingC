package com.bigdatastudio.nongyingc;

import com.bigdatastudio.nongyingc.domain.User;
import com.bigdatastudio.nongyingc.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NongYingCApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        User user = User.builder()
                .userName("111")
                .userPwd("222")
                .build();
        //System.out.println(user);
        userMapper.insert(user);

        //

    }

}
