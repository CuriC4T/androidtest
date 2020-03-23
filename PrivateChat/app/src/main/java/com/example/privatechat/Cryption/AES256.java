package com.example.privatechat.Cryption;


import android.util.Log;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256 {
    private String iv;
    private Key keySpec;

    //ase 세팅
    public AES256(String key) throws UnsupportedEncodingException {
        this.iv = key.substring(0, 16);

        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length) {
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        this.keySpec = keySpec;
    }


    // CBC 암호화
    public String aesCBCEncode(String str) {
        String encrypted_string = null;
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
            byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
            encrypted_string = new String(Base64.encodeBase64(encrypted));

        } catch (NoSuchAlgorithmException e) {
        } catch (NoSuchPaddingException e) {
        } catch (InvalidKeyException e) {
        } catch (BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (InvalidAlgorithmParameterException e) {
        }
        return encrypted_string;
    }
    //복호화
    public String aesCBCDecode(String str) {
        String decrypted_string = null;


        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));

            byte[] byteStr = Base64.decodeBase64(str.getBytes());
            decrypted_string = new String(c.doFinal(byteStr), "UTF-8");

        } catch (NoSuchAlgorithmException e) {
        } catch (NoSuchPaddingException e) {
        } catch (InvalidKeyException e) {
        } catch (BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (InvalidAlgorithmParameterException e) {

        }
        return decrypted_string;
    }


    //ecb 암호화
    public String aesECBEncode(String str) {
        String encrypted_string = null;

        try {
            Cipher ciper = Cipher.getInstance("AES/ECB/PKCS5Padding");
            ciper.init(Cipher.ENCRYPT_MODE, keySpec);

            byte[] encrypted = ciper.doFinal(str.getBytes("UTF-8"));
            Log.d("end", String.valueOf(encrypted.length));
            encrypted_string = new String(Base64.encodeBase64(encrypted));

        } catch (NoSuchAlgorithmException e) {

        } catch (NoSuchPaddingException e) {

        } catch (InvalidKeyException e) {
        } catch (BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return encrypted_string;

    }

    //ecb 복호화
    public String aesECBDecode(String str) {
        String decrypted_string = null;

        try {
            Cipher ciper = Cipher.getInstance("AES/ECB/PKCS5Padding");
            ciper.init(Cipher.DECRYPT_MODE, keySpec);


            byte[] byteStr = Base64.decodeBase64(str.getBytes());
            decrypted_string = new String(ciper.doFinal(byteStr), "UTF-8");
        } catch (NoSuchAlgorithmException e) {

        } catch (NoSuchPaddingException e) {

        } catch (InvalidKeyException e) {
        } catch (BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return decrypted_string;
    }


}

