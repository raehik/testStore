package testStore;

import java.util.Date;
import java.text.SimpleDateFormat;

import java.text.ParseException;

public class Test {
	private String name, set;
	private Date date;
	
	public Test(String name, String set, String date) {
		// remove trailing whitespace from name!
		this.name = name;
		this.set = set;
		
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yy");
		try {
			this.date = simpleDate.parse(date);
		} catch (ParseException e) {
			// wat do???
		}
	}
	
	public String name() {
		return this.name;
	}
	
	public String set() {
		return this.set;
	}
	
	public Date date() {
		return this.date;
	}
}
