package com.lylblog.common.util;

import io.netty.handler.codec.base64.Base64Encoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Author: lyl
 * @Date: 2021/11/5 21:35
 */
public class RSAUtil {

    // RSA key位数
    private static final int KEY_SIZE = 512;

    // 单次RSA加密操作所允许的最大块长度，该值与 KEY_SIZE、padding方法有关。
    // 1024key->128,2048key->1256,512key->53,11个字节用于保存padding信息。
    private static final int BLOCK_SIZE = 53;

    private static final int OUTPUT_BLOCK_SIZE = 64;

    // private static final int KEY_SIZE = 1024;
    // private static final int BLOCK_SIZE = 117;
    // private static final int OUTPUT_BLOCK_SIZE = 128;

    // private static final int KEY_SIZE = 2048;
    // private static final int BLOCK_SIZE = 245;
    // private static final int OUTPUT_BLOCK_SIZE = 256;

    private static SecureRandom secrand = new SecureRandom();
    public static Cipher rsaCipher;

    public static String Algorithm = "RSA";// RSA、RSA/ECB/PKCS1Padding

    // public static String
    // Algorithm="RSA/ECB/PKCS1Padding";//RSA、RSA/ECB/PKCS1Padding

    public RSAUtil() throws Exception {
    }

    /**
     * 生成密钥对
     *
     * @return KeyPair
     * @throws Exception
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static KeyPair generateRSAKeyPair() {
        KeyPair keyPair  =  null;
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator
                    .getInstance(Algorithm);
            // 密钥位数
            keyPairGen.initialize(KEY_SIZE);
            // 密钥对
            keyPair = keyPairGen.generateKeyPair();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    /**
     * 生成密钥对
     *
     * @return KeyPair
     * @throws Exception
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static String[] generateRSAKeyPairStringArray() {
        String[] keypair = new String[2];
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator
                    .getInstance(Algorithm);
            // 密钥位数
            keyPairGen.initialize(KEY_SIZE);
            // 密钥对
            KeyPair keyPair = keyPairGen.generateKeyPair();

            // 公钥
            PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

            // 私钥
            PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            String publicKeyString = getKeyString(publicKey);
            System.out.println("Public KEY===>" + publicKeyString);
            keypair[0] = publicKeyString;

            String privateKeyString = getKeyString(privateKey);
            System.out.println("Private KEY===>" + privateKeyString);
            keypair[1] = privateKeyString;

        } catch (Exception e) {
            System.err.println("Exception:" + e.getMessage());
        }
        return keypair;
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Algorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = (new BASE64Encoder()).encodeBuffer(keyBytes);
        return s;
    }

    /**
     * 对content采用RSA 公钥加密，再使用BASE64加密
     *
     * @param publicKeyString
     *            公钥值
     * @param content
     *            明文串
     * @return cardSecretPwd
     * @throws Exception
     */
    public static String encodeSecret(String publicKeyString, String content)
            throws Exception {

        try {
            rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw e;
        }

        Key publicKey = getPublicKey(publicKeyString);
        try {
            // PublicKey pubkey = keys.getPublic();
            rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey, secrand);
            // System.out.println(rsaCipher.getBlockSize());
            // System.out.println(Message.getBytes("utf-8").length);
            // byte[] encryptedData =
            // rsaCipher.doFinal(Message.getBytes("utf-8"));
            byte[] data = content.getBytes("utf-8");
            int blocks = data.length / BLOCK_SIZE;
            int lastBlockSize = data.length % BLOCK_SIZE;
            byte[] encryptedData = new byte[(lastBlockSize == 0 ? blocks
                    : blocks + 1) * OUTPUT_BLOCK_SIZE];
            for (int i = 0; i < blocks; i++) {
                // int thisBlockSize = ( i + 1 ) * BLOCK_SIZE > data.length ?
                // data.length - i * BLOCK_SIZE : BLOCK_SIZE ;
                rsaCipher.doFinal(data, i * BLOCK_SIZE, BLOCK_SIZE,
                        encryptedData, i * OUTPUT_BLOCK_SIZE);
            }
            if (lastBlockSize != 0) {
                rsaCipher.doFinal(data, blocks * BLOCK_SIZE, lastBlockSize,
                        encryptedData, blocks * OUTPUT_BLOCK_SIZE);
            }
            return (new BASE64Encoder()).encodeBuffer(encryptedData);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new IOException("InvalidKey");
        } catch (ShortBufferException e) {
            e.printStackTrace();
            throw new IOException("ShortBuffer");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IOException("UnsupportedEncoding");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new IOException("IllegalBlockSize");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new IOException("BadPadding");
        } finally {

        }
    }

    /**
     * 对content采用RSA加密，再BASE64加密
     *
     * @param privateKeyString
     *            私钥值
     * @param content
     *            明文串
     * @return cardSecretPwd
     * @throws Exception
     */
    public static String encodeSecretByPriKey(String privateKeyString,
                                              String content) throws Exception {

        try {
            rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw e;
        }

        Key privateKey = getPrivateKey(privateKeyString);
        try {
            // PublicKey pubkey = keys.getPublic();
            rsaCipher.init(Cipher.ENCRYPT_MODE, privateKey, secrand);
            // System.out.println(rsaCipher.getBlockSize());
            // System.out.println(Message.getBytes("utf-8").length);
            // byte[] encryptedData =
            // rsaCipher.doFinal(Message.getBytes("utf-8"));
            byte[] data = content.getBytes("utf-8");
            int blocks = data.length / BLOCK_SIZE;
            int lastBlockSize = data.length % BLOCK_SIZE;
            byte[] encryptedData = new byte[(lastBlockSize == 0 ? blocks
                    : blocks + 1) * OUTPUT_BLOCK_SIZE];
            for (int i = 0; i < blocks; i++) {
                // int thisBlockSize = ( i + 1 ) * BLOCK_SIZE > data.length ?
                // data.length - i * BLOCK_SIZE : BLOCK_SIZE ;
                rsaCipher.doFinal(data, i * BLOCK_SIZE, BLOCK_SIZE,
                        encryptedData, i * OUTPUT_BLOCK_SIZE);
            }
            if (lastBlockSize != 0) {
                rsaCipher.doFinal(data, blocks * BLOCK_SIZE, lastBlockSize,
                        encryptedData, blocks * OUTPUT_BLOCK_SIZE);
            }
            return (new BASE64Encoder()).encodeBuffer(encryptedData);

        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new IOException("InvalidKey");
        } catch (ShortBufferException e) {
            e.printStackTrace();
            throw new IOException("ShortBuffer");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IOException("UnsupportedEncoding");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new IOException("IllegalBlockSize");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new IOException("BadPadding");
        } finally {

        }
    }

    /**
     * BASE64解密，再RSA解密
     *
     * @param privateKeyString
     *            私钥值
     * @param content
     *            密文串
     * @return 用私钥解密串
     * @throws Exception
     */
    public static String decodeSecret(String privateKeyString, String content)
            throws Exception {

        try {
            rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw e;
        }

        byte[] decoded = null;
        try {
            decoded = (new BASE64Decoder()).decodeBuffer(content);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Key privateKey = getPrivateKey(privateKeyString);
        try {
            rsaCipher.init(Cipher.DECRYPT_MODE, privateKey, secrand);
            int blocks = decoded.length / OUTPUT_BLOCK_SIZE;
            ByteArrayOutputStream decodedStream = new ByteArrayOutputStream(
                    decoded.length);
            for (int i = 0; i < blocks; i++) {
                decodedStream.write(rsaCipher.doFinal(decoded, i
                        * OUTPUT_BLOCK_SIZE, OUTPUT_BLOCK_SIZE));
            }
            return new String(decodedStream.toByteArray(), "UTF-8");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new IOException("InvalidKey");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IOException("UnsupportedEncoding");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new IOException("IllegalBlockSize");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new IOException("BadPadding");
        } finally {

        }
    }

    /**
     * BASE64解密，再RSA解密
     *
     * @param publicKeyString
     *            私钥值
     * @param content
     *            密文串
     * @return 用私钥解密串
     * @throws Exception
     */
    public static String decodeSecretByPubKey(String publicKeyString, String content) throws Exception {

        try {
            rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw e;
        }

        byte[] decoded = null;
        try {
            decoded = (new BASE64Decoder()).decodeBuffer(content);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Key publicKey = getPublicKey(publicKeyString);
        try {
            rsaCipher.init(Cipher.DECRYPT_MODE, publicKey, secrand);
            int blocks = decoded.length / OUTPUT_BLOCK_SIZE;
            ByteArrayOutputStream decodedStream = new ByteArrayOutputStream(
                    decoded.length);
            for (int i = 0; i < blocks; i++) {
                decodedStream.write(rsaCipher.doFinal(decoded, i
                        * OUTPUT_BLOCK_SIZE, OUTPUT_BLOCK_SIZE));
            }
            return new String(decodedStream.toByteArray(), "UTF-8");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new IOException("InvalidKey");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IOException("UnsupportedEncoding");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new IOException("IllegalBlockSize");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new IOException("BadPadding");
        } finally {

        }
    }

    public static void main(String[] args) throws Exception {
        KeyPair kp = RSAUtil.generateRSAKeyPair();
        RSAPublicKey pubk = (RSAPublicKey) kp.getPublic();//生成公钥
        RSAPrivateKey prik= (RSAPrivateKey) kp.getPrivate();//生成私钥
        String publicKey = RSAUtil.getKeyString(pubk);
        String privateKey = RSAUtil.getKeyString(prik);
        System.out.println("生成的公钥 " + publicKey);
        System.out.println("生成的秘钥 " + privateKey);
        String encodeStr = RSAUtil.encodeSecret(publicKey, "你好啊，嘿嘿嘿");
        System.out.println("使用公钥后加密的内容 ：" +encodeStr);
        String decodeStr = RSAUtil.decodeSecret(privateKey, encodeStr);
        System.out.println("解密后的内容："+decodeStr);
    }

}
