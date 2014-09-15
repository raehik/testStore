package testStore;

import java.util.HashMap;

public class TestDatabase {
	HashMap<Integer, HashMap<Integer, Integer>> studentResults;
	HashMap<Integer, String> studentNames;
	HashMap<Integer, String> testNames;
	
	int studentCount = 0, testCount = 0;
	
	public TestDatabase() {
		this.studentResults = new HashMap<Integer, HashMap<Integer, Integer>>();
		this.studentNames = new HashMap<Integer, String>();
		this.testNames = new HashMap<Integer, String>();
	}
	
	public void addStudent(String name) {
		this.studentResults.put(this.studentCount, new HashMap<Integer, Integer>());
		for (int testName: testNames.keySet()) {
			//HashMap<Integer, Integer> studentResult = studentResults.get(this.studentCount);
			// loop through each test identifier
			// add a score of -1 for that test for new student
		}
		this.studentNames.put(this.studentCount, name);
		this.studentCount++;
	}
}
