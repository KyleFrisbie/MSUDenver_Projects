/* Assignment #12
 * 	This program takes an input file with multiple lines containing names,
 * 	credit hours, and grades. All methods are stored in a separate class
 * 	file which holds most variables relating to program functionality.
 * 	Using ArrayList, the program will store the name of each student in an
 * 	array. After the name is stored the program will read the remaining
 * 	information on the line (credit hours and grades), and calculate the
 * 	student's total credit hours and GPA based off of these values and
 * 	store them in individual GPA and credit hour arrays. As the input file
 * 	is being processed, an echo will be printed to the output file. After
 * 	all calculations have been made, a table is printed to the output file
 * 	containing each student's name, GPA, and number of hours passed. At
 * 	the end of the table the total number of students, the average GPA,
 * 	and the total number of hours passed is also printed.
 * 
 * Kyle L Frisbie
 * Program #12, Section 2
 * Java Version: 1.8.0_05, Eclipse Luna: Release (4.4.0),
 * custom built PC, Windows 8.1
 */

import java.io.IOException;

public class KyleLFrisbie_2_12 {

	public static void main(String[] args) throws IOException {
		
		// Set input/output file locations
		final String INPUT_FILE = "KyleLFrisbie_2_12_Input.txt";
		final String OUTPUT_FILE = "KyleLFrisbie_2_12_Output.txt";
		
		// Call constructor to Gpa class file with input and output file names
		KyleLFrisbie_2_12_Gpa gPA = new KyleLFrisbie_2_12_Gpa(INPUT_FILE,
															  OUTPUT_FILE);
		
		gPA.setValues();
		
		gPA.setTable();
		
		gPA.setSummary();
		
		gPA.endProgram();
	}
}
