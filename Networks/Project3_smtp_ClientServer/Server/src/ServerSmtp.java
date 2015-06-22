import java.io.IOException;
import java.net.ServerSocket;

/**
 * @Author: Kyle L Frisbie
 * @Date: 6/17/2015.
 * @Version 1.1
 *
 * This is the base Serve class for processing multiple smtp requests from a
 * user. This is implemented based on Dr. Zhu's TCPServer program example.
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

    /**
     * Listen on port for new incoming user request, create new thread for each
     * user request.
     * @param args
     * @throws IOException
     */
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
