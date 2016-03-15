import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.*;

public class Test2 extends JPanel {
   public static void main(String[] args) {
    System.out.println(Object.getData("SELECT * FROM medlem"))
   }
}

/*
public class Test2 extends JPanel {
     private boolean DEBUG = false;

     public Test2(ArrayList<Member> list) {
          super(new GridLayout(1, 0));

          list.add(new Member());
        
		 
          JTable table = new JTable(new MyTableModel(list));
          table.setPreferredScrollableViewportSize(new Dimension(500, 70));
          table.setFillsViewportHeight(true);
          // Create the scroll pane and add the table to it.
          JScrollPane scrollPane = new JScrollPane(table);
          // Add the scroll pane to this panel.
          add(scrollPane);
     }

     class Member {
	        private String id;
		    private String givenName;
		    private String familyName;
		    private String email;
		    private int gender;
		    private long birth;
		    private long memberSince;
		    private boolean active = false;

          Member(String id, String givenName, String familyName, String email, int gender,
                    long birth, long memberSince, boolean active) {
               this.givenName = givenName;
               this.familyName = familyName;
               this.email = email;
               this.gender = gender;
               this.birth = birth;
               this.memberSince = memberSince;
               this.active = active;
          }
     }

     class MyTableModel extends AbstractTableModel {
          private String[] columnNames = { "ID", "First name", "Last name",
                    "Email", "Gender", "Birthdate", "Memberdate", "Activitystatus"};
          ArrayList<Member> list = null;

          MyTableModel(ArrayList<Member> list) {
               this.list = list;
          }

          public int getColumnCount() {
               return columnNames.length;
          }

          public int getRowCount() {
               return list.size();
          }

          public String getColumnName(int col) {
               return columnNames[col];
          }

          public Object getValueAt(int row, int col) {

               Member object = list.get(row);

               switch (col) {
               case 0:
                    return object.givenName;
               case 1:
                    return object.familyName;
               case 2:
                    return object.email;
               case 3:
                    return object.gender;
               case 4:
                    return object.birth;
               case 5: 
               		return object.memberSince;
               	case 6:
               		return object.active;
               default:
                    return "unknown";
               }
          }

          public Class getColumnClass(int c) {
               return getValueAt(0, c).getClass();
          }
     }

     /**
      * Create the GUI and show it. For thread safety, this method should be
      * invoked from the event-dispatching thread.
      
     private static void createAndShowGUI() {
          // Create and set up the window.
          JFrame frame = new JFrame("Test2");
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

          // Create and set up the content pane.
          Test2 newContentPane = new Test2();
          newContentPane.setOpaque(true); // content panes must be opaque
          frame.setContentPane(newContentPane);

          // Display the window.
          frame.pack();
          frame.setVisible(true);
     }

     public static void main(String[] args) {
          // Schedule a job for the event-dispatching thread:
          // creating and showing this application's GUI.
          javax.swing.SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                    createAndShowGUI();
                    new Test2(Query.getMemberWithSurname("Johansson"));
               }
          });
     }
}
