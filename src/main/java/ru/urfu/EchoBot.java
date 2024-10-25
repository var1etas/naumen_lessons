package ru.urfu;

/**
 * Эхо-бот - бот, для отправки эхо-сообщений
 */
public class EchoBot {
    /**
     * Отправить эхо-сообщение
     */
    public void sendEchoMessage(String chatId, String message, Bot bot) {
        bot.sendMessage(chatId,"Ваше сообщение: " + message);
    }
}
