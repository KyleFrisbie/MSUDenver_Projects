import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author: Kyle L Frisbie
 * @date: 6/17/15
 * @version: 1.0
 *
 */

public class ClientSmtp {
    private String host, mailFrom, rcptTo, subject, data;
    private Scanner systemIn = new Scanner(System.in);
    private Scanner socketIn = null;
    private PrintWriter socketOut = null;
    private final int PORT = 5678;
    private Socket tcpSocket = null;


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
    }

    private void displayRTT(String procedure, long startTime) {
        System.out.println(procedure + " RTT: " +
                (System.currentTimeMillis() - startTime) + "ms");
    }

    private void promptForData() {
        StringBuilder email = new StringBuilder();

        // get email header
        System.out.println("From: <sender's email address> ");
        mailFrom = systemIn.nextLine();
        email.append(mailFrom + "\r\n");

        System.out.println("To: <receiver's email address> ");
        rcptTo = systemIn.nextLine();
        email.append(rcptTo + "\r\n");

        System.out.println("Subject: <email subject>");
        subject =systemIn.nextLine();
        email.append(subject + "\r\n\r\n");

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

    private boolean moreMail() {
        System.out.print("Do you have more mail to send? " +
                "(type \"Y\" or \"N\"): ");
        boolean again = systemIn.nextLine().equalsIgnoreCase("Y");
        if(again) {
            socketOut.println("AGAIN");
        }
        return again;
    }

    private void closeDataStreams() {
        systemIn.close();
        socketIn.close();
        try {
            tcpSocket.close();
        } catch (IOException e) {
            System.out.println("Unable to close TCP socket.");
        }
    }

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