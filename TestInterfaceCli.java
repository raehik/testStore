package testStore;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TestInterfaceCli {
	public TestDatabase db;
	
	public void printDatabase() {
		int result;
		
		// print header
		System.out.print("Student ID | Student name");
		for (Integer id: db.getAllTestIds()) {
			System.out.print(" | " + this.db.getTestName(id) + " result (%)");
		}
		System.out.println();
		
		int longestName = 0;
		for (Integer id: this.db.getAllStudentIds()) {
			longestName = Math.max(longestName, this.db.getStudentName(id).length());
		}
		String namePadding = "";
		for (int i = 0; i<longestName; i++) namePadding+= " ";
		
		// print table contents
		for (Integer id: this.db.getAllStudentIds()) {
			System.out.print(id + " | " + (this.db.getStudentName(id) + namePadding).substring(0,longestName));
			for (Integer tId: db.getAllTestIds()) {
				result = this.db.getResultOfStudent(id, tId);
				String cell = " | ";
				cell += ("  " + result).substring(("  " + result).length()-3) + "%";
				cell += " (" + (this.db.toGrade(result) + " ").substring(0, 2) + ")";
				System.out.print(cell);
			}
			System.out.println();
		}
	}
	
	public void printTestResult(int studentID, int testID) {
		String name = this.db.getStudentName(studentID);
		String test = this.db.getTestName(testID);
		int result = this.db.getResultOfStudent(studentID, testID);
		String grade = this.db.toGrade(result);
		System.out.println(name + " scored " + result + "% (" + grade + ") on test \"" + test + "\"");
	}
	
	public void bulkAddStudents(int num) {
		String name;
		int id;
		
		for (int i = 0; i < num; i++) {
			// prompt for name
			System.out.println("Student " + (i + 1) + "/" + num + " name: ");
			
			// TODO: make sure they enter something
			name = new Scanner(System.in).nextLine();
			id = this.db.addStudent(name);
			
			System.out.println(name + " added (ID " + id + ")");
		}
		name = null;
		//word = scanner.next();
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
	
	public void setResultsFor(int testID) {
		int percent;
		for (int i = 0; i < this.db.numOfStudents; i++) {
			// prompt for percentage
			System.out.println("Result for " + this.db.getStudentName(i) + " (ID " + i + ") (%): ");
			try {
				percent = new Scanner(System.in).nextInt();
			} catch (InputMismatchException e) {
				percent = 0;
			} finally {}
			this.db.setResultOfStudent(i, testID, percent);
		}
	}
	
	public void addSet(String testName) {
		int testID = this.db.addTest(testName, "true", 2, 2, 2014);
		System.out.println(testID);
		for (Integer id: this.db.getAllStudentIds()) {
			this.db.setResultOfStudent(id, testID, 10);
		}
	}
	
	public void runTest() {
		// initialise a database for holding student & result data
		this.db = new TestDatabase();
		
		// add student
		//int student1 = this.db.addStudent("Ben");
		
		// add test
		//int test1 = this.db.addTest("English 1");

		// result for test student doesn't have result for
		//this.printTestResult(student1, test1);
		
		//this.db.printResultOf(student1, 0);
		//p(this.db.IdOfStudent("Ben"));
		
		//this.addSet("Test test");
		//this.db.printResultOf(2, 0);
		//p(this.db.students.get(student1).getResult(2));
		
		//p(this.db.getResults(student1));
		
		this.bulkAddStudents(5);
		int test1 = this.db.addTest("Computer Science 1", "true", 2, 2, 2014);
		this.setResultsFor(test1);
		this.printDatabase();
	}
	
	public static void main(String[] args) {
		TestInterfaceCli test = new TestInterfaceCli();
		test.runTest();
	}
	
	// System.out.println takes sooooooo long to type
	public static void p(String s) {
		System.out.println(s);
	}
	
	public static void p(int s) {
		System.out.println(s);
	}
}
