package ru.urfu;

/**
 * Сервис для обработки и отправки эхо-сообщений
 */
public class EchoMessenger {
    /**
     * Отправить эхо-сообщение
     */
    public void sendEchoMessage(String chatId, String message, Bot bot) {
        bot.sendMessage(chatId, getEchoMessage(message));
    }

    /**
     * Обработка эхо-сообщения
     */
    private String getEchoMessage(String message) {
        return String.format("Ваше сообщение: '%s'", message);//"Ваше сообщение: " + message;
    }
}
