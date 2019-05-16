import java.io.*;
import java.util.Scanner;

public class Client implements ConnEventListener{

    private final String nickName;

    public static void main(String[] args) throws IOException {
        new Client();
    }

    private Client() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input your nick name:");
        nickName = scanner.nextLine();
        CustConnection conn = new CustConnection(this,"localhost",19191);
        conn.postMessage(nickName+" joined the chat");
        while (true){
            conn.postMessage(nickName+": "+scanner.nextLine());
        }
    }

    public void onReceiveMessage(String message) {
        System.out.println(message);
    }

    public void onConnectionError(CustConnection custConnection) {
        System.out.println("Server is down. Bye.");
        System.exit(0);
    }
}
