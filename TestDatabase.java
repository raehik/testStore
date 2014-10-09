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

	public int addStudent(String firstName, String lastName) {
		int id = this.nextStudentId;
		this.students.put(id, new Student(firstName, lastName));
		this.nextStudentId++;
		return id;
	}

	public String getStudentFirstName(int id) {
		return this.students.get(id).firstName();
	}
	
	public String getStudentLastName(int id) {
		return this.students.get(id).lastName();
	}
	
	public Integer[] getMatchingStudentIds(String[] name) {
		List<Integer> matches = new ArrayList<Integer>();
		
		for (int id : this.students.keySet()) {
			System.out.println("testing " + this.students.get(id).firstName() + " " + this.students.get(id).lastName());
			System.out.println("vs      " + name[0] + " " + name[1]);
			boolean firstCorrect = false, lastCorrect = false;
			
			// don't check a name if it is empty
			if (name[0] == null || name[0].equals(this.students.get(id).firstName())) {
				System.out.println("first match");
				firstCorrect = true;
			}
			
			if (name[1] == null || name[1].equals(this.students.get(id).lastName())) {
				System.out.println("last match");
				lastCorrect = true;
			}
			
			if (lastCorrect && firstCorrect) {
				matches.add(id);
			}
		}
		
		return matches.toArray(new Integer[matches.size()]);
	}

	public int getStudentResult(int sId, int tId) {
		return this.students.get(sId).getTestResult(tId);
	}

	public void setStudentResult(int sId, int tId, int percent)
		throws IndexOutOfBoundsException {
		// Set Student `sId`'s result for Test `tId` to `percent` if valid
		if (percent >= 0 && percent <= 100) {
			this.students.get(sId).setTestResult(tId, percent);
		} else {
			throw new IndexOutOfBoundsException("Percentage out of bounds");
		}
	}

	public void removeStudentResult(int sId, int tId) {
		this.students.get(sId).removeTestResult(tId);
	}

	public Integer[] getAllStudentIds() {
		return this.students.keySet().toArray(new Integer[this.students.size()]);
	}

	public void removeStudent(int sId) {
		this.students.remove(sId);
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
	
	public Integer[] getMatchingTestIds(String name) {
		List<Integer> matches = new ArrayList<Integer>();
		
		for (int id : this.tests.keySet()) {
			if (name.equals(this.tests.get(id).getName())) {
				matches.add(id);
			}
		}
		
		return matches.toArray(new Integer[matches.size()]);
	}

	public String getTestSet(int id) {
		return this.tests.get(id).getSet();
	}

	public Integer[] getAllTestIds() {
		return this.tests.keySet().toArray(new Integer[this.tests.size()]);
	}

	public Integer[] getTestIdsInRange(String date1, String date2) throws ParseException {
		List<Integer> validTests = new ArrayList<Integer>();
		
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

	public void removeTest(int tId) {
		// Removes test tId and all associated students' results.
		for (Integer id : this.getAllStudentIds()) {
			this.removeStudentResult(id, tId);
		}
		this.tests.remove(tId);
	}

	public String toGrade(int percent) {
		// fixed table of percent -> grade
		if (percent >= 90) { return "A*"; }
		else if (percent >= 80) { return "A"; }
		else if (percent >= 70) { return "B"; }
		else if (percent >= 60) { return "C"; }
		else if (percent >= 50) { return "D"; }
		else if (percent >= 40) { return "E"; }
        // TODO: throw an exception for this!!
		else if (percent == -1) { return "-"; }
		else { return "U"; } // below 40%
	}
}
