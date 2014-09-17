package testStore;

import java.util.HashMap;

public class Student {
	private String name;
	
	// results are stored as a hashmap of test identifier -> percentage
	// e.g. 1 -> 56
	// if student did not take a test, no identifier will exist for it.
	private HashMap<Integer, Integer> results;
	
	// initialise new Student with name as argument.
	public Student(String name) {
		// remove trailing whitespace from name
		this.name = name;
		results = new HashMap<Integer, Integer>();
	}
	
	// get the name of the student.
	public String getName() {
		return this.name;
	}
	
	// get result of the student for a specified test by testID.
	public int getResultOf(int testID) {
		try {
			return this.results.get(testID);
		} catch (NullPointerException e) {
			return -1;
		}
	}
	
	// set the result of student for specified test by testID, to the specified percentage 0 ≤ x ≤ 100
	public void setResult(int testID, int percent) {
		// return 0 if percent out-of-range
		// return -1 if testID does not exist
		this.results.put(testID, percent);
	}
}
