package com.sgkhmjaes.jdias.security;

import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

/**
 * Created by yurak on 05.06.2017.
 */
public class RSAKeysGeneratorTest {

    @Test
    public void getRsaPrivateKey() throws Exception {
        String privateKey = RSAKeysGenerator.getRsaPrivateKey();
        String publicKey = RSAKeysGenerator.getRsaPublicKey(privateKey);
        System.out.println("PUBLIC KEY " + publicKey);
        String privateKey2 = RSAKeysGenerator.getRsaPrivateKey();
        Assert.assertNotEquals(privateKey, privateKey2);
    }

    @Test
    public void getRsaPublicKey() throws Exception {
        String privateKey = RSAKeysGenerator.getRsaPrivateKey();
        String publicKey = RSAKeysGenerator.getRsaPublicKey(privateKey);
        String publicKey2 = RSAKeysGenerator.getRsaPublicKey(privateKey);

        Assert.assertEquals(publicKey, publicKey2);

    }

    @Test
    public void rsaKeys() throws NoSuchAlgorithmException {
        String privateKey = RSAKeysGenerator.getRsaPrivateKey();
        String pubKey = RSAKeysGenerator.getRsaPublicKey(privateKey);
        System.out.println("PRIVATE KEY " + privateKey);
        //System.out.println(publicKey);
        System.out.println(privateKey.length());
        System.out.println("PUBLIC KEY " + pubKey);
    }

}
