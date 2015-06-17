import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Kyle on 6/17/2015.
 */
public class ServerSmtp {
    private ServerSocket serverTCPSocket = null;
    private final int PORT = 5678;
    private boolean listening = true;

    /**
     * Create new socket to listen for clients on hardcoded port
     */
    private void newSocket() {
        try {
            serverTCPSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println("From server: Could not listen on port: "
                    + PORT);
            System.exit(-1);
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSmtp driver = new ServerSmtp();
        driver.newSocket();

        // continue listening for additional incoming client requests to create
        // additional threads.
        while(driver.listening) {
            new SmtpServerThread(driver.serverTCPSocket.accept()).start();
        }
        driver.serverTCPSocket.close();
    }
}
