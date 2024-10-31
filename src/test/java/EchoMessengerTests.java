import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.urfu.EchoMessenger;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тест обработки сообщений в классе EchoMessenger
 */
public class EchoMessengerTests {
    private EchoMessenger echoMessenger;
    Method method;

    /**
     * Создаем экземпляр класса
     */
    @BeforeEach
    public void setup() {
        echoMessenger = new EchoMessenger();
    }

    /**
     * Проверка преобразования обычного сообщения в эхо-сообщение
     */
    @Test
    public void processEchoMessageTest(){
        String message = "привет";
        String sendMessage = getEchoMethod(message);
        String expectedMessage = String.format("Ваше сообщение: '%s'", message);

        assertEquals(expectedMessage, sendMessage);
    }

    /**
     * Получение приватного метода из EchoMessenger.class
     */
    public String getEchoMethod(String message){
        try {
            method = echoMessenger.getClass().getDeclaredMethod("getEchoMessage", String.class);
            method.setAccessible(true);
            return String.valueOf(method.invoke(echoMessenger, message));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
