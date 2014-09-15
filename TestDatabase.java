package testStore;

import java.util.Collection;
import java.util.HashMap;

public class TestDatabase {
	//		student ID, student
	HashMap<Integer, Student> students;
	//		test ID, name
	HashMap<Integer, String> tests;
	
	int numOfStudents = 0, numOfTests = 0;
	
	public TestDatabase() {
		this.students = new HashMap<Integer, Student>();
		this.tests = new HashMap<Integer, String>();
		
		this.tests.put(0, "English");
		this.tests.put(1, "Maths");
		this.numOfTests = 2;
	}
	
	public void addStudent(String name) {
		//Student student = new Student(name, this.numOfTests);
		this.students.put(this.numOfStudents, new Student(name, this.numOfTests));
		this.numOfStudents++;
	}
	
	public void printResults(int studentID) {
		this.students.get(studentID).printResults();
	}
	
	public Collection<Integer> getResults(int studentID) {
		return this.students.get(studentID).getResults();
	}
	
	public static void main(String[] args) {
		TestDatabase db = new TestDatabase();
		db.addStudent("Ben");
		//db.printResults(0);
		System.out.println(db.getResults(0));
	}
}
