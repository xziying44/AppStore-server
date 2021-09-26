package com.xziying.appstorecloud.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * RSAUtil RAS加密类
 *
 * @author : xziying
 * @create : 2021-04-11 13:04
 */
public class RSAUtil {

    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        System.out.println("公钥:" + publicKeyString);
        System.out.println("秘钥:" + privateKeyString);
    }


    /**
     * 公钥加密
     */
    public static byte[] encrypt(byte[] bytes, String publicKey) throws Exception{
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(bytes);
    }

    public static byte[] decrypt(byte[] bytes, String privateKey) throws Exception{
        //64位解码加密后的字符串
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return cipher.doFinal(bytes);
    }

    public static byte[] encryptLong(byte[] bytes, String publicKey) throws Exception{
        int MAX_ENCRYPT_BLOCK = 117;

        int count = bytes.length / MAX_ENCRYPT_BLOCK;
        if (bytes.length % MAX_ENCRYPT_BLOCK != 0){
            count++;
        }
        byte[] returnValue = new byte[count * 128];

        int j = 0;

        for (int i = 0; i < bytes.length; i+= MAX_ENCRYPT_BLOCK) {
            if (i + MAX_ENCRYPT_BLOCK < bytes.length){
                byte[] temp = Arrays.copyOfRange(bytes, i, i + MAX_ENCRYPT_BLOCK);
                byte[] encrypt = encrypt(temp, publicKey);

                System.arraycopy(encrypt, 0, returnValue, j * encrypt.length, encrypt.length);
                j++;
            } else {
                byte[] temp = Arrays.copyOfRange(bytes, i, bytes.length);
                byte[] encrypt = encrypt(temp, publicKey);
                System.arraycopy(encrypt, 0, returnValue, j * 128, 128);
                break;
            }
        }
        return returnValue;
    }

    public static byte[] decryptLong(byte[] bytes, String publicKey) throws Exception{
        int MAX_ENCRYPT_BLOCK = 128;
        if (bytes.length % MAX_ENCRYPT_BLOCK != 0){
            return new byte[0];
        }
        ByteArrayOutputStream returnValue = new ByteArrayOutputStream();


        for (int i = 0; i < bytes.length; i+=MAX_ENCRYPT_BLOCK) {
            byte[] decrypt = decrypt(Arrays.copyOfRange(bytes, i, i + MAX_ENCRYPT_BLOCK), publicKey);
            returnValue.write(decrypt);
        }
        return returnValue.toByteArray();
    }

    @Test
    public void test() throws NoSuchAlgorithmException {
        genKeyPair();
    }
}
