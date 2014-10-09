package testStore;

import java.util.HashMap;

public class Student {
	private String firstName;
	private String lastName;
	
	// results are stored as a hashmap of test identifier -> result
	// e.g. 1 -> 56
	// if student did not take a test, no identifier will exist for it.
	// results are not forced to be between 0 and 100 -- however such an
	// implementation may be very useful in any classes using this one.
	private HashMap<Integer, Integer> results;
	
	public Student(String firstName, String lastName) {
		this.firstName = firstName.trim();
		this.lastName = lastName.trim();
		this.results = new HashMap<Integer, Integer>();
	}
	
	public String firstName() {
		return this.firstName;
	}
	
	public String lastName() {
		return this.lastName;
	}
	
	public int getTestResult(int testId) {
		try {
			return this.results.get(testId);
		} catch (NullPointerException e) {
			// return -1 for non-existent tests (i.e. did not take)
			return -1;
		}
	}
	
	public void setTestResult(int testId, int percent) {
		// TODO: maybe 2 separate methods for this: newResult and
		//       changeResult?
		this.results.put(testId, percent);
	}
	
	public void removeTestResult(int testId) {
		this.results.remove(testId);
	}
}
