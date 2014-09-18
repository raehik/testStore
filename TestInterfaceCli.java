package testStore;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TestInterfaceCli {
	public TestDatabase db;
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public void newStudents(int num) {
		// Add `num` students, prompting for names & printing their ID.
		
		for (int i = 0; i < num; i++) {
			// prompt for name
			System.out.print("Student " + (i + 1) + "/" + num + " name: ");
			
			// TODO: make sure they enter something
			//       (check for `\n`s etc?)
			String name = new Scanner(System.in).nextLine();
			int id = this.db.addStudent(name);
			
			System.out.println(name + " added (ID " + id + ")");
		}
	}
	
	public int newTest() {
		// Prompt for info to use to create a new test with, then do that.
		
		// get lotsa info
		System.out.print("Test name: ");
		String name = new Scanner(System.in).nextLine();
		
		System.out.print("Class/set: ");
		String set = new Scanner(System.in).nextLine();
		
		System.out.print("Day set: ");
		int day = new Scanner(System.in).nextInt();
		
		System.out.print("Month set: ");
		int month = new Scanner(System.in).nextInt();
		
		System.out.print("Year set: ");
		int year = new Scanner(System.in).nextInt();
		
		int id = this.db.addTest(name, set, day, month, year);
		
		System.out.println(name + " added (ID " + id + ")");
		
		return id;
	}
	
	public void setResultsForTest(int testId) {
		// Set all students' results for test `testId`.
		
		int percent;
		boolean inRange;
		
		// iterate over all existing students
		for (Integer id: this.db.getAllStudentIds()) {
			do {
				inRange = true;
				
				// prompt for percentage
				System.out.print("Result for " + this.db.getStudentName(id) + " (ID " + id + ") (%): ");
				try {
					percent = new Scanner(System.in).nextInt();
				} catch (InputMismatchException e) {
					// if it wasn't an integer, try again
					System.out.println("ERROR: not an integer");
					inRange = false;
					continue;
				}
				
				// try to set result with percent input
				// setResultOfStudent does its own input checking
				int ret = this.db.setResultOfStudent(id, testId, percent);
				if (ret == 1) {
					System.out.println("ERROR: not a percentage (0-100)");
					inRange = false;
					continue;
				}
			} while (! inRange); // keep trying if we had an error
		}
	}
	
	public void printDatabase() {
		// UGLY! we gotta refactor this big time!
		
		// Print the entire student-test-result database in a table format.
		
		int result;
		
		int longestName = 12;
		for (Integer id: this.db.getAllStudentIds()) {
			longestName = Math.max(longestName, this.db.getStudentName(id).length());
		}
		
		// print header
		String headerRow = ANSI_RED + "Student ID" + ANSI_RESET + " | ";
		headerRow += ANSI_GREEN + padString("Student name", longestName - 1) + ANSI_RESET;
		
		for (Integer id: db.getAllTestIds()) {
			headerRow += " | ";
			String testName = this.db.getTestName(id);
			int headerLength = Math.max(9, testName.length());
			headerRow += ANSI_CYAN + padString(testName, headerLength) + ANSI_RESET;
		}
		System.out.println(headerRow);
		
		String headerBorder = headerRow.replaceAll("\u001B\\[\\d\\d?m", "").replaceAll(".", "-");
		System.out.println(headerBorder);
		
		// print table contents
		for (Integer id: this.db.getAllStudentIds()) {
			String row = ANSI_RED + String.format("%010d", id) + ANSI_RESET + " | ";
			row += ANSI_GREEN + padString(this.db.getStudentName(id), longestName) + ANSI_RESET;
			for (Integer tId: db.getAllTestIds()) {
				result = this.db.getResultOfStudent(id, tId);
				row += " | ";
				String gradeCell = padString(result, 3, ' ', true) + "%";
				gradeCell += " (" + padString(this.db.toGrade(result) + ")", 3);
				int testCellLength = Math.max(this.db.getTestName(tId).length(), 9);
				row += ANSI_CYAN + padString(gradeCell, testCellLength) + ANSI_RESET;
			}
			System.out.println(row);
		}
	}
	
	/*
	public void printTestResult(int studentID, int testID) {
		String name = this.db.getStudentName(studentID);
		String test = this.db.getTestName(testID);
		int result = this.db.getResultOfStudent(studentID, testID);
		String grade = this.db.toGrade(result);
		System.out.println(name + " scored " + result + "% (" + grade + ") on test \"" + test + "\"");
	}
	*/
	
	/*
	public int setTest(String testName) {
		String test;
		int id = -1;
		for (int i = 0; i < this.db.numOfStudents; i++) {
			// prompt for name
			System.out.println("Test name: ");
			
			// TODO: make sure they enter something
			test = new Scanner(System.in).nextLine();
			id = this.db.addTest(test);
			
			System.out.println(test + " added (ID " + id + ")");
		}
		return id;
	}
	*/
	
	private String padString (String string, int length) {
		char padChar = ' ';
		boolean rightAlign = false;
		return padString (string, length, padChar, rightAlign);
	}
	
	private String padString (Integer intString, int length) {
		char padChar = ' ';
		boolean rightAlign = false;
		String string = intString.toString();
		return padString (string, length, padChar, rightAlign);
	}
	
	private String padString (String string, int length, char padChar) {
		boolean rightAlign = false;
		return padString (string, length, padChar, rightAlign);
	}
	
	private String padString (Integer intString, int length, char padChar) {
		boolean rightAlign = false;
		String string = intString.toString();
		return padString (string, length, padChar, rightAlign);
	}
	private String padString (Integer intString, int length, char padChar, boolean rightAlign) {
		String string = intString.toString();
		return padString (string, length, padChar, rightAlign);
	}
	
	private String padString (String string, int length, char padChar, boolean rightAlign) {
		String paddingString = "";
		for (int i = 0; i<length; i++) paddingString += padChar;
		if (rightAlign == true) string = new StringBuilder(string).reverse().toString();
		string += paddingString;
		string = string.substring(0,length);
		if (rightAlign == true) string = new StringBuilder(string).reverse().toString();
		return string;
	}
	
	public void runTest() {
		// initialise a database for holding student & result data
		this.db = new TestDatabase();
		
		this.newStudents(5);
		int test1 = this.newTest();
		this.setResultsForTest(test1);
		this.printDatabase();
	}
	
	public static void main(String[] args) {
		TestInterfaceCli test = new TestInterfaceCli();
		test.runTest();
	}
}
