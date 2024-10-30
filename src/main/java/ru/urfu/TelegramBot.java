package ru.urfu;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Телеграм бот
 */
public class TelegramBot extends TelegramLongPollingBot implements Bot {
    private final EchoMessenger echoMessenger;

    private final String telegramBotName;

    /**
     * Конструктор телеграм бота, для создания экземпляра требуется передать имя бота, токен
     * и сервис для обработки и отправки эхо-сообщений
     */
    public TelegramBot(String telegramBotName, String token, EchoMessenger echoMessenger) {
        super(token);
        this.telegramBotName = telegramBotName;
        this.echoMessenger = echoMessenger;
    }

    /**
     * Метод для запуска бота
     * @throws RuntimeException если запуск не удался
     */
    public void start() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
            System.out.println("Telegram бот запущен");
        } catch (TelegramApiException e) {
            throw new RuntimeException("Не удалось запустить телеграм бота", e);
        }
    }

    /**
     * Обработчик входящих сообщений
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message updateMessage = update.getMessage();
            Long chatId = updateMessage.getChatId();
            String messageFromUser = updateMessage.getText();
            echoMessenger.sendEchoMessage(String.valueOf(chatId), messageFromUser, this);
        }
    }

    /**
     * Отправить сообщение заданному получателю
     * @param chatId идентификатор чата
     * @param message текст сообщения
     */
    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.err.println("Не удалось отправить сообщение. " + e.getMessage());
        }
    }

    /**
     * Возвращает имя бота
     */
    @Override
    public String getBotUsername() {
        return telegramBotName;
    }
}
