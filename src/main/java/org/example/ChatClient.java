package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String LOG_FILE = "client.log";
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) {
        new ChatClient().start();
    }

    public void start() {
        try {
            Socket socket = new Socket("localhost", 12345);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            // Получаем имя пользователя
            String username = in.readLine();
            System.out.println(username);
            out.println(scanner.nextLine());

            // Поток для получения сообщений
            new Thread(new IncomingMessageHandler()).start();

            // Отправка сообщений
            String message;
            while (true) {
                message = scanner.nextLine();
                if (message.equalsIgnoreCase("/exit")) {
                    out.println(message);
                    break;
                }
                out.println(message);
                logMessage(username + ": " + message);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logMessage(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class IncomingMessageHandler implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                    logMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}