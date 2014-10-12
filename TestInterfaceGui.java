package testStore;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class TestInterfaceGui {
	private TestDatabase db;

	private JFrame frame;
	private JTextField sFirstName;
	private JTextField sLastName;
	private JTextField tName;
	private JTextField date1;
	private JTextField date2;
	private JTable results;
	private JPanel testPanel;
	private JScrollPane resultsPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestInterfaceGui window = new TestInterfaceGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestInterfaceGui() {
		initialise();
	}
	
	private void formTest() {
		this.db = new TestDatabase();

		int s1 = this.db.addStudent("Ben", "Orchard");
		int s2 = this.db.addStudent("Sharlo", "osu!");
		int s3 = this.db.addStudent("Raehik", "Lerna");
		int s4 = this.db.addStudent("Charlie", "Orchard");
		int s5 = this.db.addStudent("Ben", "Raehik");
		
		//System.out.println(this.db.getMatchingStudentIds("Ben")[0] + ", " + this.db.getMatchingStudentIds("Ben")[1]);

		int t1 = this.db.addTest("CS 1", "Yr. 12 CS", "17/09/13");
		int t2 = this.db.addTest("CS 2", "Yr. 12 CS", "17/09/15");
		int t3 = this.db.addTest("CS 3", "Yr. 12 CS", "17/09/14");

		this.db.setStudentResult(s1, t1, 100);
		this.db.setStudentResult(s1, t2, 90);
		this.db.setStudentResult(s1, t3, 80);
		this.db.setStudentResult(s2, t1, 70);
		this.db.setStudentResult(s2, t2, 60);
		this.db.setStudentResult(s2, t3, 50);
		this.db.setStudentResult(s3, t1, 40);
		this.db.setStudentResult(s3, t2, 30);
		this.db.setStudentResult(s3, t3, 20);
		this.db.setStudentResult(s4, t1, 10);
		this.db.setStudentResult(s4, t2, 75);
		this.db.setStudentResult(s4, t3, 83);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialise() {
		this.formTest();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel studentPanel = new JPanel();
		frame.getContentPane().add(studentPanel);
		
		sFirstName = new JTextField();
		studentPanel.add(sFirstName);
		sFirstName.setColumns(10);
		
		sLastName = new JTextField();
		studentPanel.add(sLastName);
		sLastName.setColumns(10);
		
		testPanel = new JPanel();
		frame.getContentPane().add(testPanel);
		
		tName = new JTextField();
		testPanel.add(tName);
		tName.setColumns(10);
		
		JPanel datePanel = new JPanel();
		frame.getContentPane().add(datePanel);
		
		date1 = new JTextField();
		datePanel.add(date1);
		date1.setColumns(10);
		
		date2 = new JTextField();
		datePanel.add(date2);
		date2.setColumns(10);
		
		JPanel searchPanel = new JPanel();
		frame.getContentPane().add(searchPanel);
		
		JButton searchButton = new JButton("Go");
		searchPanel.add(searchButton);
		
		resultsPanel = new JScrollPane();
		frame.getContentPane().add(resultsPanel);
		
		results = new JTable();
		resultsPanel.setViewportView(results);
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Test view
				// =========
				Integer[] tIds = db.getMatchingTestIds(tName.getText());
				Integer tId = tIds[0];
				
				String[] columnNames = {
						"Last Name",
                        "First Name",
                        "Result" };
				
				Integer[] allIds = db.getAllStudentIds();
				Object[][] data = new Object[allIds.length][columnNames.length];
				int i = 0;
				for (Integer sId : db.getAllStudentIds()) {
					data[i] = new Object[]{ db.getStudentLastName(sId), db.getStudentFirstName(sId), db.getStudentResult(sId, tId) };
					i++;
				}
				
				results = new JTable(data, columnNames);
				resultsPanel.setViewportView(results);
			}
		});
	}
}
