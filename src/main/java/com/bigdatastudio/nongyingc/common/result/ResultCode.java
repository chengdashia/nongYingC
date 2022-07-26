package com.bigdatastudio.nongyingc.common.result;

import lombok.Getter;

/**
 * @author 成大事
 * @since 2022/6/2 11:12
 */
public enum ResultCode {
    /**限流操作**/
    RC100(100,"服务开启限流保护,请稍后再试!"),
    /**操作失败**/
    SERVICE_UNAVAILABLE(501,"系统维护,暂时无法处理客户端请求。"),
    /**操作成功**/
    SUCCESS(200,"操作成功！"),
    /**服务降级**/
    RC201(201,"服务开启降级保护,请稍后再试!"),
    /**热点参数限流**/
    RC202(202,"热点参数限流,请稍后再试!"),
    /**系统规则不满足**/
    RC203(203,"系统规则不满足要求,请稍后再试!"),
    /**授权规则不通过**/
    RC204(204,"授权规则不通过,请稍后再试!"),
    /**access_denied**/
    RC403(403,"无访问权限,请联系管理员授予权限"),
    /**请求要求用户认证**/
    UNAUTHORIZED(401,"匿名用户访问无权限资源时的异常"),
    COLLECTION_EXIST(410,"已经收藏"),
    PWD_ERROR(411,"密码错误"),
    NOT_EXIST(412,"不存在"),
    CAPTCHA_ERROR(413,"验证码不正确"),
    CAPTCHA_EXPIRE(414,"验证码过期"),
    REGISTERED(415,"该用户已注册"),
    /** 格式不匹配**/
    FORMAT_MISMATCH(416,"格式不匹配！请检查输入的手机号或邮箱是否合规！"),
    /**服务异常**/
    FAILED(500,"系统异常，请稍后重试"),

    INVALID_TOKEN(2001,"访问令牌不合法"),
    ACCESS_DENIED(2003,"没有权限访问该资源"),
    CLIENT_AUTHENTICATION_FAILED(1001,"客户端认证失败"),
    USERNAME_OR_PASSWORD_ERROR(1002,"用户名或密码错误"),
    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式");

    /**自定义状态码**/
    @Getter
    private final int code;

    /**
     * 携 带 消 息
     */
    @Getter
    private final String message;
    /**
     * 构 造 方 法
     */
    ResultCode(int code, String message) {

        this.code = code;

        this.message = message;
    }

}
