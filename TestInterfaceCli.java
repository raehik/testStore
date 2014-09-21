package testStore;

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TestInterfaceCli {
	private TestDatabase db;
	
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
		boolean badInput = false;
		String line;
		
		do {
			badInput = false;
			
			System.out.print(prompt);
			line = new Scanner(System.in).nextLine();
			
			if (line.isEmpty()) {
				System.out.println("ERROR: nothing entered");
				badInput = true;
			}
		} while (badInput);
		
		return line;
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
        String date = this.getLine("Date: ");

		int id = this.db.addTest(name, set, date);
		
		System.out.println(name + " added (ID " + id + ")");
		
		return id;
	}
	
	public void setResults(int testId) {
		// Set all students' results for test `testId`.
		
		int percent;
		boolean badInput;
		
		// iterate over all existing students
		for (Integer id: this.db.getAllStudentIds()) {
			do {
				badInput = false;
				
				// prompt for percentage
				percent = this.promptPositiveInt("Result for " + this.db.getStudentName(id) + " (ID " + id + ") (%): ");
				
				// setStudentTestResult checks for percentage
				try {
					this.db.setStudentResult(id, testId, percent);
				} catch (IndexOutOfBoundsException e) {
					System.out.println("ERROR: not a percentage (0-100)");
					badInput = true;
					continue;
				}
			} while (badInput); // keep trying if we had an error
		}
	}
	
	public void printDatabase(Integer[] studentIds, Integer[] testIds) {
		// Print the entire student-test-result database in a table format.
		
		int longestName = 12;
		for (Integer sId : studentIds)
			longestName = Math.max(longestName, this.db.getStudentName(sId).length());
		
		// print header
		String headerRow = ANSI_RED + "Student ID" + ANSI_RESET + " | "
			+ ANSI_GREEN + padString("Student name", longestName) + ANSI_RESET;
		
		for (Integer tId : testIds) {
			headerRow += " | ";
			String testName = this.db.getTestName(tId);
			int headerLength = Math.max(9, testName.length());
			headerRow += ANSI_CYAN + padString(testName, headerLength) + ANSI_RESET;
		}
		System.out.println(headerRow);
		
		String headerBorder = headerRow.replaceAll("\u001B\\[\\d\\d?m", "").replaceAll(".", "-");
		System.out.println(headerBorder);
		
		// print table contents
		for (Integer sId : studentIds) {
			String row = ANSI_RED + String.format("%010d", sId) + ANSI_RESET + " | ";
			row += ANSI_GREEN + padString(this.db.getStudentName(sId), longestName) + ANSI_RESET;
			for (Integer testId: testIds) {
				int result = this.db.getStudentResult(sId, testId);
				row += " | ";
				String gradeCell;
				if (result == -1) {
					gradeCell = padString(" N/A", 4);
				} else {
					gradeCell = padString(result, 3, ' ', true) + "%";
					gradeCell += " (" + padString(this.db.toGrade(result) + ")", 3);
				}
				int testCellLength = Math.max(this.db.getTestName(testId).length(), 9);
				row += ANSI_CYAN + padString(gradeCell, testCellLength) + ANSI_RESET;
			}
			System.out.println(row);
		}
	}
	
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
	
	private Integer[] getAllTestIds() {
		return this.db.getAllTestIds();
	}
	
	private Integer[] getTestIdsInRange(String date1, String date2) {
		try {
			return this.db.getTestIdsInRange(date1, date2);
		} catch (ParseException e) { return null; }
	}
	
	public void runTest() {
		// initialise a database for holding student & result data
		this.db = new TestDatabase();

		int s1 = this.db.addStudent("Ben");
		int s2 = this.db.addStudent("Sharlo");
		int s3 = this.db.addStudent("Raehik");
		int s4 = this.db.addStudent("Charlie");

		int t1 = this.db.addTest("Computer Science 1", "Yr. 12 CS", "17/09/13");
		int t2 = this.db.addTest("Computer Science 2", "Yr. 12 CS", "17/09/15");
		int t3 = this.db.addTest("Computer Science 3", "Yr. 12 CS", "17/09/14");

		this.db.setStudentResult(s1, t1, 100);
		this.db.setStudentResult(s1, t2, 90);
		this.db.setStudentResult(s1, t3, 80);
		this.db.setStudentResult(s2, t1, 70);
		this.db.setStudentResult(s2, t2, 60);
		this.db.setStudentResult(s2, t3, 50);
		this.db.setStudentResult(s3, t1, 40);
		this.db.setStudentResult(s3, t2, 30);
		this.db.setStudentResult(s3, t3, 20);
		this.db.setStudentResult(s4, t1, 10);
		this.db.setStudentResult(s4, t2, 75);
		this.db.setStudentResult(s4, t3, 83);
		
		this.printDatabase(this.db.getAllStudentIds(), this.getAllTestIds());
		this.printDatabase(new Integer[] {0, 1}, this.getTestIdsInRange("16/09/14", "22/09/14"));
	}
	
	public static void main(String[] args) {
		TestInterfaceCli test = new TestInterfaceCli();
		test.runTest();
	}
}
