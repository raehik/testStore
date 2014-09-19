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
	
	private String getLine(String prompt) {
		// TODO: make sure they enter something
		//       (check for `\n`s etc?)
		System.out.print(prompt);
		return new Scanner(System.in).nextLine();
	}
	
	private int getPositiveInt() {
		// Try to get a positive integer from stdin.
		//
		// If an integer is not entered, return -2.
		// If it is not positive, return -1.
		//
		
		int num;
		
		try {
			num = new Scanner(System.in).nextInt();
		} catch (InputMismatchException e) {
			return -2;
		}
		
		if (num < 0) { return -1; } else { return num; }
	}
	
	private int promptPositiveInt(String prompt) {
		// Force a positive integer to be entered.
		//
		// Prompts using string `prompt`.
		// (keeps prompting until we get valid input)
		//
		
		boolean badInput;
		int num;
		
		do {
			badInput = false;
			
			System.out.print(prompt);
			num = this.getPositiveInt();
			
			if (num == -2) {
				System.out.println("ERROR: not an integer");
				badInput = true;
			} else if (num == -1) {
				System.out.println("ERROR: not a positive integer");
				badInput = true;
			}
		} while (badInput);
		
		return num;
	}
	
	public void newStudents(int num) {
		// Add `num` students, prompting for names & printing their ID.
		
		for (int i = 0; i < num; i++) {
			// prompt for name
			String name = this.getLine("Student " + (i + 1) + "/" + num + " name: ");
			
			int id = this.db.addStudent(name);
			
			System.out.println(name + " added (ID " + id + ")");
		}
	}
	
	public int newTest() {
		// Prompt for info to use to create a new test with, then do that.
		
		// get lotsa info
		String name = this.getLine("Test name: ");
		String set = this.getLine("Class/set: ");

		int day = this.promptPositiveInt("Day set: ");
		int month = this.promptPositiveInt("Month set: ");
		int year = this.promptPositiveInt("Year set: ");
		
		int id = this.db.addTest(name, set, day, month, year);
		
		System.out.println(name + " added (ID " + id + ")");
		
		return id;
	}
	
	public void setResultsForTest(int testId) {
		// Set all students' results for test `testId`.
		
		int percent;
		boolean badInput;
		
		// iterate over all existing students
		for (Integer id: this.db.getAllStudentIds()) {
			do {
				badInput = false;
				
				// prompt for percentage
				percent = this.promptPositiveInt("Result for " + this.db.getStudentName(id) + " (ID " + id + ") (%): ");
				
				// setResultOfStudent checks for percentage
				try {
					this.db.setResultOfStudent(id, testId, percent);
				} catch (IndexOutOfBoundsException e) {
					System.out.println("ERROR: not a percentage (0-100)");
					badInput = true;
					continue;
				}
			} while (badInput); // keep trying if we had an error
		}
	}
	
	public void removeStudent(int studentId) {
		this.db.removeStudent(studentId);
	}
	
	public void removeTest(int testId) {
		this.db.removeTest(testId);
	}
	
	public void printDatabase() {
		// TODO: we gotta refactor this big time!
		// Print the entire student-test-result database in a table format.
		
		int longestName = 12;
		for (Integer id: this.db.getAllStudentIds())
			longestName = Math.max(longestName, this.db.getStudentName(id).length());
		
		// print header
		String headerRow = ANSI_RED + "Student ID" + ANSI_RESET + " | "
			+ ANSI_GREEN + padString("Student name", longestName) + ANSI_RESET;
		
		for (Integer tId: db.getAllTestIds()) {
			headerRow += " | ";
			String testName = this.db.getTestName(tId);
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
				row += " | ";
				String gradeCell;
				int result = this.db.getResultOfStudent(id, tId);
				if (result == -1) {
					gradeCell = padString(" N/A", 4);
				} else {
					gradeCell = padString(result, 3, ' ', true) + "%";
					gradeCell += " (" + padString(this.db.toGrade(result) + ")", 3);
				}
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

		this.newStudents(2);
		int test1 = this.newTest();
		this.setResultsForTest(test1);
		this.printDatabase();
		
		this.removeStudent(1);
		this.newStudents(1);
		int test2 = this.newTest();
		this.setResultsForTest(test2);
		this.printDatabase();
		
		this.removeTest(test1);
		this.printDatabase();
	}
	
	public static void main(String[] args) {
		TestInterfaceCli test = new TestInterfaceCli();
		test.runTest();
	}
}
