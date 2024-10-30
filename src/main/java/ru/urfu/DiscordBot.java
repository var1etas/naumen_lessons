package ru.urfu;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

/**
 * Дискорд бот
 */
public class DiscordBot implements Bot{
    private final String token;
    private final EchoMessenger echoMessenger;
    private GatewayDiscordClient client;

    /**
     * Конструктор дискорд бота, для создания экземпляра требуется передать токен
     * и сервис для обработки и отправки эхо-сообщений
     */
    public DiscordBot(String token, EchoMessenger echoMessenger) {
        this.token = token;
        this.echoMessenger = echoMessenger;
    }

    /**
     * Метод для запуска бота и обработки входящих сообщений
     * @throws RuntimeException если возникла ошибка при входе в дискорд или в работе бота
     */
    public void start() {
        client = DiscordClient.create(token).login().block();
        if (client == null) {
            throw new RuntimeException("Ошибка при входе в Discord");
        }
        client.on(MessageCreateEvent.class)
                .doOnError(throwable -> {
                    throw new RuntimeException("Ошибка при работе Discord бота", throwable);
                })
                .subscribe(event -> {
                    Message eventMessage = event.getMessage();
                    if (eventMessage.getAuthor().map(user -> !user.isBot()).orElse(false)) {
                        String chatId = eventMessage.getChannelId().asString();
                        String messageFromUser = eventMessage.getContent();
                        echoMessenger.sendEchoMessage(messageFromUser, chatId, this);
                    }
                });
        System.out.println("Discord бот запущен");
        client.onDisconnect().block();
    }

    /**
     * Отправить сообщение заданному получателю
     * @param chatId идентификатор чата
     * @param message текст сообщения
     */
    @Override
    public void sendMessage(String chatId, String message) {
        Snowflake channelId = Snowflake.of(chatId);
        MessageChannel channel = client.getChannelById(channelId).ofType(MessageChannel.class).block();
        if (channel != null) {
            channel.createMessage(message).block();
        } else {
            System.err.println("Канал не найден");
        }
    }
}
