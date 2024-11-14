package example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты логики бота в классе BotLogic
 */
public class BotLogicTests {
    private TestBot bot;
    private BotLogic botLogic;
    private User user;

    @BeforeEach
    public void setUp() {
        bot = new TestBot();
        botLogic = new BotLogic(bot);
        user = new User(1L);
    }

    /**
     * Тест обработки команды /start
     */
    @Test
    public void testCommand_Start() {
        botLogic.processCommand(user, "/start");

        Assertions.assertEquals("Привет!", bot.getLastMessage());
    }

    /**
     * Тест обработки команды /test и обработки ответов
     */
    @Test
    public void testCommand_Test() {
        botLogic.processCommand(user, "/test");

        Assertions.assertEquals("Вычислите степень: 10^2", bot.getLastMessage());

        botLogic.processCommand(user, "90");

        Assertions.assertEquals("Вы ошиблись, верный ответ: 100", bot.getMessageFromEnd(2));
        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());

        botLogic.processCommand(user, "6");

        Assertions.assertEquals("Правильный ответ!", bot.getMessageFromEnd(2));
        Assertions.assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * Тест обработки команды /stop и завершения теста
     */
    @Test
    public void testCommand_Stop() {
        botLogic.processCommand(user, "/stop");

        Assertions.assertEquals("Вы не начинали тестирование. Воспользуйтесь командой /help, " +
                        "чтобы прочитать инструкцию.", bot.getLastMessage());

        botLogic.processCommand(user, "/test");
        botLogic.processCommand(user, "/stop");

        Assertions.assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * Тест обработки команды /repeat и обработки ответов
     */
    @Test
    public void testCommand_Repeat() {
        botLogic.processCommand(user, "/test");
        botLogic.processCommand(user, "100");

        Assertions.assertEquals("Правильный ответ!", bot.getMessageFromEnd(2));
        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());

        botLogic.processCommand(user, "8");

        Assertions.assertEquals("Вы ошиблись, верный ответ: 6", bot.getMessageFromEnd(2));

        botLogic.processCommand(user, "/stop");
        botLogic.processCommand(user, "/repeat");

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());

        botLogic.processCommand(user, "6");

        Assertions.assertEquals("Правильный ответ!", bot.getMessageFromEnd(2));
        Assertions.assertEquals("Тест завершен", bot.getLastMessage());

        botLogic.processCommand(user, "/repeat");

        Assertions.assertEquals("Нет вопросов для повторения", bot.getLastMessage());
    }

    /**
     * Тест обработки команды /notify, установки текста и таймера напоминания
     */
    @Test
    public void testCommand_Notify() throws InterruptedException {
        botLogic.processCommand(user, "/notify");

        Assertions.assertEquals("Введите текст напоминания", bot.getLastMessage());

        botLogic.processCommand(user, "Написать тесты");

        Assertions.assertEquals("Через сколько секунд напомнить?", bot.getLastMessage());

        botLogic.processCommand(user, "1");

        Assertions.assertEquals(3, bot.getMessageCount());

        Thread.sleep(1020);

        Assertions.assertEquals(4, bot.getMessageCount());
        Assertions.assertEquals("Сработало напоминание: 'Написать тесты'", bot.getLastMessage());
    }

    /**
     * Тест обработки необрабатываемой команды
     */
    @Test
    public void testUnprocessableCommand() {
        botLogic.processCommand(user, "/unprocessable");

        Assertions.assertEquals("Такой команды пока не существует, или Вы допустили ошибку в написании. " +
                        "Воспользуйтесь командой /help, чтобы прочитать инструкцию.", bot.getLastMessage());
    }
}
