package testStore;

import java.util.Collection;
import java.util.HashMap;

public class Student {
	private String name;
	private int numOfTests;
	private HashMap<Integer, Integer> results;
	
	public Student(String name, int numOfTests) {
		this.name = name;
		this.numOfTests = numOfTests;
		results = new HashMap<Integer, Integer>();
		this.initialise();
	}
	
	private void initialise() {
		// fake/not very usefully implemented ID system atm
		for (int testID = 0; testID < this.numOfTests; testID++) {
			this.results.put(testID, -1);
		}
	}
	
	public void setResult(int testID, int percent) {
		this.results.put(testID, percent);
	}
	
	public void printResults() {
		System.out.println(this.name + ": " + this.results.toString());
	}
	
	public Collection<Integer> getResults() {
		return this.results.values();
	}
}
