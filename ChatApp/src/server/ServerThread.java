import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerThread extends Thread
    {

        private Socket socket; // İstemci ile sunucu arasındaki bağlantıyı temsil eder
        private BufferedReader in; // İstemciden gelen mesajları okumak için
        private PrintWriter out; // İstemciye mesaj göndermek için
        private ArrayList<ServerThread> clientThreads; // Tüm bağlı istemcilerin listesi

        public ServerThread(Socket socket, ArrayList<ServerThread> clientThreads)
        {

            this.socket = socket;
            this.clientThreads = clientThreads;
        }

        @Override
        public void run()
        {
            try {

                in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // İstemciden veri almak için input stream
                out = new PrintWriter(socket.getOutputStream(), true); // İstemciye veri göndermek için output stream

                // Kullanıcı adını al
                
                String username = in.readLine(); // Kullanıcı adını alıyoruz
                broadcastMessage(username + " has joined the chat!"); // Yeni katılan kullanıcıyı duyur

                String message;

                while ((message = in.readLine()) != null) // İstemciden mesaj geldiği sürece
                {
                    broadcastMessage(message); // Mesajı diğer istemcilere gönderir.
                }

            } catch (IOException e)
            {

                e.printStackTrace();

            } finally {

                try
                {

                    socket.close(); // Bağlantıyı Kapat

                } catch (IOException e)
                {
                    e.printStackTrace();
                }

            }

        }

        private void broadcastMessage (String message)
        {
            for (ServerThread client : clientThreads) //Diğer tüm istemcilere mesaj gönder.
            {
                client.out.println(message); // Mesajı diğer istemcilere yolla.
            }
        }

    }