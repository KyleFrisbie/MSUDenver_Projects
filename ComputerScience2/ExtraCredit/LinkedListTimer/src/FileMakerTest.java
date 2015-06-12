import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Kyle on 10/19/2014.
 */
public class FileMakerTest {
    private String outputFileName = "test.txt";
    private PrintWriter outputFile;

    FileMakerTest() {
        FileWriter outputDataFile = null;
        try {
            outputDataFile = new FileWriter(outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputFile = new PrintWriter(outputDataFile);
    }

    public static void main(String[] args) {
        FileMakerTest fmt = new FileMakerTest();
        fmt.outputFile.close();
        System.exit(0);
    }
}
