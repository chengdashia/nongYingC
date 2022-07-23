package com.bigdatastudio.nongyingc.config.mybatis.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.regex.Pattern;

/**
 * @author 成大事
 * @since 2022/7/22 17:08
 */
public class EncryptUtil {
    /**密匙，必须16位*/
    private static final String KEY = "f4k9f5w7f8g4er26";
    /**偏移量*/
    private static final String OFFSET = "5e8y6w45ju8w9jq8";
    /**编码*/
    private static final String ENCODING = "UTF-8";
    /**算法*/
    private static final String ALGORITHM = "AES";
    /** 默认的加密算法，CBC模式*/
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    /**验证*/
    private static final String BASE64_REGX= "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";


    /**
     * 加密
     * @param data 数据
     * @return 加密结果
     */
    public static String aesEncrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("ASCII"), ALGORITHM);
        //IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes());//CBC模式偏移量IV
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes(ENCODING));
        //加密后再使用BASE64做转码
        return new Base64().encodeToString(encrypted);
    }

    /**
     * 解密
     * @param data 数据
     * @return 解密结果
     */
    public static String aesDecrypt(String data) throws Exception {
        data = data.replace(" ","+");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("ASCII"), ALGORITHM);
        //IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes()); //CBC模式偏移量IV
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        //先用base64解码
        byte[] buffer = new Base64().decode(data);
        byte[] encrypted=encrypted = cipher.doFinal(buffer);
        return new String(encrypted, ENCODING);
    }


    /**
     * 验证是否加密
     * @param data 数据
     * @return 是否加密
     */
    public static Boolean isEncrypted(String data){
        return Pattern.matches(BASE64_REGX, data) && data.endsWith("==");
    }

}
