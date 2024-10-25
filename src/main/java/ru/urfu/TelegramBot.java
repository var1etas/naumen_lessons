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
    EchoBot echoBot;

    private final String telegramBotName;
    private String lastMessage;

    public TelegramBot(String telegramBotName, String token, EchoBot echoBot) {
        super(token);
        this.telegramBotName = telegramBotName;
        this.echoBot = echoBot;
    }

    public void start() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
            System.out.println("Telegram бот запущен");
        } catch (TelegramApiException e) {
            throw new RuntimeException("Не удалось запустить телеграм бота", e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message updateMessage = update.getMessage();
            Long chatId = updateMessage.getChatId();
            String messageFromUser = updateMessage.getText();
            echoBot.sendEchoMessage(String.valueOf(chatId), messageFromUser, this);
        }
    }

    /**
     * Отправить сообщение
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


    @Override
    public String getBotUsername() {
        return telegramBotName;
    }
}
