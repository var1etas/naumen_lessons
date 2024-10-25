package ru.urfu;

/**
 * Бот - программа для обмена сообщениями
 */
public interface Bot {
    /**
     * Отправить сообщение
     */
    void sendMessage(String chatId, String message);
}
