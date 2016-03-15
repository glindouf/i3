import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.Arrays;
import java.util.*;

public class Sorter {


	static DefaultTableModel model = new DefaultTableModel();
	static ResultSet rs;
	public static JScrollPane scroll;
	public static JFrame frame = new JFrame();

	public static void sortFName() throws SQLException{

		ConnectDB db = new ConnectDB();
		Connection c = db.getConnection();
		Statement s = db.getStatement(c);

		model = new DefaultTableModel();
		frame.dispose();
		
		rs = db.createRS(s, "SELECT * FROM medlem ORDER BY familyName ASC");
		model = resultSetToTableModel(model, rs);
		
		frame = new JFrame("Members");
		scroll = new JScrollPane(new JTable(model));
		
		frame.add(scroll);
		frame.pack();
		frame.setSize(600,800);
		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();

		rs.close();
		s.close();
		c.close();

	}

	public static void sortID() throws SQLException {
		
		ConnectDB db = new ConnectDB();
		Connection c = db.getConnection();
		Statement s = db.getStatement(c);

		model = new DefaultTableModel();
		frame.dispose();

		rs = db.createRS(s, "SELECT * FROM medlem ORDER BY id ASC");
		model = resultSetToTableModel(model, rs);
		
		frame = new JFrame("Members");
		scroll = new JScrollPane(new JTable(model));

		frame.add(scroll);
		frame.pack();
		frame.setSize(600,800);
		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();

		rs.close();
		s.close();
		c.close();

	}

	public static void searchMember() throws SQLException {
        
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


	public static void searchCoach() throws SQLException {
        
        ConnectDB db = new ConnectDB();
        Connection c = db.getConnection();
		Statement s = db.getStatement(c);

		int size = getRowCount("select distinct team from funktion where team IS NOT NULL order by team asc");
		String[] list = new String[size];
        
		ResultSet rs1 = db.createRS(s, "select distinct team from funktion where team IS NOT NULL order by team asc");

			for(int i = 0; rs1.next(); i++) {
	        	list[i] = rs1.getString(2);
			}

		String team = (String) JOptionPane.showInputDialog(null, "Select Team", "Select Coach", JOptionPane.QUESTION_MESSAGE, null, list, "Teams");

        model = new DefaultTableModel();
		frame.dispose();

        ResultSet rs2 = db.createRS(s, "select distinct id, givenName, familyName from medlem natural join funktion where id=(select id from funktion where role='1' and team='" + team + "')");
		model = resultSetToTableModel(model, rs2);

		frame = new JFrame("Coach " + team);
		scroll = new JScrollPane(new JTable(model));

		frame.add(scroll);
		frame.pack();
		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();

		rs1.close();
		rs2.close();
		s.close();
		c.close();

	}

	public static void viewTeam() throws SQLException {

		ConnectDB db = new ConnectDB();
		Connection c = db.getConnection();
		Statement s = db.getStatement(c);

		DefaultTableModel model1 = new DefaultTableModel();
		DefaultTableModel model2 = new DefaultTableModel();
		frame.dispose();

		int size = getRowCount("select distinct team from funktion where team IS NOT NULL order by team asc");
		String[] list = new String[size];
        
		ResultSet rs1 = db.createRS(s, "select distinct team from funktion where team IS NOT NULL order by team asc");

			for(int i = 0; rs1.next(); i++) {
	        	list[i] = rs1.getString(1);
			}

		String team = (String) JOptionPane.showInputDialog(null, "Select Team", "Select Coach", JOptionPane.QUESTION_MESSAGE, null, list, "Teams");


		int nrOfPlayers = getRowCount("select id from funktion where role='0' and team='" + team + "'");


        ResultSet rs2 = db.createRS(s, "select givenName, familyName from medlem natural join funktion where role='0' and team='" + team + "'");
		model1 = resultSetToTableModel(model1, rs2);

        ResultSet rs3 = db.createRS(s, "select givenName, familyName from medlem natural join funktion where role='1' and team='" + team +"'");
		model2 = resultSetToTableModel(model2, rs3);


		JLabel noPlayers = new JLabel("Number of Players in team " + team + " is " + nrOfPlayers);
		JLabel players = new JLabel("Players");
		JLabel coaches = new JLabel("Coaches");


		frame = new JFrame("Team " + team);
		JTable table1 = new JTable(model1);
		JTable table2 = new JTable(model2);

		Container cont = frame.getContentPane();
		cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));
		cont.add(noPlayers);
		noPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
		cont.add(Box.createRigidArea(new Dimension(0,20)));
		cont.add(players);
		players.setAlignmentX(Component.RIGHT_ALIGNMENT);
		cont.add(table1);
		cont.add(Box.createRigidArea(new Dimension(0,20)));
		cont.add(coaches);
		coaches.setAlignmentX(Component.RIGHT_ALIGNMENT);
		cont.add(table2);

		frame.pack();
		frame.setSize(600,800);
		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();

		rs1.close();
		rs2.close();
		rs3.close();
		s.close();
		c.close();

	}


	public static DefaultTableModel resultSetToTableModel(DefaultTableModel model, ResultSet row) throws SQLException {
   
    ResultSetMetaData meta= row.getMetaData();
     
    String cols[]=new String[meta.getColumnCount()];
      
        for(int i=0;i< cols.length;++i)
        {
        cols[i]= meta.getColumnLabel(i+1);
        }

    model.setColumnIdentifiers(cols);

    while(row.next())
        {
        Object data[]= new Object[cols.length];
        for(int i=0;i< data.length;++i)
             {
             data[i]=row.getObject(i+1);
             }
        model.addRow(data);
        }
    return model;
    } 

    public static int getRowCount(String select) throws SQLException {
    	
    	ConnectDB db = new ConnectDB();
    	Connection c = db.getConnection();
		Statement s = db.getStatement(c);

    	ResultSet rs1 = db.createRS(s, select);
		
		ArrayList<String> tempList = new ArrayList<String>();

		while(rs1.next()) {
			tempList.add(rs1.getString(1));
		}


		return tempList.size();
    }



	
}