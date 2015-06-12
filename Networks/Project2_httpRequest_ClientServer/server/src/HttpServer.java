import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Kyle L Frisbie
 * @version 1.3
 * Date: 6/9/15
 * Class: Networking, Dr. Zhu, Summer 2015
 *
 * Base server program which creates multiple threads to address multiple
 * requests to server.
 *
 * Please note that the base structure for this project was derived from
 * Dr. Zhu's TCP_ToUpperCase_MultiThread Project examples.
 */

public class HttpServer {
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
        HttpServer driver = new HttpServer();
        driver.newSocket();

        // continue listening for additional incoming client requests to create
        // additional threads.
        while(driver.listening) {
            new HttpServerThread(driver.serverTCPSocket.accept()).start();
        }
        driver.serverTCPSocket.close();
    }
}