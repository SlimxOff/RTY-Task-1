package org.example.second;

import java.security.PublicKey;

public class Main {
    public static void main(String[] args) {
        try {
            // Создаем экземпляр сервера
            Server server = new Server();

            // Получаем публичный ключ сервера
            PublicKey serverPublicKey = server.getPublicKey();

            // Создаем экземпляр клиента и передаем ему публичный ключ сервера
            Client client = new Client(serverPublicKey);

            // Сервер генерирует nonce
            byte[] nonce = server.generateNonce();

            // Клиент подписывает nonce
            byte[] signature = client.signMessage(nonce);

            // Сервер проверяет подпись
            boolean isValid = server.verifySignature(nonce, signature);

            // Выводим результат проверки
            System.out.println("Signature is valid: " + isValid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}