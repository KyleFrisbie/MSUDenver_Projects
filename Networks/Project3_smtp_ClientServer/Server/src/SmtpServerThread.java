import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

/**
 * @Author: Kyle L Frisbie
 * @Date: 6/17/2015.
 * @Version 1.2
 *
 * This is a single thread instance for the ServerSmtp class which allows for
 * multiple instances of the ServerSmtp. This is implemented based on Dr. Zhu's
 * TCPMultithread program example.
 */
public class SmtpServerThread extends Thread {
    private Socket clientTCPSocket = null;
    private PrintWriter cSocketOut = null;
    private Scanner cSocketIn = null;
    private SocketAddress serverIP;

    /**
     * Constructor to create a new socket instance for this particular thread.
     *
     * @param socket
     */
    public SmtpServerThread(Socket socket) {
        super("SmtpServerThread");
        clientTCPSocket = socket;
    }

    /**
     * Initialize socket to accept incoming request from client and enable
     * outgoing responses to client from thread.
     */
    private void initializeSocket() {
        try {
            cSocketOut =
                    new PrintWriter(clientTCPSocket.getOutputStream(), true);
            cSocketIn = new Scanner(new InputStreamReader(
                    clientTCPSocket.getInputStream()));

            serverIP = clientTCPSocket.getLocalSocketAddress();
            cSocketOut.println("220 " + serverIP);
        } catch (IOException e) {
            cSocketOut.println("An error occurred while " +
                    "initializing the socket.");
            System.exit(-1);
        }
    }

    /**
     * Check smtp request messages from client against expected values. If
     * client request is incorrect/out of order, respond with appropriate
     * message and wait for correct response.
     */
    private void processDataTransfer() {
        String[] tokens;
        String nextLine;

        do {
            nextLine = cSocketIn.nextLine();
            tokens = nextLine.split(" ");
            if (!tokens[0].equals("HELO")) {
                cSocketOut.println("503 5.5.2 Send hello first.");
            }
        } while (!tokens[0].equals("HELO"));
        //todo: server and client ip
        cSocketOut.println("250 <" + serverIP + "> Hello <" +
                clientTCPSocket.getRemoteSocketAddress() +">.");

        do {
            nextLine = cSocketIn.nextLine();
            tokens = nextLine.split(" ");
            if ((tokens.length < 2) || (!tokens[0].equals("MAIL") && !tokens[1].equals("FROM"))) {
                cSocketOut.println("503 5.5.2 Need mail command.");
            }
        } while ((tokens.length < 2) || (!tokens[0].equals("MAIL") && !tokens[1].equals("FROM")));
        cSocketOut.println("250 2.1.0 Sender OK.");

        do {
            nextLine = cSocketIn.nextLine();
            tokens = nextLine.split(" ");
            if ((tokens.length < 2) || (!tokens[0].equals("RCPT") && !tokens[1].equals("TO"))) {
                cSocketOut.println("503 5.5.2 Need rcpt command.");
            }
        } while ((tokens.length < 2) || (!tokens[0].equals("RCPT") && !tokens[1].equals("TO")));
        cSocketOut.println("250 2.1.5 Recipient OK.");

        do {
            nextLine = cSocketIn.nextLine();
            tokens = nextLine.split(" ");
            if (!tokens[0].equals("DATA")) {
                cSocketOut.println("503 5.5.2 Need data command.");
            }
        } while (!tokens[0].equals("DATA"));

        processClientEmail();
    }

    /**
     * Prompt user to send email contents, concluded by a '.'.
     */
    private void processClientEmail() {
        cSocketOut.println("354 Start mail input; end with <CRLF>.<CRLF>");

        String nextLine;

        while (!(nextLine = cSocketIn.nextLine()).equals(".")) {
            System.out.println(nextLine);
        }

        cSocketOut.println("250 Message received and to be delivered.");
    }

    /**
     * Check for user continue response.
     * @return
     */
    private boolean clientContinues() {
        return !cSocketIn.nextLine().equals("QUIT");
    }

    /**
     * Close resources to prevent data leak.
     */
    private void closeDataStreams() {
        //todo serverIp
        cSocketOut.println("221 <servers ip> closing connection");
        cSocketOut.close();
        cSocketIn.close();
        try {
            clientTCPSocket.close();
        } catch (IOException e) {
            System.out.println("Unable to close TCP socket.");
        }
    }

    /**
     * Override super's run method with local method calls.
     */
    public void run() {
        initializeSocket();
        do {
            processDataTransfer();
        } while (clientContinues());

        closeDataStreams();
    }
}
