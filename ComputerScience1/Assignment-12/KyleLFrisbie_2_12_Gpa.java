/* Assignment #12 Gpa class
 * 	Kyle L Frisbie
 * 	Class file which KyleLFrisbie_2_12 calls all methods from.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class KyleLFrisbie_2_12_Gpa {
	
	private static Toolkit_General tools = new Toolkit_General();

	private Scanner inputFile;				// read values from input file
	private PrintWriter outputFile;				// write values to output file
	private ArrayList<String> name =			// name of students from input file
				new ArrayList<String>();
	private ArrayList<Double> gradePA =			// values of students GPA's from
				new ArrayList<Double>();	//	input file
	private ArrayList<Integer> creditHours=			// number of students credit hours
				new ArrayList<Integer>();
	private int nStudents = 0;				// number of students processed
								//	from input file
	private double avgGPA;					// average of all GPA's from input
								//	file
	private int tHours = 0;					// total number of hours passed
	private int tPointHours = 0;				// total number of weighted hours
								//	passed (grade point * hours)
	private final String SPLIT_CHAR1 =			// delimiter to split string (name
			"#";					// 	from credit hours and grades)
	
/*****************************************************************************/
	// Constructor to create input file scanner and output file print writer
	public KyleLFrisbie_2_12_Gpa(String inFile, String outFile)
						throws IOException {
		File inputDataFile = new File (inFile);
		inputFile = new Scanner(inputDataFile);
		outputFile = new PrintWriter(outFile);
		System.out.println("Reading file " + inFile + "\r\n"
				+ "Creating file " + outFile + "\n");
	}

/*****************************************************************************/
	// Read input file, set values in arrays, calculate total hours, number of
	// 	students, individual student GPA, and average GPA
	public void setValues() {
		while (inputFile.hasNext()) {
			String nextLine = inputFile.nextLine().trim();
			outputFile.println(nextLine);
			String lineTokens[] = nextLine.split(SPLIT_CHAR1);
			name.add(lineTokens[0].trim());
			double studentAverage = 0;	// average GPA for each student
			int studentPoints = 0;		// weighted hours for each student
							//	(hours * hourPoints)
			int studentHours = 0;		// number of valid credit hours
			int hourPoints;			// weighted point value for each
							//	credit hour
			Scanner gpaScan = new Scanner(lineTokens[1]);
				while (gpaScan.hasNext()) {
					int hours = gpaScan.nextInt();
					outputFile.print(hours + ", ");
					String grade = gpaScan.next().toLowerCase();
					outputFile.println(grade);
					switch (grade) {
						case "a":
							hourPoints = 4;
							break;
						case "b":
							hourPoints = 3;
							break;
						case "c":
							hourPoints = 2;
							break;
						case "d":
							hourPoints = 1;
							break;
						case "f":
							hourPoints = 0;
							break;
						default:
							hourPoints = -1;
							System.out.println("Check your input file, there "
									+ "appears to be an invalid grade at "
									+ "student: " + (nStudents + 1));
					}
					/*Map<String, Integer> letterMap = new HashMap<String, Integer>();
						letterMap.put("a", new Integer(4));
						letterMap.put("b", new Integer(3));
						letterMap.put("c", new Integer(2));
						letterMap.put("d", new Integer(1));
						letterMap.put("f", new Integer(0));
						Integer mapGrade = letterMap.get(grade);
                        			hourPoints =
                        				mapGrade == null ? -1 : mapGrade.intValue();
                        		*/

					if (hourPoints != -1) {
						studentPoints += hourPoints * hours;
						studentHours += hours;
						tPointHours += hourPoints * hours;
						tHours += hours;
					}
				}
			if (studentHours != 0) {
				studentAverage = ((double)studentPoints / studentHours);
			} else {
				studentAverage = 0;
			}
			gradePA.add(nStudents, studentAverage);
			creditHours.add(studentHours);
			nStudents++;
		}
		avgGPA = (double)tPointHours / tHours;
	}

/*****************************************************************************/
	// Set Table header and table values
	public void setTable() {
		outputFile.print("\n\n"
				+ tools.padString("Student Name", 20, "", " "));
		outputFile.print(tools.padString("GPA", 7, " ", ""));
		outputFile.println(tools.padString("Hours Passed", 15, " ", ""));
		
		for (int i = 0; i < nStudents ; i++) {
			outputFile.print(tools.padString(name.get(i) , 20, "", " "));
			outputFile.print(tools.leftPad(gradePA.get(i), 7, "0.00", " "));
			outputFile.println(
				tools.leftPad(creditHours.get(i), 15, "#0", " "));
		}
	}

/*****************************************************************************/
	// Set table summary with total number of students, average GPA, and total
	// 	hours passed
	public void setSummary() {
		outputFile.println("\n\n");
		outputFile.println("The number of students processed from the input"
					+ " file are:" + nStudents);
		outputFile.println("The average of all student's GPA's is: "
					+ tools.leftPad(avgGPA, 4, "#0.00", " "));
		outputFile.println("The total number of hours passed is: " + tHours);
	}

/*****************************************************************************/
	// End the program by closing input and output files and exiting program
	public void endProgram() {
		inputFile.close();
		outputFile.close();
		System.exit(0);
	}
}
