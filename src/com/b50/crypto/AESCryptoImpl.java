package com.b50.crypto;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public class AESCryptoImpl implements Cryptographical {

    private Key key;
    private Cipher ecipher;
    private Cipher dcipher;

    private AESCryptoImpl(Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        this.key = key;
        this.ecipher = Cipher.getInstance("AES");
        this.dcipher = Cipher.getInstance("AES");
        this.ecipher.init(Cipher.ENCRYPT_MODE, key);
        this.dcipher.init(Cipher.DECRYPT_MODE, key);
    }

    public static Cryptographical initialize(CryptoKeyable key) throws CryptoException {
        try {
            return new AESCryptoImpl(key.getKey());
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        } catch (NoSuchPaddingException e) {
            throw new CryptoException(e);
        } catch (InvalidKeyException e) {
            throw new CryptoException(e);
        }
    }

    public String encrypt(String plaintext) {
        try {
            return new BASE64Encoder().encode(ecipher.doFinal(plaintext.getBytes("UTF8")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String ciphertext) {
        try {
            return new String(dcipher.doFinal(new BASE64Decoder().decodeBuffer(ciphertext)), "UTF8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
