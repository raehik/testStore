package testStore;

import java.awt.EventQueue;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestInterfaceGui {
	private TestDatabase db;

	private JFrame frame;
	
	private JPanel panel_student;
	private JLabel label_first;
	private JTextField field_first;
	private JLabel label_last;
	private JTextField field_last;
	private JButton button_student;

	private JPanel testPanel;
	private JLabel label_test;
	private JTextField field_test;
	private JTextField date1;
	private JTextField date2;
	private JButton button_all_tests;
	
	private JScrollPane resultsPanel;
	private JTable results;

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
		this.db.setStudentResult(s5, t3, 81);
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
		
		JPanel panel_student = new JPanel();
		frame.getContentPane().add(panel_student);
		
		label_first= new JLabel("First name");
		panel_student.add(label_first);
		
		field_first = new JTextField();
		panel_student.add(field_first);
		field_first.setColumns(10);
		
		label_last = new JLabel("Last name");
		panel_student.add(label_last);
		
		field_last = new JTextField();
		panel_student.add(field_last);
		field_last.setColumns(10);
		
		button_student = new JButton("Student results");
		panel_student.add(button_student);
		button_student.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Test view
				// =========
				Integer[] tIds = db.getMatchingTestIds(field_test.getText());
				Integer tId = tIds[0];
				
				String[] cols = {
						"Last Name",
                        "First Name",
                        "Result" };
				
				Integer[] allStudents = db.getAllStudentIds();
				Object[][] data = new Object[allStudents.length][cols.length];
				int i = 0;
				for (Integer sId : allStudents) {
					data[i] = new Object[]{ db.getStudentLastName(sId), db.getStudentFirstName(sId), db.getStudentResult(sId, tId) };
					i++;
				}
				
				results = new JTable(data, cols);
				resultsPanel.setViewportView(results);
			}
		});
		
		testPanel = new JPanel();
		frame.getContentPane().add(testPanel);
		
		label_test = new JLabel("Test name");
		testPanel.add(label_test);
		
		field_test = new JTextField();
		testPanel.add(field_test);
		field_test.setColumns(10);
		
		JButton btnTest = new JButton("Get all students");
		frame.getContentPane().add(btnTest);
		
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Test view
				// =========
				Integer[] tIds = db.getMatchingTestIds(field_test.getText());
				Integer tId = tIds[0];
				
				String[] cols = {
						"Last Name",
                        "First Name",
                        "Result" };
				
				Integer[] allStudents = db.getAllStudentIds();
				Object[][] data = new Object[allStudents.length][cols.length];
				int i = 0;
				for (Integer sId : allStudents) {
					data[i] = new Object[]{ db.getStudentLastName(sId), db.getStudentFirstName(sId), db.getStudentResult(sId, tId) };
					i++;
				}
				
				results = new JTable(data, cols);
				resultsPanel.setViewportView(results);
			}
		});
		
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
		
		resultsPanel = new JScrollPane();
		frame.getContentPane().add(resultsPanel);
		
		results = new JTable();
		resultsPanel.setViewportView(results);
		
		button_all_tests = new JButton("All tests");
		frame.getContentPane().add(button_all_tests);
		button_all_tests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// All tests view
				// ==============
				String[] cols = {
						"Date",
                        "Set",
                        "Name" };

				Integer[] allTests = db.getAllTestIds();
				Object[][] data = new Object[allTests.length][cols.length];
				int i = 0;
				
				for (Integer tId : allTests) {
					data[i] = new Object[]{ db.getTestDate(tId), db.getTestSet(tId), db.getTestName(tId) };
					i++;
				}
				
				results = new JTable(data, cols);
				resultsPanel.setViewportView(results);
			}
		});
	}
}
