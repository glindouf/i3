import java.sql.*;
import java.io.*;
import javax.swing.JOptionPane;

public class ConnectDB {
    Connection c;
	Statement s;
    ResultSet rs;
    

    public Connection createConnection() {
    Connection localConn = null;

    try { 
        Class.forName("org.sqlite.JDBC"); //Sqlite-drivrutin
    } catch (ClassNotFoundException cnfe) {
        System.err.println("Couldn't find driver class:");
        System.out.println(cnfe.getMessage());
        System.exit(1);
    }
  
    try {
        localConn = DriverManager.getConnection("jdbc:sqlite:databas.db");
    } catch (SQLException se) {
        System.out.println("Couldn't connect: print out a stack trace and exit.");
        System.out.println(se.getMessage());
        System.exit(1);
    }

    return localConn;
    }


    public Statement myCreateStatement(Connection c) {
	Statement s = null;

	try {
	    s = c.createStatement();
	} catch (SQLException se) {
	    System.out.println("We got an exception while creating a statement:" +
			       "that probably means we're no longer connected.");
	    System.out.println(se.getMessage());
	    System.exit(1);
	}
	return s;
    }

public ResultSet createRS(Statement s, String query) {
	ResultSet rs = null; //varför funkar det inte om detta är innanför try?
	try {
		rs = s.executeQuery(query);

	} catch (SQLException se) {
	    System.out.println("We got an exception while executing our query:" +
			       "that probably means our SQL is invalid");
	    System.out.println(se.getMessage());
	    System.exit(1);
	}
	return rs;
    }

	public Connection getConnection() {
		c = createConnection();
		return c;
	}

    public Statement getStatement(Connection c) {
	s = myCreateStatement(c);
	return s;
	}

	public int getNextId() throws SQLException {
		Connection c = getConnection();
		Statement s = getStatement(c);
		ResultSet rs = createRS(s, "SELECT MAX(id) FROM medlem");
		int id = Integer.parseInt(rs.getObject(1) + "");
		rs.close();
		s.close();
		c.close();
		return id+1;
	}


}