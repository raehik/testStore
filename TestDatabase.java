package testStore;

import java.util.HashMap;

public class TestDatabase {
	//		student ID, Student
	private HashMap<Integer, Student> students;
	//		test ID, name
	public HashMap<Integer, Test> tests;
	
	public int nextStudentId, nextTestId;
	
	public TestDatabase() {
		this.students = new HashMap<Integer, Student>();
		this.tests = new HashMap<Integer, Test>();
	}
	
	public int addStudent(String name) {
		int id = this.nextStudentId;
		this.students.put(id, new Student(name));
		this.nextStudentId++;
		return id;
	}
	
	public String getStudentName(int id) {
		return this.students.get(id).getName();
	}
	
	public int getStudentTestResult(int studentId, int testId) {
		return this.students.get(studentId).getTestResult(testId);
	}
	
	public void setStudentTestResult(int studentId, int testId, int percent)
		throws IndexOutOfBoundsException {
		// Set Student `studentId`'s result for Test `testId` to `percent`, if between -1 and 100
		if (percent >= 0 && percent <= 100) {
			this.students.get(studentId).setTestResult(testId, percent);
		} else {
			throw new IndexOutOfBoundsException("Percentage out of bounds");
		}
	}
	
	public void removeStudentTestResult(int studentId, int testId) {
		this.students.get(studentId).removeTestResult(testId);
	}
	
	public Integer[] getAllStudentIds() {
		return this.students.keySet().toArray(new Integer[this.students.size()]);
	}
	
	public int addTest(String name, String set, int day, int month, int year) {
		int id = this.nextTestId;
		this.tests.put(id, new Test(name, set, day, month, year));
		this.nextTestId++;
		return id;
	}

	public String getTestName(int id) {
		return this.tests.get(id).getName();
	}
	
	public String getTestSet(int id) {
		return this.tests.get(id).getSet();
	}
	
	public String getTestDate(int id) {
		return this.tests.get(id).getDate();
	}
	
	public Integer[] getAllTestIds() {
		return this.tests.keySet().toArray(new Integer[this.tests.size()]);
	}
	
	public void removeTest(int testId) {
		this.tests.remove(testId);
	}
	
	public String toGrade(int percent) {
		// fixed table of percent -> grade
		if (percent >= 90) { return "A*"; }
		else if (percent >= 80) { return "A"; }
		else if (percent >= 70) { return "B"; }
		else if (percent >= 60) { return "C"; }
		else if (percent >= 50) { return "D"; }
		else if (percent >= 40) { return "E"; }
		else if (percent == -1) { return "-"; }
		else { return "U"; } // below 40%
	}
	
	// TODO: these should return an *array* of IDs matching the name.
	/*
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
	*/
}
