package com.tss.security.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * Utility class to generate RSA key pairs for JWT signing
 */
public class RSAKeyGenerator {
    
    private static final int KEY_SIZE = 2048;
    
    public static void main(String[] args) {
        try {
            KeyPair keyPair = generateKeyPair();
            
            String privateKey = encodeKey(keyPair.getPrivate().getEncoded());
            String publicKey = encodeKey(keyPair.getPublic().getEncoded());
            
            System.out.println("=== RSA Key Pair Generated ===");
            System.out.println("\nPrivate Key (for application.properties):");
            System.out.println("app.jwt-private-key=" + privateKey);
            
            System.out.println("\nPublic Key (for application.properties):");
            System.out.println("app.jwt-public-key=" + publicKey);
            
            System.out.println("\n=== Copy these to your application.properties file ===");
            
        } catch (Exception e) {
            System.err.println("Error generating RSA keys: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }
    
    private static String encodeKey(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }
}
