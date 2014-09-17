package testStore;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TestInterfaceCli {
	public TestDatabase db;
	
	public void setResultsFor(int testId) {
		int percent;
		boolean inRange;
		
		// iterate over all existing students
		for (Integer id: this.db.getAllStudentIds()) {
			do {
				inRange = true;
				// prompt for percentage
				System.out.println("Result for " + this.db.getStudentName(id) + " (ID " + id + ") (%): ");
				try {
					percent = new Scanner(System.in).nextInt();
				} catch (InputMismatchException e) {
					// if it wasn't an integer
					System.out.println("ERROR: not an integer");
					inRange = false;
					continue;
				}
				
				// try to set result with percent input
				int ret = this.db.setResultOfStudent(id, testId, percent);
				if (ret == 1) {
					System.out.println("ERROR: not a percentage (0-100)");
					inRange = false;
					continue;
				}
			} while (! inRange); // keep trying if we had an error
		}
	}
	
	public void bulkAddStudents(int num) {
		// Add `num` students, prompting for names & printing their ID.
		String name;
		int id;
		
		for (int i = 0; i < num; i++) {
			// prompt for name
			System.out.println("Student " + (i + 1) + "/" + num + " name: ");
			
			// TODO: make sure they enter something
			//       (check for `\n`s etc?)
			name = new Scanner(System.in).nextLine();
			id = this.db.addStudent(name);
			
			System.out.println(name + " added (ID " + id + ")");
		}
		name = null;
	}
	
	public void printDatabase() {
		int result;
		
		int longestName = 12;
		for (Integer id: this.db.getAllStudentIds()) {
			longestName = Math.max(longestName, this.db.getStudentName(id).length());
		}
		String namePadding = "";
		for (; namePadding.length() < longestName; namePadding+= " ");
		
		// print header
		String headerRow = "Student ID | ";
		headerRow += ("Student name" + namePadding).substring(0, longestName);
		
		for (Integer id: db.getAllTestIds()) {
			headerRow += " | ";
			String testName = this.db.getTestName(id);
			int headerLength = Math.max(9, testName.length());
			headerRow += (testName + "        ").substring(0, headerLength);
		}
		System.out.println(headerRow);
		
		String headerBorder = "";
		for (; headerBorder.length() < headerRow.length(); headerBorder+= "¯");
		System.out.println(headerBorder);
		
		// print table contents
		for (Integer id: this.db.getAllStudentIds()) {
			String row = String.format("%010d", id) + " | ";
			row += (this.db.getStudentName(id) + namePadding).substring(0,longestName);
			for (Integer tId: db.getAllTestIds()) {
				result = this.db.getResultOfStudent(id, tId);
				row += " | ";
				String gradeCell = ("  " + result).substring(("" + result).length()-1) + "%";
				gradeCell += " (" + (this.db.toGrade(result) + ") ").substring(0, 3);
				String testNamePadding = "";
				for (;
					testNamePadding.length() < this.db.getTestName(tId).length();
					testNamePadding+= " "
				);
				row += (gradeCell + testNamePadding).substring(0, this.db.getTestName(tId).length());
			}
			System.out.println(row);
		}
	}
	
	public void printTestResult(int studentID, int testID) {
		String name = this.db.getStudentName(studentID);
		String test = this.db.getTestName(testID);
		int result = this.db.getResultOfStudent(studentID, testID);
		String grade = this.db.toGrade(result);
		System.out.println(name + " scored " + result + "% (" + grade + ") on test \"" + test + "\"");
	}
	
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
	
	public void runTest() {
		// initialise a database for holding student & result data
		this.db = new TestDatabase();
		
		this.bulkAddStudents(5);
		int test1 = this.db.addTest("Computer Science 1", "true", 2, 2, 2014);
		this.setResultsFor(test1);
		this.printDatabase();
	}
	
	public static void main(String[] args) {
		TestInterfaceCli test = new TestInterfaceCli();
		test.runTest();
	}
}
