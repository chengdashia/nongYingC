package com.bigdatastudio.nongyingc.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bigdatastudio.nongyingc.config.mybatis.encrypt.annotation.EncryptClass;
import com.bigdatastudio.nongyingc.config.mybatis.encrypt.annotation.EncryptField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 成大事
 * @since 2022-07-21 16:01:26
 */
@Data
@Builder
@EncryptClass
// @TableName 注解的 autoResultMap 一定要为true 否则查询得到的结果会为null
@TableName(value = "user",autoResultMap = true)
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId("id")
    private String id;

    @ApiModelProperty("用户密码")
    //@TableField(value = "user_pwd",typeHandler = AesEncryptHandler.class)
    @EncryptField
    private String userPwd;

    @ApiModelProperty("用户账号")
    @TableField("user_name")
    private String userName;


}
