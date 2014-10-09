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

public class TestInterfaceGui {
	private TestDatabase db;

	private JFrame frame;
	private JTextField sName;
	private JTextField tName;
	private JTextField date1;
	private JTextField date2;
	private JPanel resultsPanel;
	private JTable table;

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

		int s1 = this.db.addStudent("Ben");
		int s2 = this.db.addStudent("Sharlo");
		int s3 = this.db.addStudent("Raehik");
		int s4 = this.db.addStudent("Charlie");
		int s5 = this.db.addStudent("Ben");
		
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
		
		JPanel namePanel = new JPanel();
		frame.getContentPane().add(namePanel);
		
		sName = new JTextField();
		namePanel.add(sName);
		sName.setColumns(10);
		
		tName = new JTextField();
		namePanel.add(tName);
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
		
		resultsPanel = new JPanel();
		frame.getContentPane().add(resultsPanel);
		
		table = new JTable();
		table.getColumnModel().getColumn(0).setPreferredWidth(123);
		table.getColumnModel().getColumn(1).setPreferredWidth(97);
		resultsPanel.add(table);
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*Integer[] sIds = db.getMatchingStudentIds(sName.getText());
				Integer[] tIds = db.getMatchingTestIds(tName.getText());
				
				if (sIds.length == 0) {
					sIds = db.getAllStudentIds();
				}
				
				if (tIds.length == 0) {
					tIds = db.getAllTestIds();
				}
				//String sDate1 = date1.getText();
				//String sDate2 = date2.getText();
				for (Integer sId : sIds) {
					for (Integer tId : tIds) {
						System.out.println(""+sId + tId);
						System.out.println(db.getStudentResult(sId, tId));
					}
				}
				*/
				
				// Test view
				// =========
				Integer[] tIds = db.getMatchingTestIds(tName.getText());
				Integer tId = tIds[0];
				
				String[] columnNames = {
						"Last Name",
                        "First Name",
                        "Result"};
				
				Object[][] data = {
						{ ""
				}
				
				for (Integer sId : db.getAllStudentIds()) {
					
				}
			}
		});
	}
}
