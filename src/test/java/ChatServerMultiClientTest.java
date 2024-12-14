import org.example.ChatServer;
import org.junit.jupiter.api.*;
import java.io.*;
import java.net.*;

import static org.mockito.Mockito.*;

public class ChatServerMultiClientTest {
    private ChatServer server;

    @BeforeEach
    public void setUp() {
        server = new ChatServer();
    }

    @Test
    public void testMultipleClients() throws IOException {
        // Создаем сокеты для имитации клиентов
        Socket client1 = mock(Socket.class);
        Socket client2 = mock(Socket.class);

        // Имитация потоков ввода/вывода
        PrintWriter out1 = new PrintWriter(new OutputStreamWriter(new ByteArrayOutputStream()), true);
        PrintWriter out2 = new PrintWriter(new OutputStreamWriter(new ByteArrayOutputStream()), true);

        when(client1.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        when(client2.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        // Запускаем обработчики клиентов
        ChatServer.ClientHandler handler1 = server.new ClientHandler(client1);
        ChatServer.ClientHandler handler2 = server.new ClientHandler(client2);

        handler1.start();
        handler2.start();

        // Отправляем сообщения от клиентов
        handler1.out.println("Hello from client 1");
        handler2.out.println("Hello from client 2");

        // Проверяем, что оба сообщения обработаны
        // Здесь можно добавить проверки на то, что сообщения были отправлены всем клиентам
    }
}
