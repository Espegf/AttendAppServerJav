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
       /* INPUT.readUTF();
        InputStreamReader ir = new InputStreamReader(INPUT);
        StringBuilder msj = new StringBuilder();
        String line = "";
        while ((line = ir.) != null) {
            msj.append(line);
            msj.append(System.lineSeparator());
        }

        */
        return INPUT.readUTF();
    }


    @Override
    public String toString() {
        return "Client{" +
                "SOCKET=" + getIp() +
                '}';
    }
}