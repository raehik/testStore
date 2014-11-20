package testStore;

import java.util.HashMap;

public class Student {
	private String firstName, lastName;
	
	// stored as a hashmap of test identifier -> result
	// e.g. 1 -> 56
	// if student did not take a test, no identifier will exist for it.
	// results can be any integer, not just a percentage between 0 and
	// 100 -- it is up to the class using Student to define any such
	// restrictions
	private HashMap<Integer, Integer> results;
	
	public Student(String firstName, String lastName) {
		// trim leading/trailing whitespace from strings
		this.firstName = firstName.trim();
		this.lastName = lastName.trim();
		this.results = new HashMap<Integer, Integer>();
	}
	
	public String firstName() { return this.firstName; }
	public String lastName() { return this.lastName; }
	
	/**
	 * Return the result of test testId for a student.
	 * 
	 * Returns -1 if the student did not take the test testId.
	 * 
	 * @param testId	The ID of the test to use.
	 * @return			The result associated with testId.
	 */
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
