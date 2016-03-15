import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.sql.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

public class Test2 extends JPanel {
   public static void main(String[] args) {
   }


     public static TableModel resultSetToTableModel(ResultSet rs) {
          try {
                      ResultSetMetaData metaData = rs.getMetaData();
                      int numberOfColumns = metaData.getColumnCount();
                      ArrayList<String> columnNames = new ArrayList<String>();
                      
                      // Get the column names
                      for (int column = 0; column < numberOfColumns; column++) {
                                      columnNames.add(metaData.getColumnLabel(column + 1));
                      }
                      // Get the rows
                      ArrayList<Object> rows = new ArrayList<Object>();
                      while (rs.next()) {
                                      ArrayList<Object> newRow = new ArrayList<Object>();
                                      for (int i = 1; i <= numberOfColumns; i++) {
                                                     newRow.add(rs.getObject(i));
                                      }
                                      rows.add(newRow);
                      }
                      
                      return new DefaultTableModel(rows, columnNames);
          } catch (Exception e) {
                      e.printStackTrace();
                      return null;
          }
     }
}