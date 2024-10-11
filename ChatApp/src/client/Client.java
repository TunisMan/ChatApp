import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost"; // Sunucu adresi
    private static final int SERVER_PORT = 8080; // Sunucu portu


    public static void main(String[] args)
    {

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {


            System.out.println("Connected to server.");


            // Kullanıcı adını sunucuya gönder
            System.out.print("Enter your username: ");
            String username = consoleInput.readLine();
            out.println(username); // Sunucuya kullanıcı adını gönder


            // Sunucudan gelen mesajları dinleyen thread
            new Thread(() ->
            {

                String messageFromServer;

                try {
                    while ((messageFromServer = in.readLine()) != null) {
                        System.out.println(messageFromServer); // Sunucudan gelen mesajı ekrana yazdır
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                out.println(username + ": "+ userInput); // Kullanıcıdan alınan mesajı sunucuya gönder
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}