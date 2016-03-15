import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Jacob
 */
public class Connect {
    Connection conn =null;
    
    public static Connection ConnectDB() {
    try{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("JDBC:sqlite:databas.db");
        JOptionPane.showMessageDialog(null, "Connect to Databas? ");
        
        return conn;
    }
        catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
}
