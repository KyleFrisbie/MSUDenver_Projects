import java.io.*;
import java.net.Socket;
import java.rmi.UnexpectedException;

/**
 * @author Kyle L Frisbie
 * @version 1.3
 * Date: 6/9/15
 * Class: Networking, Dr. Zhu, Summer 2015
 *
 * Client program which requests user specified htm files from a host server.
 * Maintains thread to server for as long as the user has requests.
 * Receives response from server line by line, as request is processed.
 *
 * Please note that the base structure for this project was derived from
 * Dr. Zhu's TCP_ToUpperCase_MultiThread Project examples.
 */

public class HttpClient {
    private final int PORT = 5678;
    private Socket tcpSocket = null;
    private PrintWriter socketOut = null;
    private BufferedReader socketIn, systemIn = null;
    private String host, requestType, fileName, httpVersion,
            userAgent, fromServer, fromUser = new String();
    private long startTime;

    /**
     * Initialize connection to host server, output time to establish socket
     * connection.
     */
    private void connectToSocket() {
        try {
            startTime = System.currentTimeMillis();
            tcpSocket = new Socket(host, PORT);

            // Display RTT of establishing TCP connection
            // (difference in time before and after creating socket)
            System.out.println("Client: Time to establish TCP connection: " +
                    (System.currentTimeMillis() - startTime) + " ms");

            socketOut = new PrintWriter(tcpSocket.getOutputStream(), true);
            socketIn = new BufferedReader(
                    new InputStreamReader(tcpSocket.getInputStream()));
        } catch (UnexpectedException e) {
            System.out.println("Unable to find host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Unable to obtain I/O for the " +
                    "connection to: " + host);
            System.exit(1);
        }
    }

    /**
     * Prompt user to input address of host via console.
     */
    private void userInputHost() {
        System.out.print("Client: Please input the " +
                "DNS name/ip of your HTTP server: ");
        try {
            host = systemIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prompt user to input a series of values relative to the file they are
     * requesting from the server.
     * @throws IOException
     */
    private void httpRequestInfo() throws IOException {
        System.out.print("Client: Input HTTP method type: ");
        requestType = systemIn.readLine();
        System.out.print("Client: Input name of requested htm file " +
                "(be sure to include '/'): ");
        fileName = systemIn.readLine();
        System.out.print("Client: Input HTTP version: ");
        httpVersion = systemIn.readLine();
        System.out.print("Client: Input user agent: ");
        userAgent = systemIn.readLine();
    }

    /**
     * Package user provided values (regarding htm file request) to be sent to
     * server.
     */
    private void httpRequestMsg() {
        fromUser = (requestType + " " + fileName +
                " HTTP/" + httpVersion + "\r\n" +
                "Host: " + host + "\r\n" +
                "User-Agent: " + userAgent);
    }

    /**
     * Directs method calls to other methods which prompt user for input values
     * via the console.
     */
    private void getUserInput() {
        try {
            httpRequestInfo();
            httpRequestMsg();
            socketOut.println(fromUser);
            socketOut.println("");
        } catch (IOException e) {
            System.out.println("Client: error in processing request/response.");
        }
    }

    /**
     * Receives server provided response based on client request. Checks for
     * validity of response. Outputs ands stores response data accordingly.
     */
    private void getServerResponse() {
            FileWriter outFile = null;
            PrintWriter outputFile = null;

            int emptyLines = 0;
            boolean writeToFile = false;
            boolean badRequest = true;

        // try/catch for while loop/socketIn
        try {
            while ((emptyLines < 4) &&
                    ((fromServer = socketIn.readLine()) != null)) {

                if (!writeToFile && fromServer.contains("200 OK")) {
                    badRequest = false;

                    // try/catch for creating outFile
                    try {
                        outFile = new FileWriter(fileName.replaceAll("/", ""));
                    } catch (IOException e) {
                        System.out.println("Client: Error in creating file.");
                    }
                    outputFile = new PrintWriter(outFile);
                }
                if (fromServer.equals("")) {
                    if (badRequest) {
                        System.out.println("Server: " + fromServer);
                        break;
                    }
                    emptyLines++;
                    writeToFile = !badRequest;
                } else {
                    emptyLines = 0;
                }
                System.out.println("Server: " + fromServer);
                if (writeToFile) {
                    outputFile.println(fromServer);
                }
            }
        } catch (IOException e) {
            System.out.println("Client: Error in reading from input socket from server.");
        }
        try {
            if (!badRequest) {
                fromServer = socketIn.readLine();
                outputFile.println(fromServer);
                System.out.println(fromServer);
                outputFile.close();
            }
            socketIn.readLine();
        }catch (IOException e) {
            System.out.println("Client: Error in closing flie.");
        }
    }

    /**
     * Close sockets, buffered readers, and print writers before exiting
     * program.
     * @throws IOException
     */
    private void closeResources() throws IOException {
        socketOut.close();
        socketIn.close();
        systemIn.close();
        tcpSocket.close();
    }

    public static void main(String[] args) throws IOException {
        HttpClient driver = new HttpClient();
        driver.systemIn = new BufferedReader(new InputStreamReader(System.in));
        boolean again = true;

        // get host and connect to server
        driver.userInputHost();
        driver.connectToSocket();

        // keep connection to thread alive as long as user has more requests
        while (again) {
            driver.getUserInput();
            driver.getServerResponse();

            // display RTT of HTTP query:
            // (moment after response is received) - (moment request is sent)
            System.out.println("Client: Time to establish TCP connection: " +
                    (System.currentTimeMillis() - driver.startTime) + " ms");
            System.out.print("Client: Would you like to submit " +
                    "another request? (Enter y or n): ");

            // note that program isn't robust, it only accepts "y" or "n"
            again = driver.systemIn.readLine().equalsIgnoreCase("y");
            if(again) {
                driver.socketOut.println("continue");
            } else {
                driver.socketOut.println("exit");
            }
        }
        driver.closeResources();
    }
}
