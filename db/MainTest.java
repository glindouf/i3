import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.util.Collections;

public class MainTest extends JFrame{

	public void actionPerformed(ActionEvent e){
               showText.setText(Query.getMemberWithId("10").toString());
          }

  public static void main(String [] args) {
  }
}