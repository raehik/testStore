package testStore;

import java.util.HashMap;

public class Student {
	private String name;
	
	// results are stored as a hashmap of test identifier -> percentage
	// e.g. 1 -> 56
	// if student did not take a test, no identifier will exist for it.
	private HashMap<Integer, Integer> results;
	
	public Student(String name) {
		// remove trailing whitespace from name
		this.name = name;
		results = new HashMap<Integer, Integer>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getResultOf(int testID) {
		try {
			return this.results.get(testID);
		} catch (NullPointerException e) {
			return -1;
		}
	}
	
	public void setResult(int testID, int percent) {
		// return 0 if percent out-of-range
		// return -1 if testID does not exist
		this.results.put(testID, percent);
	}
}
