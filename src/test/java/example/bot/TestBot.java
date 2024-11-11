package example.bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Тестовая реализация бота для тестирования класса BotLogic
 */
public class TestBot implements Bot {

    private final BotLogic botLogic = new BotLogic(this);

    /**
     * Для хранения и удобной проверки отправленных сообщений используется список
     */
    private List<String> chat = new ArrayList<>();

    private User user = new User(1L);

    @Override
    public void sendMessage(Long chatId, String message) {
        chat.add(message);
    }

    /**
     * Обработка входящих сообщений с помощью BotLogic().onMessageReceived()
     */
    public void onMessageReceived(String message) {
        botLogic.processCommand(user, message);
    }

    /**
     * Запуск бота, начинается обработка входящих команд
     */
    public void run() {
        try(Scanner scanner = new Scanner(System.in)) {
            scanner.useDelimiter("\n");
            while(true) {
                String message = scanner.next();
                onMessageReceived(message);
            }
        }
    }

    /**
     * Возвращает список с сообщениями чата
     */
    public List<String> getChat() {
        return chat;
    }

    /**
     * Возвращает пользователя
     */
    public User getUser() {
        return user;
    }

    public static void main(String[] args) {
        new TestBot().run();
    }
}
