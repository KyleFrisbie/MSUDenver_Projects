import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

/**
 * @author Kyle L Frisbie
 * @version 1.3
 * Date: 6/9/15
 * Class: Networking, Dr. Zhu, Summer 2015
 *
 * Single thread instance of the HttpServer. Handles requests from user for
 * particular htm files located on the server. Writes back to client line by
 * line, as client request is handled.
 *
 * Please note that the base structure for this project was derived from
 * Dr. Zhu's TCP_ToUpperCase_MultiThread Project examples.
 */
public class HttpServerThread extends Thread{
    private Socket clientTCPSocket = null;
    private PrintWriter cSocketOut = null;
    private BufferedReader cSocketIn = null;
    private String fromClient, toClient = null;
    private File inputDataFile = null;
    private Scanner inputFile;

    /**
     * Constructor to create a new socket instance for this particular thread.
     * @param socket
     */
    public HttpServerThread(Socket socket) {
        super("HttpServerThread");
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
            cSocketIn = new BufferedReader(new InputStreamReader(
                    clientTCPSocket.getInputStream()));
        } catch (IOException e) {
            cSocketOut.println("An error occurred while " +
                    "initializing the socket.");
            System.exit(-1);
        }
    }

    /**
     * Process request line from client and output to console on server.
     * @return
     */
    private String[] processRequest() {
        // breakup incoming message header from client for processing
        String[] tokens = fromClient.split(" ");
        try {
            // output incoming http request to standard output
            // (console on server side)
            System.out.println(fromClient);
            System.out.println(cSocketIn.readLine());
            System.out.println(cSocketIn.readLine());
            System.out.println();
        } catch (IOException e) {
            cSocketOut.println("Error processing request");
        }
        return tokens;
    }

    /**
     * Checks request type in request header from client. Creates response
     * header in accordance with type of request and if file is located on
     * server.
     * Sends responses to client, line by line, including response header,
     * date request was processed, and server field.
     * @return
     */
    private boolean createResponseHeader() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // Contains: [0]requestType, [1]fileRequested, [2]httpVersion
        String[] statusItems = processRequest();
        boolean validRequest = false;

        if (statusItems[0].equalsIgnoreCase("get")) {
            try {
                inputDataFile = new File(statusItems[1].replaceAll("/", ""));
                inputFile = new Scanner(inputDataFile);
                validRequest = true;
                toClient = statusItems[2] + " 200 OK";
            } catch (FileNotFoundException e) {
                toClient = statusItems[2] + " 404 Not Found";
            }
        } else {
            toClient = statusItems[2] + " 400 Bad Request";
        }
        cSocketOut.println(toClient);
        Calendar cal = Calendar.getInstance();
        cSocketOut.println("Date: " + dateFormat.format(cal.getTime()));
        cSocketOut.println("KyleLFrisbie_Server");
        cSocketOut.println();

        return validRequest;
    }

    /**
     * Scans the requested file on server and sends back contents to client
     * line by line.
     * @param inputFile
     */
    private void scanRequestedFile(Scanner inputFile) {
        while (inputFile.hasNext()) {
            toClient = inputFile.nextLine();
            cSocketOut.println(toClient);
        }
    }

    /**
     * Generates general server response to client based on validity of client
     * request. Calls supporting methods to handle particular aspects of
     * server response.
     * Appends four empty lines to end of body of message to mark end of file.
     * @throws IOException
     */
    private void generateServerResponse() throws IOException {
        boolean validRequest = createResponseHeader();

        if (validRequest) {
            scanRequestedFile(inputFile);
        }
    }

    /**
     * Overridden method from thread which reads request from client, generates
     * response to client, and maintains thread as long as client has a new
     * request.
     */
    public void run() {
        initializeSocket();
        try {
            boolean userContinues;
            do {
                fromClient = cSocketIn.readLine();
                generateServerResponse();

                fromClient = cSocketIn.readLine();
                if(fromClient.equals("")) {
                    fromClient = cSocketIn.readLine();
                }
                userContinues = fromClient.equalsIgnoreCase("continue");
            } while (userContinues);

            cSocketIn.close();
            cSocketOut.close();
            clientTCPSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
