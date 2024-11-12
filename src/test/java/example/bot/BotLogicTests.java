package example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты логики бота в классе BotLogic
 */
public class BotLogicTests {
    private final String HELP_INFO = """
            Бот для обучения. Ты можешь проходить здесь тесты и проверять свой скилл.
            Просто используй:
            /start - для запуска бота
            /help - если тебе вдруг что-то стало непонятно
            /test - для запуска теста
            /stop - для завершения работы в режиме теста
            /notify - отправить уведомление с текстом <text> через <seconds> секунд
            /repeat - повторно задать вопросы, на которые был дан неправильный ответ (как только дан правильный ответ, вопрос удаляется списка на повторение)""";

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
        bot.onMessageReceived("/start", user, botLogic);

        Assertions.assertEquals("Привет!", bot.getMessageList().getLast());
        Assertions.assertEquals(State.INIT, user.getState());
    }

    /**
     * Тест обработки команды /help
     */
    @Test
    public void testCommand_Help() {
        bot.onMessageReceived("/help", user, botLogic);

        Assertions.assertEquals(HELP_INFO, bot.getMessageList().getLast());
    }

    /**
     * Тест обработки команды /test, обработки ответов
     * в processNonCommand(), checkTestAnswer() и checkAnswer()
     */
    @Test
    public void testCommand_Test() {
        bot.onMessageReceived("/test", user, botLogic);

        Assertions.assertEquals(State.TEST, user.getState());
        Assertions.assertEquals("Вычислите степень: 10^2", bot.getMessageList().getLast());

        bot.onMessageReceived("90", user, botLogic);

        Assertions.assertEquals("Вы ошиблись, верный ответ: 100", bot.getMessageList().get(bot.getMessageList().size() - 2));
        Assertions.assertEquals("Вычислите степень: 10^2", user.getCurrentWrongAnswerQuestion().get().text());
        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getMessageList().getLast());

        bot.onMessageReceived("6", user, botLogic);

        Assertions.assertEquals("Правильный ответ!", bot.getMessageList().get(bot.getMessageList().size() - 2));
        Assertions.assertEquals("Тест завершен", bot.getMessageList().getLast());
    }

    /**
     * Тест обработки команды /stop и завершения теста в processStop()
     */
    @Test
    public void testCommand_Stop() {
        bot.onMessageReceived("/stop", user, botLogic);

        Assertions.assertEquals("Вы не начинали тестирование. Воспользуйтесь командой /help, чтобы прочитать инструкцию.",
                bot.getMessageList().getLast());

        bot.onMessageReceived("/test", user, botLogic);
        bot.onMessageReceived("/stop", user, botLogic);

        Assertions.assertEquals(bot.getMessageList().getLast(), "Тест завершен");
        Assertions.assertEquals(State.INIT, user.getState());
    }

    /**
     * Тест обработки команды /repeat, обработки ответов
     * в processNonCommand(), checkRepeatTestAnswer() и checkAnswer()
     */
    @Test
    public void testCommand_Repeat() {
        bot.onMessageReceived("/test", user, botLogic);
        bot.onMessageReceived("100", user, botLogic);
        bot.onMessageReceived("/repeat", user, botLogic);

        Assertions.assertEquals("Нет вопросов для повторения", bot.getMessageList().getLast());

        bot.onMessageReceived("/test", user, botLogic);
        bot.onMessageReceived("90", user, botLogic);
        bot.onMessageReceived("/repeat", user, botLogic);

        Assertions.assertEquals(State.REPEAT, user.getState());
        Assertions.assertEquals("Вычислите степень: 10^2", bot.getMessageList().getLast());

        bot.onMessageReceived("100", user, botLogic);

        Assertions.assertEquals("Правильный ответ!", bot.getMessageList().get(bot.getMessageList().size() - 2));
        Assertions.assertEquals("Тест завершен", bot.getMessageList().getLast());

        bot.onMessageReceived("/repeat", user, botLogic);

        Assertions.assertEquals("Нет вопросов для повторения", bot.getMessageList().getLast());
    }

    /**
     * Тест обработки команды /notify, установки текста и таймера напоминания в processNonCommand()
     */
    @Test
    public void testCommand_Notify() throws InterruptedException {
        bot.onMessageReceived("/notify", user, botLogic);

        Assertions.assertEquals(State.SET_NOTIFY_TEXT, user.getState());
        Assertions.assertEquals("Введите текст напоминания", bot.getMessageList().getLast());

        bot.onMessageReceived("Написать тесты", user, botLogic);

        Assertions.assertEquals(State.SET_NOTIFY_DELAY, user.getState());
        Assertions.assertEquals("Через сколько секунд напомнить?", bot.getMessageList().getLast());

        bot.onMessageReceived("1", user, botLogic);

        Assertions.assertEquals(State.INIT, user.getState());
        Assertions.assertNotEquals("Сработало напоминание: 'Написать тесты'", bot.getMessageList().getLast());

        Thread.sleep(1020);

        Assertions.assertEquals("Сработало напоминание: 'Написать тесты'", bot.getMessageList().getLast());
    }

    /**
     * Тест обработки необрабатываемой команды
     */
    @Test
    public void testUnprocessableCommand() {
        bot.onMessageReceived("/unprocessable", user, botLogic);

        Assertions.assertEquals("Такой команды пока не существует, или Вы допустили ошибку в написании. Воспользуйтесь командой /help, чтобы прочитать инструкцию.",
                bot.getMessageList().getLast());
    }
}
