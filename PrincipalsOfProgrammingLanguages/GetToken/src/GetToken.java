import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Kyle on 2/24/2015.
 */
public class GetToken {
    private Scanner fileIn;
    private PrintWriter outFile;
    private BufferedReader reader;
    private HashMap tokenValues = new HashMap(31);

    private void initializeIO() {
        File inputFile = new File("InputFile.txt");
        File outputFile = new File("OutputFile.txt");
        try {
            FileReader file = new FileReader(inputFile);
            reader = new BufferedReader(file);
            fileIn = new Scanner(file);
            outFile = new PrintWriter(outputFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
            System.exit(1);
        }
        createHashMap();
    }

    private void stdOutput(String str) {
        System.out.println(tokenValues.get(str) + "\t" + str);
        outFile.println(tokenValues.get(str) + "\t" + str);
    }

    private void stdOutput(String key, String str) {
        System.out.println(tokenValues.get(key) + "\t" + str);
        outFile.println(tokenValues.get(key) + "\t" + str);
    }

    private void createHashMap() {
        tokenValues.put("IF", 1);
        tokenValues.put("THEN", 2);
        tokenValues.put("ELSE", 3);
        tokenValues.put("FI", 4);
        tokenValues.put("LOOP", 5);
        tokenValues.put("BREAK", 6);
        tokenValues.put("READ", 7);
        tokenValues.put("REPEAT", 8);
        tokenValues.put("PRINT", 9);
        tokenValues.put("AND", 10);
        tokenValues.put("OR", 11);
        tokenValues.put(")", 12);
        tokenValues.put("(", 13);
        tokenValues.put("/", 14);
        tokenValues.put("*", 15);
        tokenValues.put("-", 16);
        tokenValues.put("+", 17);
        tokenValues.put("<>", 18);
        tokenValues.put(">", 19);
        tokenValues.put(">=", 20);
        tokenValues.put("=", 21);
        tokenValues.put("<=", 22);
        tokenValues.put("<", 23);
        tokenValues.put(":=", 24);
        tokenValues.put(";", 25);
        tokenValues.put("space", 26);
        tokenValues.put("EOLN", 27);
        tokenValues.put("identifier", 28);
        tokenValues.put("number", 29);
        tokenValues.put("string", 30);
        tokenValues.put(".", 31);
    }

    private void getToken() {
        initializeIO();
        while (fileIn.hasNext()) {
            String nextLine = fileIn.nextLine();
            for (int i = 0; i <= nextLine.length(); i++) {
                if (i == nextLine.length()) {
                    stdOutput("EOLN");
                } else {
                    char thisChar = nextLine.charAt(i);
                    if (((i + 1) < nextLine.length()) && tokenValues.containsKey(String.valueOf(thisChar).concat(String.valueOf(nextLine.charAt(i + 1))))) {
                        stdOutput(String.valueOf(thisChar).concat(String.valueOf(nextLine.charAt(i + 1))));
                        i++;
                    } else if (tokenValues.containsKey(String.valueOf(thisChar))) {
                        stdOutput(String.valueOf(thisChar));
                    } else if (String.valueOf(thisChar).matches("(\\s)")) {
                        stdOutput("space");
                    } else if (thisChar == '"') {
                        String thisString = "\"";
                        do {
                            thisString = thisString.concat(String.valueOf(nextLine.charAt(++i)));
                        }
                        while (!String.valueOf(nextLine.charAt(i)).equals("\""));
                        stdOutput("string", thisString);
                    } else if (String.valueOf(thisChar).matches("[a-zA-Z]")) {
                        String thisString = "";
                        while (((i + 1) < nextLine.length()) && (String.valueOf(nextLine.charAt(1 + i)).matches("(\\w)"))) {
                            thisString = thisString.concat(String.valueOf(nextLine.charAt(i)));
                            i++;
                        }
                        thisString = thisString.concat(String.valueOf(nextLine.charAt(i)));
                        if (tokenValues.containsKey(thisString)) {
                            stdOutput(thisString);
                        } else {
                            stdOutput("identifier", thisString);
                        }
                    } else if (String.valueOf(thisChar).matches("(\\d)")) {
                        String thisString = "";
                        while (((i + 1) < nextLine.length())
                                && String.valueOf(nextLine.charAt(i)).matches("(\\d|[.])")) {
                            thisString = thisString.concat(String.valueOf(nextLine.charAt(i)));
                            i++;
                        }
                        i--;
                        stdOutput("number", thisString);
                    } else {
                        stdOutput("n/a", String.valueOf(thisChar));
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        GetToken token = new GetToken();
        token.getToken();
        token.outFile.close();
        System.exit(0);
    }
}
