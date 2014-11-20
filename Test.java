package testStore;

import java.util.Date;
import java.text.SimpleDateFormat;

import java.text.ParseException;

public class Test {
	private String name, set;
	private Date date;
	
	public Test(String name, String set, String date) {
		// trim leading/trailing whitespace from strings
		this.name = name.trim();
		this.set = set.trim();
		
		// SimpleDateFormat converts strings -> and compares dates ez
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yy");
		try {
			this.date = simpleDate.parse(date);
		} catch (ParseException e) {
			// TODO: deal with incorrectly formatted date
		}
	}
	
	// Ruby attr_reader plz
	public String name() { return this.name; }
	public String set() { return this.set; }
	public Date date() { return this.date; }
}
