package com.bigdatastudio.nongyingc.controller;

import com.bigdatastudio.nongyingc.common.result.R;
import com.bigdatastudio.nongyingc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 成大事
 * @since 2022-08-02 16:09:18
 */
@Api(tags = "用户模块")
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

    private final UserService userService;


    @ApiOperation("用户登录")
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="userName",dataTypeClass = String.class,required=true,value="用户名"),
            @ApiImplicitParam(paramType="query",name="password",dataTypeClass = String.class,required=true,value="密码")
    })
    public R<Object> login(
        @NotBlank(message = "用户名不能为空") @RequestParam("userName") String userName,
        @NotBlank(message = "用户名不能为空") @RequestParam("password") String password
    ){
        return userService.login(userName,password);
    }



    @ApiOperation("用户注册")
    @PostMapping("/register")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="userName",dataTypeClass = String.class,required=true,value="用户名"),
            @ApiImplicitParam(paramType="query",name="password",dataTypeClass = String.class,required=true,value="密码")
    })
    public R<Object> register(
            @NotBlank(message = "用户名不能为空") @RequestParam("userName") String userName,
            @NotBlank(message = "用户名不能为空") @RequestParam("password") String password
    ){
        return userService.register(userName,password);
    }

}
