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
	
	public char toGrade(int percent) {
		if (percent >= 90) { return 'A'; }
		else if (percent >= 80) { return 'B'; }
		else if (percent >= 70) { return 'C'; }
		else if (percent >= 60) { return 'D'; }
		else if (percent >= 50) { return 'E'; }
		else if (percent >= 40) { return 'F'; }
		else if (percent >= 30) { return 'G'; }
		else if (percent == 0) { return '-'; }
		else { return 'U'; }
	}
	
	public int getResultOf(int studentID, int testID) {
		return this.students.get(studentID).getResult(testID);
	}
	
	public void printResultOf(int studentID, int testID) {
		int result = this.getResultOf(studentID, testID);
		char grade = this.toGrade(result);
		System.out.println(result + "% " + grade);
	}
	
	public int IdOfStudent(String name) {
		for (int i = 0; i < this.students.size(); i++) {
			if (name == this.students.get(i).getName()) {
				return i;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		TestDatabase db = new TestDatabase();
		db.addStudent("Ben");
		System.out.println(db.getResults(0));
		db.students.get(0).setResult(0, 95);
		int result = db.students.get(0).getResult(0);
		db.printResultOf(0, 0);
		System.out.println(db.IdOfStudent("Ben"));
	}
}
