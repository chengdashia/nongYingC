package com.bigdatastudio.nongyingc.dao;

import com.bigdatastudio.nongyingc.model.domain.User;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 成大事
 * @since 2022-08-02 16:09:18
 */
@Mapper
public interface UserDao extends MPJBaseMapper<User> {

}
