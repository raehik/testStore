package testStore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class TestDatabase {
	//		student ID, Student
	private HashMap<Integer, Student> students;
	//		test ID, name
	public HashMap<Integer, Test> tests;

	private int nextStudentId = 0, nextTestId = 0;

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

	public int getStudentResult(int studentId, int testId) {
		return this.students.get(studentId).getTestResult(testId);
	}

	public void setStudentResult(int studentId, int testId, int percent)
		throws IndexOutOfBoundsException {
		// Set Student `studentId`'s result for Test `testId` to `percent`, if between -1 and 100
		if (percent >= 0 && percent <= 100) {
			this.students.get(studentId).setTestResult(testId, percent);
		} else {
			throw new IndexOutOfBoundsException("Percentage out of bounds");
		}
	}

	public void removeStudentResult(int studentId, int testId) {
		this.students.get(studentId).removeTestResult(testId);
	}

	public Integer[] getAllStudentIds() {
		return this.students.keySet().toArray(new Integer[this.students.size()]);
	}

	public void removeStudent(int studentId) {
		this.students.remove(studentId);
	}

	public int addTest(String name, String set, String date) {
		int id = this.nextTestId;
		this.tests.put(id, new Test(name, set, date));
		this.nextTestId++;
		return id;
	}

	public String getTestName(int id) {
		return this.tests.get(id).getName();
	}

	public String getTestSet(int id) {
		return this.tests.get(id).getSet();
	}

	public Integer[] getAllTestIds() {
		return this.tests.keySet().toArray(new Integer[this.tests.size()]);
	}

	public Integer[] getTestIdsInRange(String date1, String date2) throws ParseException {
		List<Integer> validTests = new ArrayList<Integer>();
		
		// TODO: if actualDate2 < actualDate 1, be smart: swap the check
		Date fromDate = null;
		Date toDate = null;
		
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yy");
		
		if (simpleDate.parse(date1).after(simpleDate.parse(date2))) {
			fromDate = simpleDate.parse(date2);
			toDate = simpleDate.parse(date1);
		} else {
			fromDate = simpleDate.parse(date1);
			toDate = simpleDate.parse(date2);
		}
		
		for (Integer id : this.getAllTestIds()) {
			if (this.tests.get(id).date().compareTo(fromDate) >= 0 && this.tests.get(id).date().compareTo(toDate) <= 0) {
				validTests.add(id);
			}
		}
		
		return validTests.toArray(new Integer[validTests.size()]);
	}

	public void removeTest(int testId) {
		// Removes test testId and all associated students' results.
		for (Integer id : this.getAllStudentIds()) {
			this.removeStudentResult(id, testId);
		}
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
	
	public Integer[] studentId(String name) {
		List<Integer> matchingIds = new ArrayList<Integer>();
		for (Integer id : this.getAllStudentIds()) {
			if (name.equals(this.students.get(id).getName())) {
				matchingIds.add(id);
			}
		}
		return matchingIds.toArray(new Integer[matchingIds.size()]);
	}
	
	public Integer[] testId(String name) {
		List<Integer> matchingIds = new ArrayList<Integer>();
		for (Integer id : this.getAllTestIds()) {
			if (name.equals(this.tests.get(id).getName())) {
				matchingIds.add(id);
			}
		}
		return matchingIds.toArray(new Integer[matchingIds.size()]);
	}
}
