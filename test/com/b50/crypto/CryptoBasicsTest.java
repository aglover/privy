package com.b50.crypto;

import org.junit.Assert;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.security.KeyStore;

/**
 * Created by IntelliJ IDEA.
 * User: aglover
 * Date: 12/7/11
 * Time: 4:03 PM
 */
public class CryptoBasicsTest {


    @Test
    public void testEncrypt() throws Exception {
        SecretKey key = KeyGenerator.getInstance("AES").generateKey();
        KeyStore ks = KeyStore.getInstance("JCEKS");
        ks.load(null, null);
        KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(key);

        ks.setEntry("agb50pass21", skEntry, new KeyStore.PasswordProtection("agb50app".toCharArray()));
        FileOutputStream fos = new FileOutputStream("agb50.keystore");
        ks.store(fos, "pas_4444".toCharArray());
        fos.close();

        Cryptographical crypto = AESCryptoImpl.initialize(new AESCryptoKey(key));
        String enc = crypto.encrypt("Andy");
        Assert.assertEquals("Andy", crypto.decrypt(enc));

        Cryptographical anotherInst = AESCryptoImpl.initialize(new AESCryptoKey(key));
        String anotherEncrypt = anotherInst.encrypt("Andy");
        Assert.assertEquals("Andy", anotherInst.decrypt(anotherEncrypt));

        Assert.assertTrue(anotherEncrypt.equals(enc));
    }

    @Test
    public void testEncryptRandomKey() throws Exception {
        SecretKey key = KeyGenerator.getInstance("AES").generateKey();
        Cryptographical crypto = AESCryptoImpl.initialize(new AESCryptoKey(key));
        String enc = crypto.encrypt("Andy");
        Assert.assertEquals("Andy", crypto.decrypt(enc));

        SecretKey anotherKey = KeyGenerator.getInstance("AES").generateKey();
        Cryptographical anotherInst = AESCryptoImpl.initialize(new AESCryptoKey(anotherKey));
        String anotherEncrypt = anotherInst.encrypt("Andy");
        Assert.assertEquals("Andy", anotherInst.decrypt(anotherEncrypt));

        Assert.assertFalse(anotherEncrypt.equals(enc));

    }


}
