package testStore;

import java.util.HashMap;

public class TestDatabase {
	//		student ID, student
	private HashMap<Integer, Student> students;
	//		test ID, name
	public HashMap<Integer, String> tests;
	
	public int numOfStudents = 0, numOfTests = 0;
	
	public TestDatabase() {
		this.students = new HashMap<Integer, Student>();
		this.tests = new HashMap<Integer, String>();
	}
	
	public int addStudent(String name) {
		int id = this.numOfStudents;
		this.students.put(id, new Student(name));
		this.numOfStudents++;
		return id;
	}
	
	public int addTest(String name) {
		int id = this.numOfTests;
		this.tests.put(id, name);
		this.numOfTests++;
		return id;
	}
	
	public String getStudentName(int id) {
		return this.students.get(id).getName();
	}
	
	public Integer[] getAllStudentIDs() {
		return this.students.keySet().toArray(new Integer[this.students.size()]);
	}
	
	public String getTestName(int id) {
		return this.tests.get(id);
	}
	
	public void getResults(int studentID) {
		System.out.println("implemented here, or in TestInterface? probably latter~");
	}
	
	public String toGrade(int percent) {
		if (percent >= 90) { return "A*"; }
		else if (percent >= 80) { return "A"; }
		else if (percent >= 70) { return "B"; }
		else if (percent >= 60) { return "C"; }
		else if (percent >= 50) { return "D"; }
		else if (percent >= 40) { return "E"; }
		else if (percent == -1) { return "-"; }
		else { return "U"; } // below 40%
	}
	
	public int getResultOfStudent(int studentID, int testID) {
		try {
			return this.students.get(studentID).getResultOf(testID);
		} catch (NullPointerException e) {
			return -2;
		}
	}
	
	public void setResultOfStudent(int studentID, int testID, int percent) {
		this.students.get(studentID).setResult(testID, percent);
	}
	
	public void printResultOf(int studentID, int testID) {
		int result = this.getResultOfStudent(studentID, testID);
		String grade = this.toGrade(result);
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
	
	public int IdOfTest(String name) {
		for (int i = 0; i < this.tests.size(); i++) {
			if (name.equals(this.tests.get(i))) {
				return i;
			}
		}
		return -1;
	}
}
