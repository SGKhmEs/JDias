package com.sgkhmjaes.jdias.security;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

/**
 * Class for generating RSA keys.
 */
public class RSAKeysGenerator {

    /**
     * Generate RSA private key.
     *
     * @return encoded RSA private key
     */
    public static String getRsaPrivateKey() {
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        kpg.initialize(512);
        KeyPair keyPair = kpg.generateKeyPair();
        //System.out.println("PUBLIC KEY " + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        return Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
    }

    /**
     * Generate RSA public key from private.
     *
     * @return encoded RSA private key
     */
    public static String getRsaPublicKey(String privateKey) {
        byte[] clear = Base64.getDecoder().decode(privateKey.getBytes());
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(clear);
        PublicKey publicKey = null;
        try {
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PrivateKey priv = fact.generatePrivate(pkcs8EncodedKeySpec);
            RSAPrivateKeySpec privateKeySpec = fact.getKeySpec(priv, RSAPrivateKeySpec.class);
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(privateKeySpec.getModulus(), BigInteger.valueOf(65537));
            publicKey = fact.generatePublic(rsaPublicKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
}
