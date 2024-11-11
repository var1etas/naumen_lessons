package example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты логики бота в классе BotLogic
 */
public class BotLogicTests {

    private TestBot bot;
    private Question question1;
    private Question question2;

    @BeforeEach
    public void setUp() {
        bot = new TestBot();
        question1 = new Question("Вычислите степень: 10^2", "100");
        question2 = new Question("Сколько будет 2 + 2 * 2", "6");
    }

    /**
     * Тест обработки команды /start
     */
    @Test
    public void testCommand_Start() {
        bot.onMessageReceived("/start");

        Assertions.assertEquals("Привет!", bot.getChat().getLast());
        Assertions.assertEquals(State.INIT, bot.getUser().getState());
    }

    /**
     * Тест обработки команды /help
     */
    @Test
    public void testCommand_Help() {
        bot.onMessageReceived("/help");

        Assertions.assertEquals(Constants.HELP_INFO, bot.getChat().getLast());
    }

    /**
     * Тест обработки команды /test, обработки ответов
     * в processNonCommand(), checkTestAnswer() и checkAnswer()
     */
    @Test
    public void testCommand_Test() {
        bot.onMessageReceived("/test");

        Assertions.assertEquals(State.TEST, bot.getUser().getState());
        Assertions.assertEquals(question1.text(), bot.getChat().getLast());

        bot.onMessageReceived("90");

        Assertions.assertEquals("Вы ошиблись, верный ответ: 100", bot.getChat().get(bot.getChat().size()-2));
        Assertions.assertEquals(question1, bot.getUser().getCurrentWrongAnswerQuestion().get());
        Assertions.assertEquals(question2.text(), bot.getChat().getLast());

        bot.onMessageReceived("6");

        Assertions.assertEquals("Правильный ответ!", bot.getChat().get(bot.getChat().size()-2));
        Assertions.assertEquals("Тест завершен", bot.getChat().getLast());
    }

    /**
     * Тест обработки команды /stop и завершения теста в processStop()
     */
    @Test
    public void testCommand_Stop() {
        bot.onMessageReceived("/stop");
        Assertions.assertEquals("Вы не начинали тестирование. Воспользуйтесь командой /help, чтобы прочитать инструкцию.",
                bot.getChat().getLast());

        bot.onMessageReceived("/test");
        bot.onMessageReceived("/stop");
        Assertions.assertEquals(bot.getChat().getLast(), "Тест завершен");
        Assertions.assertEquals(State.INIT, bot.getUser().getState());
    }

    /**
     * Тест обработки команды /repeat, обработки ответов
     * в processNonCommand(), checkRepeatTestAnswer() и checkAnswer()
     */
    @Test
    public void testCommand_Repeat() {
        bot.onMessageReceived("/test");
        bot.onMessageReceived("90");
        bot.onMessageReceived("/repeat");

        Assertions.assertEquals(State.REPEAT, bot.getUser().getState());
        Assertions.assertEquals("Вычислите степень: 10^2", bot.getChat().getLast());

        bot.onMessageReceived("100");

        Assertions.assertEquals("Правильный ответ!", bot.getChat().get(bot.getChat().size()-2));
        Assertions.assertEquals("Тест завершен", bot.getChat().getLast());

        bot.onMessageReceived("/repeat");

        Assertions.assertEquals("Нет вопросов для повторения", bot.getChat().getLast());
    }

    /**
     * Тест обработки команды /notify, установки текста и таймера напоминания в processNonCommand()
     */
    @Test
    public void testCommand_Notify() throws InterruptedException {
        bot.onMessageReceived("/notify");

        Assertions.assertEquals(State.SET_NOTIFY_TEXT, bot.getUser().getState());
        Assertions.assertEquals("Введите текст напоминания", bot.getChat().getLast());

        bot.onMessageReceived("Написать тесты");

        Assertions.assertEquals(State.SET_NOTIFY_DELAY, bot.getUser().getState());
        Assertions.assertEquals("Через сколько секунд напомнить?", bot.getChat().getLast());

        bot.onMessageReceived("1");

        Assertions.assertEquals(State.INIT, bot.getUser().getState());

        Thread.sleep(1020);

        Assertions.assertEquals("Сработало напоминание: 'Написать тесты'", bot.getChat().getLast());
    }

    /**
     * Тест обработки необрабатываемой команды
     */
    @Test
    public void testUnprocessableCommand() {
        bot.onMessageReceived("/unprocessable");
        Assertions.assertEquals("Такой команды пока не существует, или Вы допустили ошибку в написании. Воспользуйтесь командой /help, чтобы прочитать инструкцию.",
                bot.getChat().getLast());
    }
}
