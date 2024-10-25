package ru.urfu;

/**
 * Класс для запуска приложения
 */
public class Application {

    public static void main(String[] args) {
        String telegramBotName = System.getenv("telegram_botName");
        String telegramToken = System.getenv("telegram_token");
        EchoBot echoBot = new EchoBot();
        new TelegramBot(telegramBotName, telegramToken, echoBot)
                .start();

        String discordToken = System.getenv("discord_token");
        new DiscordBot(discordToken, echoBot)
                .start();

        // сколько угодно чат платформ и все должны работать одинаково
    }

}
