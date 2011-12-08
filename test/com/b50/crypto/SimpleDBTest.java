package com.b50.crypto;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.*;
import com.amazonaws.services.simpledb.util.SimpleDBUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aglover
 * Date: 12/8/11
 * Time: 9:33 AM
 */
public class SimpleDBTest {
    @Test
    public void testSimpleDBInteraction() throws Exception {
        AmazonSimpleDB sdb = getSimpleDB();
        String domain = "accounts";
        sdb.createDomain(new CreateDomainRequest(domain));


        List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();

        data.add(new ReplaceableItem().withName("account_01").withAttributes(
                new ReplaceableAttribute().withName("name").withValue("Acme Life, LLC")));

        sdb.batchPutAttributes(new BatchPutAttributesRequest(domain, data));

        Thread.sleep(300);


        String qry = "select * from " + SimpleDBUtils.quoteName(domain)
                + " where name = 'Acme Life, LLC'";
        SelectRequest selectRequest = new SelectRequest(qry);
        boolean forLoopProcessed = false;
        for (Item item : sdb.select(selectRequest).getItems()) {
            forLoopProcessed = true;
            Assert.assertEquals("account_01", item.getName());
        }
        Assert.assertTrue(forLoopProcessed);

    }

    @Test
    public void testSimpleDBEncryptInsert() throws Exception {

        KeyStore.SecretKeyEntry pkEntry = getKeyStoreEntry();
        Cryptographical crypto = AESCryptoImpl.initialize(new AESCryptoKey(pkEntry.getSecretKey()));

        AmazonSimpleDB sdb = getSimpleDB();

        String domain = "accounts";
        sdb.createDomain(new CreateDomainRequest(domain));


        List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();

        String encryptedName = crypto.encrypt("Acme Life, LLC");

        data.add(new ReplaceableItem().withName("account_02").withAttributes(
                new ReplaceableAttribute().withName("name").withValue(encryptedName)));

        sdb.batchPutAttributes(new BatchPutAttributesRequest(domain, data));

        Thread.sleep(300);


        String qry = "select * from " + SimpleDBUtils.quoteName(domain)
                + " where name = '" + encryptedName + "'";

        SelectRequest selectRequest = new SelectRequest(qry);
        boolean forLoopProcessed = false;
        for (Item item : sdb.select(selectRequest).getItems()) {
            forLoopProcessed = true;
            Assert.assertEquals("account_02", item.getName());
        }
        Assert.assertTrue(forLoopProcessed);

    }

    private KeyStore.SecretKeyEntry getKeyStoreEntry() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException {
        KeyStore ks = KeyStore.getInstance("JCEKS");
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(new File("agb50.keystore")));
        ks.load(fis, "pas_4444".toCharArray());
        fis.close();
        KeyStore.PasswordProtection pss = new KeyStore.PasswordProtection("agb50app".toCharArray());
        return (KeyStore.SecretKeyEntry) ks.getEntry("agb50pass21", pss);
    }

    private AmazonSimpleDB getSimpleDB() throws IOException {
        return new AmazonSimpleDBClient(new PropertiesCredentials(new File("etc/AwsCredentials.properties")));
    }
}
