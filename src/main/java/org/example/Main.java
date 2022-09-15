package org.example;

import org.example.asymmetric.AsymmetricCryptography;
import org.example.asymmetric.GenerateKeys;


import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static final String MESSAGE_FILE = "message.txt";
    public static final String CUSTOM_VAULT_FILE = "customVault.bin";
    public static final String ENCRYPTED_MESSAGE_FILE = "encrypedMessage.enc";
    public static final String SYMMETRIC_CRYPTOGRAPHY_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String SYMMETRIC_CRYPTOGRAPHY_METHOD = "AES";

    public static void main(String[] args) {
        try {
            symmetricEncryptTest();
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public static void symmetricEncryptTest() throws NoSuchAlgorithmException,
            NoSuchPaddingException, IOException, InvalidKeyException, InvalidAlgorithmParameterException, ClassNotFoundException {

        String originalContent = org.example.Utility.readFile(MESSAGE_FILE, StandardCharsets.UTF_8);
        SecretKey secretKey = KeyGenerator.getInstance(SYMMETRIC_CRYPTOGRAPHY_METHOD).generateKey();
        //save secretKey to file
        org.example.Utility.saveKeyToFile(secretKey, CUSTOM_VAULT_FILE);
        // load secret key from file
        SecretKey savedKey = org.example.Utility.readKey(CUSTOM_VAULT_FILE);

        org.example.SymmetricFileEncrypterDecrypter symmetricFileEncrypterDecrypter
                = new org.example.SymmetricFileEncrypterDecrypter(savedKey, SYMMETRIC_CRYPTOGRAPHY_ALGORITHM);

        symmetricFileEncrypterDecrypter.encrypt(originalContent, ENCRYPTED_MESSAGE_FILE);
        String decryptedContent = symmetricFileEncrypterDecrypter.decrypt(ENCRYPTED_MESSAGE_FILE);
        if (decryptedContent.equals(originalContent))
            System.out.println("Identical");

        //  new File(ENCRYPTED_MESSAGE_FILE).delete(); // cleanup
    }


    public static void asymmetricTest() throws Exception {
        GenerateKeys.main(new String[]{});
        AsymmetricCryptography.main(new String[]{});

    }

}