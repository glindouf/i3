import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.Arrays;
import java.util.*;


public class Test4 {
	
public static void main(String[] args)  throws SQLException {

	Sorter sort = new Sorter();
	//sort.sortFName(); fungerar
	sort.searchForMemberName();
	}
}