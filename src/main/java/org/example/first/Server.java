package org.example.first;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Random;

public class Server {
    private static final String DSA_ALGORITHM = "SHA256withDSA";
    private static final int INCREMENT_SIZE = 1000;

    private static KeyPair keyPair;
    private int currentIncrement = 0;

    public Server() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
        keyGen.initialize(1024);
        keyPair = keyGen.generateKeyPair();
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public static PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    public byte[] generateChallenge() {
        Random random = new Random();
        byte[] challenge = new byte[32];
        random.nextBytes(challenge);
        return challenge;
    }

    public int getNextIncrement() {
        return currentIncrement++;
    }

    public boolean verifySignature(byte[] message, byte[] signature) throws Exception {
        Signature sign = Signature.getInstance(DSA_ALGORITHM);
        sign.initVerify(keyPair.getPublic());
        sign.update(message);
        return sign.verify(signature);
    }
}