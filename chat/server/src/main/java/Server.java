import java.io.IOException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server implements ConnEventListener{

    private final ArrayList<CustConnection> connections = new ArrayList();

    public static void main(String[] args) throws IOException {
        new Server();
    }

    private Server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(19191);
        System.out.println("Server up and running");
        while (true) {
            connections.add(new CustConnection(this,serverSocket.accept()));
            System.out.println("New user connected. Online count = " + connections.size());
        }
    }

    private void sendMessageToAll(String message) {
        System.out.println(message);
        for (int i = 0; i<connections.size(); i++){
            try {
                connections.get(i).postMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onReceiveMessage(String message) {
        sendMessageToAll(message);
    }

    public void onConnectionError(CustConnection custConnection) {
        connections.remove(custConnection);
        System.out.println("User disconnected. Online count = " + connections.size());
    }
}
