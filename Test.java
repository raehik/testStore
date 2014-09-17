package testStore;

public class Test {
	private String name, set;
	// date variables for when the test was set
	private int day, month, year;
	
	public Test(String name, String set, int day, int month, int year) {
		// remove trailing whitespace from name!
		this.name = name;
		this.set = set;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSet() {
		return this.set;
	}
	
	// get date of test (when it was set)
	public String getDate() {
		return this.day + "/" + this.month + "/" + this.year;
	}
}
