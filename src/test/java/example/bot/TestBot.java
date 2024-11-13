package example.bot;

import java.util.ArrayList;
import java.util.List;

/**
 * Тестовая реализация бота для тестирования класса BotLogic
 */
public class TestBot implements Bot {

    /**
     * Для хранения и удобной проверки отправленных сообщений используется список
     */
    private final List<String> messageList = new ArrayList<>();

    @Override
    public void sendMessage(Long chatId, String message) {
        messageList.add(message);
    }

    /**
     * Возвращает список с сообщениями чата
     */
    public String getLastMessage() {
        return messageList.getLast();
    }

    /**
     * Возвращает сообщение с заданными индексом с конца
     */
    public String getMessageFromEnd(int indexFromEnd) {
        return messageList.get(messageList.size() - indexFromEnd);
    }
}
