package testStore;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Test {
	private String name, set;
	public Date date;
	
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
	
	public String getName() {
		return this.name;
	}
	
	public String getSet() {
		return this.set;
	}
}
