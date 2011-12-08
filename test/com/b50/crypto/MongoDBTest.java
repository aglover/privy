package com.b50.crypto;

import com.mongodb.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

/**
 * Created by IntelliJ IDEA.
 * User: aglover
 * Date: 12/7/11
 * Time: 8:26 PM
 */
public class MongoDBTest {

    @Test
    public void testingWithMongoDB() throws Exception {
        DB db = getMongoConnection();
        Assert.assertNotNull(db);

        DBCollection coll = db.getCollection("accounts");

        KeyStore.SecretKeyEntry pkEntry = getKeyStoreEntry();

        Cryptographical crypto = AESCryptoImpl.initialize(new AESCryptoKey(pkEntry.getSecretKey()));

        Assert.assertNotNull(crypto);

        BasicDBObject encryptedDoc = new BasicDBObject();
        encryptedDoc.put(crypto.encrypt("name"), crypto.encrypt("Acme, LLC"));
        coll.insert(encryptedDoc);

        Thread.sleep(300);

        BasicDBObject encryptedQuery = new BasicDBObject();
        encryptedQuery.put(crypto.encrypt("name"), crypto.encrypt("Acme, LLC"));

        DBObject result = coll.findOne(encryptedQuery);

        String value = result.get(crypto.encrypt("name")).toString();
        Assert.assertEquals("Acme, LLC", crypto.decrypt(value));

    }


    @Test
    public void encryptMongoDBRecords() throws Exception {
        KeyStore.SecretKeyEntry pkEntry = getKeyStoreEntry();
        Cryptographical crypto = AESCryptoImpl.initialize(new AESCryptoKey(pkEntry.getSecretKey()));

        DB db = getMongoConnection();
        DBCollection coll = db.getCollection("accounts");

        BasicDBObject encryptedDoc = new BasicDBObject();
        encryptedDoc.put("name", crypto.encrypt("Acme Life, LLC"));
        coll.insert(encryptedDoc);

        Thread.sleep(300);

        BasicDBObject encryptedQuery = new BasicDBObject();
        encryptedQuery.put("name", crypto.encrypt("Acme Life, LLC"));

        DBObject result = coll.findOne(encryptedQuery);

        String value = result.get("name").toString();
        Assert.assertEquals("Acme Life, LLC", crypto.decrypt(value));

    }

    private KeyStore.SecretKeyEntry getKeyStoreEntry() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
        KeyStore ks = KeyStore.getInstance("JCEKS");
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(new File("agb50.keystore")));
        ks.load(fis, "pas_4444".toCharArray());
        fis.close();
        KeyStore.PasswordProtection pss = new KeyStore.PasswordProtection("agb50app".toCharArray());
        return (KeyStore.SecretKeyEntry) ks.getEntry("agb50pass21", pss);
    }

    private DB getMongoConnection() throws UnknownHostException {
        Mongo m = new Mongo("flame.mongohq.com", 27054);
        DB db = m.getDB("magnus");
        db.authenticate("magnus", "magnus".toCharArray());
        return db;
    }

}
