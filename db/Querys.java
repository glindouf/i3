import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.Arrays;
import java.util.*;


public class Querys {


	static DefaultTableModel model = new DefaultTableModel();
	static ResultSet rs;
	public static JScrollPane scrollPane;
	public static JFrame frame = new JFrame();

	public static void sort(String query) throws SQLException{

		ConnectDB db = new ConnectDB();
		Connection c = db.getConnection();
		Statement s = db.getStatement(c);

		model = new DefaultTableModel();
		frame.dispose();
		
		rs = db.createRS(s, query);
		model = resultSetToTableModel(model, rs);
		
		frame = new JFrame("Members");
		scrollPane = new JScrollPane(new JTable(model));
		
		frame.add(scrollPane);
		frame.pack();
		frame.setSize(600,800);
		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();

		rs.close();
		s.close();
		c.close();
	}

		public static void searchForMemberID() throws SQLException {
        
        ConnectDB db = new ConnectDB();
		Connection c = db.getConnection();
		Statement s = db.getStatement(c);

        while(true) {
        model = new DefaultTableModel();
		frame.dispose();

	        try {
	        int memberID = Integer.parseInt(JOptionPane.showInputDialog("Enter Member ID"));
	    	rs = db.createRS(s, "SELECT * FROM medlem WHERE id='" + memberID + "'");
	    	
	    	String tempstring = rs.getString(1); //används?
			model = resultSetToTableModel(model, rs);

			frame = new JFrame("Member");
			scroll = new JScrollPane(new JTable(model));

			frame.add(scroll);
			frame.pack();
			frame.setVisible(true);
			frame.revalidate();
			frame.repaint();
			break;
			}
			catch (NumberFormatException e1) {
				if(e1.getMessage().equals("null")) {
					break;
				}
				else {
					JOptionPane.showMessageDialog(null, "Incorrect Input", "Error", JOptionPane.ERROR_MESSAGE);
				}
			
			}
			catch (SQLException e2) {
				JOptionPane.showMessageDialog(null, "ID does not exist, try again.", "Error", JOptionPane.ERROR_MESSAGE);	
			}
		}

		rs.close();
		s.close();
		c.close();
	}

	public static void searchForMemberName() throws SQLException {
        
        ConnectDB db = new ConnectDB();
		Connection c = db.getConnection();
		Statement s = db.getStatement(c);

        while(true) {
        model = new DefaultTableModel();
		frame.dispose();

	        try {
	        String familyName = JOptionPane.showInputDialog("Enter Member name");
	    	rs = db.createRS(s, "SELECT * FROM medlem WHERE familyName='" + familyName + "'");
	    	
	    	String tempstring = rs.getString(1); //används?
			model = resultSetToTableModel(model, rs);

			frame = new JFrame("Member");
			scroll = new JScrollPane(new JTable(model));

			frame.add(scroll);
			frame.pack();
			frame.setVisible(true);
			frame.revalidate();
			frame.repaint();
			break;

			}
			catch (NumberFormatException e1) {
				if(e1.getMessage().equals("null")) {
					break;
				}
				else {
					JOptionPane.showMessageDialog(null, "Incorrect Input", "Error", JOptionPane.ERROR_MESSAGE);
				}
			
			}
			catch (SQLException e2) {
				JOptionPane.showMessageDialog(null, "Surname does not exist, try again.", "Error", JOptionPane.ERROR_MESSAGE);	
			}

		}
	}



}