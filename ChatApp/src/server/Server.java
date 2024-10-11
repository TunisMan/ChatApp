import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;


public class Server
    {

        private static final int PORT = 8080; // Sunucunun dinleyeceği port numarası
        private static ArrayList<ServerThread> clientThreads = new ArrayList<>(); // Bağlanan istemcileri tutan liste

        public static void main(String[] args)
        {

            try (ServerSocket serverSocket = new ServerSocket(PORT))  // Sunucuyu başlat ve istemci bağlantılarını bekle
            {

                System.out.println("Server Started at Port: " + PORT);

                while (true)
                {
                    Socket clientSocket =serverSocket.accept(); // İstemci bağlantısını kabul et.
                    ServerThread clientThread = new ServerThread(clientSocket, clientThreads); // Her istemci için bir thread başlat
                    clientThreads.add((clientThread)); // Yeni bağlantıyı listeye ekle
                    clientThread.start(); // İstemci thread'ini çalıştır
                }

            } catch (IOException e) {
                 e.printStackTrace();
            }

        }


    }