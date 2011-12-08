package com.b50.crypto;

//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.CreateBucketRequest;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import org.apache.commons.cli.*;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.CertificateException;
//import java.util.UUID;
//
//import static org.apache.commons.cli.OptionBuilder.withDescription;

/**
 *
 */
public class KeyCreator {

    private static final String AWSKEY = "awskey";
    private static final String AWSSECRET = "awssecret";
    private static final String KEYPASS = "keypass";
    private static final String KEYSTOREPASS = "keystorepass";
    private static final String SIMPLEDB_S3_KEYSTORE = "simpledb.s3.keystore";

    public static void main(String[] args) {
//        try {
//            final CommandLine commandLine = getValidatedCommandLine(args);
//            final String amazonkey = commandLine.getOptionValue(AWSKEY);
//            final String amazconsec = commandLine.getOptionValue(AWSSECRET);
//            final String keypassword = commandLine.getOptionValue(KEYPASS);
//            final String keystorepassword = commandLine.getOptionValue(KEYSTOREPASS);
//            createS3Key(amazonkey, amazconsec, keypassword, keystorepassword);
//        } catch (Exception e) {
//            handleHelpForMain(getOptionsForMain());
//            System.err.println("there was an error creating a key and placing it in S3. See below" +
//                    " stack trace for more details");
//            e.printStackTrace();
//        }
    }

//    private static void handleHelpForMain(final Options options) {
//        new HelpFormatter().printHelp("KeyCreator ", options);
//    }
//
//    private static void createS3Key(final String amazonkey, final String amazonsecret,
//                                    final String keypass, final String keystorepass)
//            throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException {
//
//        SecretKey key = KeyGenerator.getInstance("AES").generateKey();
//        KeyStore ks = KeyStore.getInstance("JCEKS");
//        ks.load(null, null);
//
//        KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(key);
//        KeyStore.PasswordProtection pss =
//                new KeyStore.PasswordProtection(keypass.toCharArray());
//        ks.setEntry("simplejdbc", skEntry, pss);
//
//        FileOutputStream fos = new FileOutputStream(SIMPLEDB_S3_KEYSTORE);
//        ks.store(fos, keystorepass.toCharArray());
//        fos.close();
//
//        String bucketName = doS3Put(amazonkey, amazonsecret);
//
//        System.out.println("S3 bucket name is " + bucketName);
//        System.out.println("A private key store has been placed in the above bucket. You will need to \n" +
//                "provide this bucket name when using the SimpleJDBC crypto extension JDBC driver. \n" +
//                "Plus, a local key store file has been created (" + SIMPLEDB_S3_KEYSTORE + ") -- this \n" +
//                "file can be used for local testing and as a backup");
//    }
//
//    private static String doS3Put(final String amazonkey, final String amazonsecret) {
//        final AmazonS3 s3 = new AmazonS3Client(new BasicAWSCredentials(amazonkey, amazonsecret));
//        final String bucketName = "sjdbc-" + UUID.randomUUID();
//        final String keyname = "simplejdbc-keystore";
//        s3.createBucket(new CreateBucketRequest(bucketName));
//        File fle = new File(SIMPLEDB_S3_KEYSTORE);
//        s3.putObject(new PutObjectRequest(bucketName, keyname, fle));
//        return bucketName;
//    }
//
//    private static CommandLine getValidatedCommandLine(String[] args) throws ParseException {
//        final Options options = getOptionsForMain();
//        final CommandLine commandLine = getCommandLineForMain(args, options);
//        validateArguments(commandLine);
//        return commandLine;
//    }
//
//    private static Options getOptionsForMain() {
//        final Options options = new Options();
//        options.addOption(withDescription("Amazon key").hasOptionalArg().create(AWSKEY));
//        options.addOption(withDescription("Amazon secret").hasOptionalArg().create(AWSSECRET));
//        options.addOption(withDescription("Encrypt/Decrypt key password").hasOptionalArg().create(KEYPASS));
//        options.addOption(withDescription("Encrypt/Decrypt key store password").hasOptionalArg().create(KEYSTOREPASS));
//        return options;
//    }
//
//    private static CommandLine getCommandLineForMain(final String[] args, final Options options) throws ParseException {
//        return new GnuParser().parse(options, args);
//    }
//
//    private static void validateArguments(final CommandLine commandLine) throws IllegalArgumentException {
//        if (commandLine.getOptions().length < 4) {
////            handleHelpForMain(getOptionsForMain());
//            throw new IllegalArgumentException("Required arguments missing. You must provide Amazon credentials and two" +
//                    "passwords for key and its store.");
//        }
//    }
}
