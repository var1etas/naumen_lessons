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
     * Обработка входящих сообщений с помощью BotLogic().processCommand()
     */
    public void onMessageReceived(String message, User user, BotLogic botLogic) {
        botLogic.processCommand(user, message);
    }

    /**
     * Возвращает список с сообщениями чата
     */
    public List<String> getMessageList() {
        return messageList;
    }
}
