package testStore;

import java.util.Scanner;
import java.text.ParseException;
import java.util.InputMismatchException;

public class TestInterfaceCli {
	private TestDatabase db;
	
	// terminal colour escape code definitions
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	private Scanner scanner;
	
	public TestInterfaceCli() {
		scanner = new Scanner(System.in);
	}
	
	/**
	 * Force a non-null string to be entered, prompting with prompt.
	 * 
	 * @param prompt	String to prompt every time.
	 * @return			Entered (non-null) string.
	 */
	private String getLine(String prompt) {
		boolean badInput = false;
		String line;
		
		do {
			badInput = false;
			
			System.out.print(prompt);
			line = scanner.nextLine();
			
			if (line.isEmpty()) {
				System.out.println("ERROR: nothing entered");
				badInput = true;
			}
		} while (badInput);
		
		return line;
	}
	
	/**
	 * Try to get a positive integer from stdin.
	 * 
	 * If an integer is not entered, return -2.
	 * If it is not positive, return -1.
	 * 
	 * @return	Input (either -2, -1 or the entered integer)
	 */
	private int getPositiveInt() {
		int num;
		
		try {
			num = new Scanner(System.in).nextInt();
		} catch (InputMismatchException e) {
			return -2;
		}
		
		if (num < 0) return -1; else return num;
	}
	
	/**
	 * Force a positive integer to be entered.
	 * 
	 * @param prompt	Prompt to show every time.
	 * @return			The (valid) integer entered.
	 */
	private int promptPositiveInt(String prompt) {
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
	
	/**
	 * Add num students, prompting for names & printing their ID.
	 * 
	 * @param num	The number of students to add.
	 */
	public void newStudents(int num) {
		
		for (int i = 0; i < num; i++) {
			// prompt for name
			String firstName = this.getLine("Student " + (i + 1) + "/" + num + " first name: ");
			String lastName = this.getLine("Student " + (i + 1) + "/" + num + " last name: ");
			
			int id = this.db.addStudent(lastName, firstName);
			
			System.out.println(firstName + " " + lastName + " added (ID " + id + ")");
		}
	}
	
	/**
	 * Prompt for info and use it to create a new test.
	 * 
	 * @return	The ID of the created test.
	 */
	public int newTest() {
		String name = this.getLine("Test name: ");
		String set = this.getLine("Class/set: ");
        String date = this.getLine("Date: ");

		int id = this.db.addTest(name, set, date);
		
		System.out.println(name + " added (ID " + id + ")");
		return id;
	}
	
	/**
	 * Prompt & set all students' results for test testId.
	 * 
	 * @param tId	Test to prompt for results.
	 */
	public void setResults(int tId) {
		
		int percent;
		boolean badInput;
		
		// iterate over all existing students
		for (Integer id: this.db.getAllStudentIds()) {
			do {
				badInput = false;
				
				// prompt for percentage
				percent = this.promptPositiveInt("Result for " + this.db.getStudentLastName(id) + ", " + this.db.getStudentFirstName(id) + " (ID " + id + ") (%): ");
				
				// setStudentTestResult checks for percentage
				try {
					this.db.setStudentResult(id, tId, percent);
				} catch (IndexOutOfBoundsException e) {
					System.out.println("ERROR: not a percentage (0-100)");
					badInput = true;
					continue;
				}
			} while (badInput); // keep trying if we had an error
		}
	}
	
	/**
	 * Print out part of the student test result database in a table.
	 * 
	 * @param sIds		Student IDs to use.
	 * @param tIds		Test IDs to use.
	 */
	public void printDatabase(Integer[] sIds, Integer[] tIds) {
		int longestName = 12;
		for (Integer sId : sIds)
			longestName = Math.max(longestName, this.db.getStudentLastName(sId).length());
		
		// header
		// ======
		String headerRow = ANSI_RED + "Student ID" + ANSI_RESET + " | "
			+ ANSI_GREEN + padString("Student name", longestName) + ANSI_RESET;
		
		for (Integer tId : tIds) {
			headerRow += " | ";
			String testName = this.db.getTestName(tId);
			int headerLength = Math.max(9, testName.length());
			headerRow += ANSI_CYAN + padString(testName, headerLength) + ANSI_RESET;
		}
		System.out.println(headerRow);
		
		String headerBorder = headerRow.replaceAll("\u001B\\[\\d\\d?m", "").replaceAll(".", "-");
		System.out.println(headerBorder);
		
		// table contents
		// ==============
		for (Integer sId : sIds) {
			String row = ANSI_RED + String.format("%010d", sId) + ANSI_RESET + " | ";
			row += ANSI_GREEN + padString(this.db.getStudentLastName(sId), longestName) + ANSI_RESET;
			for (Integer tId: tIds) {
				Integer result = this.db.getStudentResult(sId, tId);
				row += " | ";
				String gradeCell;
				if (result == -1) {
					gradeCell = padString(" N/A", 4);
				} else {
					gradeCell = padString(result.toString(), 3) + "%";
					gradeCell += " (" + padString(this.db.toGrade(result) + ")", 3);
				}
				int testCellLength = Math.max(this.db.getTestName(tId).length(), 9);
				row += ANSI_CYAN + padString(gradeCell, testCellLength) + ANSI_RESET;
			}
			System.out.println(row);
		}
	}
	
	private String padString(String string, int length) {
		char padChar = ' ';
		boolean rightAlign = false;
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

		int s1 = this.db.addStudent("Ben", "Orchard");
		int s2 = this.db.addStudent("Sharlo", "osu!");
		int s3 = this.db.addStudent("Raehik", "Lerna");
		int s4 = this.db.addStudent("Charlie", "Orchard");
		int s5 = this.db.addStudent("Ben", "Raehik");
		
		System.out.println(this.db.getMatchingStudentIds(new String[] {"Ben", null})[0] + ", " + this.db.getMatchingStudentIds(new String[] {null, "Orchard"})[1]);

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
		this.db.setStudentResult(s5, t2, 38);
		
		this.printDatabase(this.db.getAllStudentIds(), this.getAllTestIds());
		this.printDatabase(new Integer[] {0, 1}, this.getTestIdsInRange("16/09/14", "22/09/14"));
	}
	
	public static void main(String[] args) {
		TestInterfaceCli test = new TestInterfaceCli();
		test.runTest();
	}
}
