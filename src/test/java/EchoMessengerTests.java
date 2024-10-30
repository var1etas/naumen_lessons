import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.urfu.EchoMessenger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoMessengerTests {
    private EchoMessenger echoMessenger;

    @BeforeEach
    public void setup() {
        echoMessenger = new EchoMessenger();
    }

    @Test
    public void processEchoMessageTest(){
        String message = "привет";
        String sendMessage = echoMessenger.getEchoMessage(message);
        String expectedMessage = String.format("Ваше сообщение: '%s'", message);

        assertEquals(sendMessage, expectedMessage);
    }
}
