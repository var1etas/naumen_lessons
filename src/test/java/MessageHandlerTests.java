import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.urfu.MessageHandler;

/**
 * Тест обработки сообщений в классе EchoMessenger
 */
public class MessageHandlerTests {
    private MessageHandler messageHandler;

    /**
     * Создаем экземпляр класса
     */
    @BeforeEach
    public void setup() {
        messageHandler = new MessageHandler();
    }

    /**
     * Проверка преобразования обычного сообщения в эхо-сообщение
     */
    @Test
    public void processEchoMessageTest(){
        String message = "привет";
        String acceptMessage = messageHandler.convertMessageToEcho(message);
        String expectedMessage = "Ваше сообщение: 'привет'";

        Assertions.assertEquals(expectedMessage, acceptMessage);
    }
}
