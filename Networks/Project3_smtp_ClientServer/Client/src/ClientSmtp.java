import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author: Kyle L Frisbie
 * @date: 6/17/15
 * @version: 1.4

 * This is a Client program used to send smtp requests to the smtp server.
 * This is implemented based on Dr. Zhu's TCPClient program example.
 */

public class ClientSmtp {
    private String host, mailFrom, rcptTo, subject, data;
    private Scanner systemIn = new Scanner(System.in);
    private Scanner socketIn = null;
    private PrintWriter socketOut = null;
    private final int PORT = 5678;
    private Socket tcpSocket = null;


    /**
     * Get server host name from user.
     */
    private void requestHostName() {
        System.out.print("Please enter the host name or ip address of your " +
                "mail server: ");
        host = systemIn.nextLine();
    }

    /**
     * Connect to smtp server via TCP Socket.
     * Initialize output stream to server, and input stream from server.
     */
    private void initializeSocket() {
        requestHostName();
        try {
            tcpSocket = new Socket(host, PORT);
            try {
                socketOut = new PrintWriter(tcpSocket.getOutputStream(), true);
                try {
                    socketIn = new Scanner(
                            new InputStreamReader(tcpSocket.getInputStream()));

                    // display 220 response from server
                    System.out.println(socketIn.nextLine());
                } catch (IOException e) {
                    System.out.println("Unable to get input stream, " +
                            "exiting program.");
                    System.exit(-1);
                }
            } catch (IOException e) {
                System.out.println("Unable to send output stream, " +
                        "exiting program.");
                System.exit(-1);
            }
        } catch (IOException e) {
            System.out.println("Unable to connect to socket, " +
                    "exiting program.");
            System.exit(-1);
        }
    }

    /**
     * Send HELO, MAIL FROM, and RCPT TO messages to server based on user
     * input. Send email message after Server DATA response.
     */
    private void communicateWithServer() {
        long startTime;

        socketOut.println("HELO kyle.com");
        startTime = System.currentTimeMillis();
        System.out.println(socketIn.nextLine());
        displayRTT("HELO", startTime);

        startTime = System.currentTimeMillis();
        socketOut.println("MAIL FROM: " + mailFrom);
        System.out.println(socketIn.nextLine());
        displayRTT("MAIL FROM", startTime);

        startTime = System.currentTimeMillis();
        socketOut.println("RCPT TO: " + rcptTo);
        System.out.println(socketIn.nextLine());
        displayRTT("RCPT TO", startTime);

        startTime = System.currentTimeMillis();
        socketOut.println("DATA");
        System.out.println(socketIn.nextLine());
        socketOut.println(data);
        displayRTT("DATA", startTime);

        // user input email
        promptForEmail();
        startTime = System.currentTimeMillis();
        socketOut.println(data);
        System.out.println(socketIn.nextLine());
        displayRTT("Mail", startTime);
    }

    /**
     * Display RTT of specific request to server in ms.
     * @param procedure
     * @param startTime
     */
    private void displayRTT(String procedure, long startTime) {
        System.out.println(procedure + " RTT: " +
                (System.currentTimeMillis() - startTime) + "ms");
    }

    /**
     * Prompt user for specific email parameters.
     */
    private void promptForData() {
        StringBuilder header = new StringBuilder();

        // get email header
        System.out.println("From: <sender's email address> ");
        mailFrom = systemIn.nextLine();
        header.append(mailFrom + "\r\n");

        System.out.println("To: <receiver's email address> ");
        rcptTo = systemIn.nextLine();
        header.append(rcptTo + "\r\n");

        System.out.println("Subject: <email subject>");
        subject =systemIn.nextLine();
        header.append(subject + "\r\n\r\n");

        data = header.toString();

    }

    /**
     * Get email message from user, continue collecting email until user inputs
     * a '.' on a line by itself.
     */
    private void promptForEmail() {
        StringBuilder email = new StringBuilder();

        // get email message
        System.out.println("Email Contents: " +
                "conclude your email with a \".\" on its own line: ");

        String nextLine;

        while(!(nextLine = systemIn.nextLine()).equals(".")) {
            email.append(nextLine + "\r\n");
        }
        email.append(".");
        data = email.toString();
    }

    /**
     * Allow user to continue sending message until user specifies completion
     * with 'N'.
     * @return
     */
    private boolean moreMail() {
        System.out.print("Do you have more mail to send? " +
                "(type \"Y\" or \"N\"): ");
        boolean again = systemIn.nextLine().equalsIgnoreCase("Y");
        if(again) {
            socketOut.println("AGAIN");
        }
        return again;
    }

    /**
     * Close data streams to prevent leak.
     */
    private void closeDataStreams() {
        systemIn.close();
        socketIn.close();
        try {
            tcpSocket.close();
        } catch (IOException e) {
            System.out.println("Unable to close TCP socket.");
        }
    }

    /**
     * Prompt user for messages and communicate with server while user
     * selects to continue.
     * @param args
     */
    public static void main(String[] args) {
        ClientSmtp driver = new ClientSmtp();
        driver.initializeSocket();

        do {
            driver.promptForData();
            driver.communicateWithServer();
        } while(driver.moreMail());
        driver.socketOut.println("QUIT");

        driver.closeDataStreams();
        System.exit(0);
    }
}