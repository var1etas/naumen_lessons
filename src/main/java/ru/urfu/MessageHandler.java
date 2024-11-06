package ru.urfu;

/**
 * Сервис для обработки сообщений
 */
public class MessageHandler {
    /**
     * Конвертирует сообщение в эхо-сообщение
     */
    public String convertMessageToEcho(String message) {
        return "Ваше сообщение: '" + message + "'";
    }
}
