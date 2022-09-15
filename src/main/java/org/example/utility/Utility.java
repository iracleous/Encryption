package org.example.utility;

import javax.crypto.SecretKey;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utility {
    public static void saveKeyToFile(SecretKey secretKey, String filename) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(filename)));
        try {
            out.writeObject(secretKey);
        } finally {
            out.close();
        }
    }

    public static SecretKey readKey(String filename) throws IOException, ClassNotFoundException {
        SecretKey key;
        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(new File(filename)));
        try {
            key = (SecretKey) oin.readObject();
        } finally {
            oin.close();
        }
        return key;
    }

    public static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
