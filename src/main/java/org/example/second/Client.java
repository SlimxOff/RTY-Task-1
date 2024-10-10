package org.example.second;

import java.security.PublicKey;
import java.security.Signature;

public class Client {
    private static final String DSA_ALGORITHM = "SHA256withDSA";

    private PublicKey serverPublicKey;

    public Client(PublicKey serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }

    public byte[] signMessage(byte[] nonce) throws Exception {
        Signature sign = Signature.getInstance(DSA_ALGORITHM);
        sign.initSign(Server.getPrivateKey()); // Предполагается, что у клиента есть доступ к приватному ключу сервера
        sign.update(nonce);
        return sign.sign();
    }
}