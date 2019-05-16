import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class CustConnection {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final Thread readThread;
    private final ConnEventListener connEventListener;

    public CustConnection(ConnEventListener connEventListener,String host, int port) throws IOException {
        this(connEventListener ,new Socket(host,port));
    }

    public CustConnection(ConnEventListener conEventListener,Socket sock) throws IOException {
        this.socket = sock;
        this.connEventListener = conEventListener;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!readThread.isInterrupted()){
                        connEventListener.onReceiveMessage(in.readLine());
                    }
                } catch (IOException e) {
                    connEventListener.onConnectionError(CustConnection.this);
                }
            }
        });
        readThread.start();
    }

    public void postMessage(String message) throws IOException {
        out.write(message + "\r\n");
        out.flush();
    }



}
