package org.example.first;

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

            // Сервер генерирует случайное сообщение (challenge)
            byte[] challenge = server.generateChallenge();

            // Сервер генерирует следующий инкремент
            int increment = server.getNextIncrement();

            // Клиент подписывает сообщение (challenge + increment)
            byte[] signature = client.signMessage(challenge, increment);

            // Сервер проверяет подпись
            boolean isValid = server.verifySignature(concatenate(challenge, intToByteArray(increment)), signature);

            // Выводим результат проверки
            System.out.println("Signature is valid: " + isValid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для объединения двух массивов байтов
    private static byte[] concatenate(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    // Метод для преобразования int в массив байтов
    private static byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value
        };
    }
}