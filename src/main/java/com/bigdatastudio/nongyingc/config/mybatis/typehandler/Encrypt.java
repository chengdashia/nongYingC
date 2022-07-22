package com.bigdatastudio.nongyingc.config.mybatis.typehandler;

/**
 * @author 成大事
 * @since 2022/7/21 23:03
 */
public class Encrypt {
    private String value;

    public Encrypt() {
    }

    public Encrypt(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
