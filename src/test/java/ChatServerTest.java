import org.example.ChatServer;
import org.junit.jupiter.api.*;
import java.io.*;
import java.net.*;

import static org.junit.jupiter.api.Assertions.*;

public class ChatServerTest {
    private ChatServer server;

    @BeforeEach
    public void setUp() {
        server = new ChatServer();
    }

    @Test
    public void testLogMessage() {
        String message = "Test message";
        server.logMessage(message);

        // Проверяем, что сообщение записано в файл
        try (BufferedReader br = new BufferedReader(new FileReader("server.log"))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.contains(message)) {
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