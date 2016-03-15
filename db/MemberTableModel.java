import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author phcr
 */
public class MemberTableModel extends AbstractTableModel implements TableModelListener {

    private final String[] columnNames = {
        "ID",
        "Name",
        "Surname",
        "Email",
        "Gender",
        "Birthdate",
        "Joindate",
        "Active",
        "Role(s)",
        "Team",
        "Children"
    };
     
    public ArrayList<Member> data;
    
    public MemberTableModel() {
        
    }
    
    public void setData(ArrayList<Member> lst) {
        this.data = lst;        
    }
    
    public String roleToString(int role) {
        switch (role) {
            case 0:
                return "Player";
            case 1:
                return "Parent";
            case 2:
                return "Coach";
        }
        return ""; // Should not happen
    }
    
    public String rolesToString(List<Integer> roles) {
        StringBuilder sb = new StringBuilder();
        for (Integer role : roles) {
            sb.append(roleToString(role));
            sb.append(",");
        }
        return sb.toString().replaceAll(",$","");
    }
    
    public String teamsToString(List<String> teams) {
        if (teams.isEmpty()) {
            return "";
        } else {
            return teams.get(0);
        }
    }
    
    public String childrenToString(List<Member> kids) {
        StringBuilder sb = new StringBuilder();        
        for (Member c : kids) {
            sb.append(c.getName());
            sb.append(",");
        }
        return sb.toString().replaceAll(",$","");
    }
   
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    @Override
    public int getRowCount() {
        return data.size();        
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Class getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    }

    @Override
    public Object getValueAt(int row, int col) {
        Object value = "";
        Member m = data.get(row);
        switch (col) {
            case 0:
                value = m.getId();
                break;
            case 1:
                value = m.getName();
                break;
            case 2:
                value = m.getSurname();
                break;
            case 3:
                value = m.getEmail();
                break;
            case 4:
                value = m.getGender()==0 ? "Male" : "Female";
                break;
            case 5:
                value = new Date(m.getBirthdate());
                break;
            case 6:
                value = new Date(m.getJoindate());
                break;
            case 7:
                value = m.getActive() == 1;
                break;
            case 8:
                value = rolesToString(m.getRoles()); // Jesus, cast from hell
                break;
            case 9:
                value = teamsToString(m.getTeams());
                break;
            case 10:
                value = childrenToString(m.getChildren());
                break;
        }
        
        return value;
    }
    
    public Member getMemberAtRow(int row) {
        return this.data.get(row);
    }

    @Override
    public void tableChanged(TableModelEvent tme) {
        System.out.println("Data change");
    }
    
}