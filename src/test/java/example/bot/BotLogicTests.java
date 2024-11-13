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
        Assertions.assertEquals(State.INIT, user.getState());
    }

    /**
     * Тест обработки команды /help
     */
    @Test
    public void testCommand_Help() {
        botLogic.processCommand(user, "/help");
        String HELP_INFO = """
                Бот для обучения. Ты можешь проходить здесь тесты и проверять свой скилл.
                Просто используй:
                /start - для запуска бота
                /help - если тебе вдруг что-то стало непонятно
                /test - для запуска теста
                /stop - для завершения работы в режиме теста
                /notify - отправить уведомление с текстом <text> через <seconds> секунд
                /repeat - повторно задать вопросы, на которые был дан неправильный ответ (как только дан правильный ответ, вопрос удаляется списка на повторение)""";


        Assertions.assertEquals(HELP_INFO, bot.getLastMessage());
    }

    /**
     * Тест обработки команды /test, обработки ответов
     * в processNonCommand(), checkTestAnswer() и checkAnswer()
     */
    @Test
    public void testCommand_Test() {
        botLogic.processCommand(user, "/test");

        Assertions.assertEquals(State.TEST, user.getState());
        Assertions.assertEquals("Вычислите степень: 10^2", bot.getLastMessage());

        botLogic.processCommand(user, "90");

        Assertions.assertEquals("Вы ошиблись, верный ответ: 100", bot.getMessageFromEnd(2));
        Assertions.assertEquals("Вычислите степень: 10^2", user.getCurrentWrongAnswerQuestion().get().text());
        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());

        botLogic.processCommand(user, "6");

        Assertions.assertEquals("Правильный ответ!", bot.getMessageFromEnd(2));
        Assertions.assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * Тест обработки команды /stop и завершения теста в processStop()
     */
    @Test
    public void testCommand_Stop() {
        botLogic.processCommand(user, "/stop");

        Assertions.assertEquals("Вы не начинали тестирование. Воспользуйтесь командой /help, чтобы прочитать инструкцию.",
                bot.getLastMessage());

        botLogic.processCommand(user, "/test");
        botLogic.processCommand(user, "/stop");

        Assertions.assertEquals("Тест завершен", bot.getLastMessage());
        Assertions.assertEquals(State.INIT, user.getState());
    }

    /**
     * Тест обработки команды /repeat, обработки ответов
     * в processNonCommand(), checkRepeatTestAnswer() и checkAnswer()
     */
    @Test
    public void testCommand_Repeat() {
        botLogic.processCommand(user, "/test");
        botLogic.processCommand(user, "100");
        botLogic.processCommand(user, "/repeat");

        Assertions.assertEquals("Нет вопросов для повторения", bot.getLastMessage());

        botLogic.processCommand(user, "8");
        botLogic.processCommand(user, "/repeat");

        Assertions.assertEquals(State.REPEAT, user.getState());
        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());

        botLogic.processCommand(user, "6");

        Assertions.assertEquals("Правильный ответ!", bot.getMessageFromEnd(2));
        Assertions.assertEquals("Тест завершен", bot.getLastMessage());

        botLogic.processCommand(user, "/repeat");

        Assertions.assertEquals("Нет вопросов для повторения", bot.getLastMessage());
    }

    /**
     * Тест обработки команды /notify, установки текста и таймера напоминания в processNonCommand()
     */
    @Test
    public void testCommand_Notify() throws InterruptedException {
        botLogic.processCommand(user, "/notify");

        Assertions.assertEquals(State.SET_NOTIFY_TEXT, user.getState());
        Assertions.assertEquals("Введите текст напоминания", bot.getLastMessage());

        botLogic.processCommand(user, "Написать тесты");

        Assertions.assertEquals(State.SET_NOTIFY_DELAY, user.getState());
        Assertions.assertEquals("Через сколько секунд напомнить?", bot.getLastMessage());

        botLogic.processCommand(user, "1");

        Assertions.assertEquals(State.INIT, user.getState());
        Assertions.assertNotEquals("Сработало напоминание: 'Написать тесты'", bot.getLastMessage());

        Thread.sleep(1020);

        Assertions.assertEquals("Сработало напоминание: 'Написать тесты'", bot.getLastMessage());
    }

    /**
     * Тест обработки необрабатываемой команды
     */
    @Test
    public void testUnprocessableCommand() {
        botLogic.processCommand(user, "/unprocessable");

        Assertions.assertEquals("Такой команды пока не существует, или Вы допустили ошибку в написании. Воспользуйтесь командой /help, чтобы прочитать инструкцию.",
                bot.getLastMessage());
    }
}
