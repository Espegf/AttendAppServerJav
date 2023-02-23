import java.io.*;
import java.net.Socket;

public class Client {
    private final DataInputStream INPUT;
    private final DataOutputStream OUTPUT;
    private final Socket SOCKET;

    // Constructor
    public Client(Socket SOCKET) throws IOException {
        this.SOCKET = SOCKET;
        INPUT = new DataInputStream(SOCKET.getInputStream());
        OUTPUT = new DataOutputStream(SOCKET.getOutputStream());
        System.out.println("-- socket " + getIp() + " connected");
    }

    // Getters
    public Socket getSocket() {
        return SOCKET;
    }

    public String getIp() {
        return SOCKET.getInetAddress().getHostAddress();
    }

    public void close() throws IOException {
        SOCKET.close();
        INPUT.close();
        OUTPUT.close();
        System.out.println("-- socket " + getIp() + " disconnected");
    }

    public String receiveMessage() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(INPUT, "UTF-8");

// Leer los caracteres enviados desde el cliente y guardarlos en un StringBuilder
        StringBuilder stringBuilder = new StringBuilder();
        int c;
        while ((c = inputStreamReader.read()) != -1) {
            stringBuilder.append((char)c);
        }
        String data = stringBuilder.toString();

// Imprimir los datos recibidos desde el cliente
        System.out.println("Data received from client: " + data);
        return data;
    }


    @Override
    public String toString() {
        return "Client{" +
                "SOCKET=" + getIp() +
                '}';
    }
}