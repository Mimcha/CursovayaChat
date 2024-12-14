import org.example.ChatClient;
import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChatClientTest {
    private ChatClient client;

    @BeforeEach
    public void setUp() {
        client = new ChatClient();
    }

    @Test
    public void testLogMessage() {
        String message = "Test message";
        client.logMessage(message);

        // Проверяем, что сообщение записано в файл
        try (BufferedReader br = new BufferedReader(new FileReader("client.log"))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.equals(message)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        } catch (IOException e) {
            fail("IOException occurred while reading log file");
        }
    }
}