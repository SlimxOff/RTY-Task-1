package org.example.first;

import java.security.PublicKey;
import java.security.Signature;

public class Client {
    private static final String DSA_ALGORITHM = "SHA256withDSA";

    private PublicKey serverPublicKey;
    private int currentIncrement = 0;

    public Client(PublicKey serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }

    public byte[] signMessage(byte[] challenge, int increment) throws Exception {
        byte[] message = new byte[challenge.length + 4];
        System.arraycopy(challenge, 0, message, 0, challenge.length);
        System.arraycopy(intToByteArray(increment), 0, message, challenge.length, 4);

        Signature sign = Signature.getInstance(DSA_ALGORITHM);
        sign.initSign(Server.getPrivateKey()); // Предполагается, что у клиента есть доступ к приватному ключу сервера
        sign.update(message);
        return sign.sign();
    }

    private byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value
        };
    }
}