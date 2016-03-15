import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author phcr
 */
public class Query {
    
    public static Member memberFromRS(ResultSet rs) throws SQLException {
        Member m = new Member();
        m.setId(rs.getString("id"));
        m.setGivenName(rs.getString("givenName"));
        m.setFamilyName(rs.getString("familyName"));
        m.setEmail(rs.getString("email"));
        m.setGender(rs.getInt("gender"));
        m.setBirth(rs.getLong("birth"));
        m.setMemberSince(rs.getLong("memberSince"));
        m.setActive(rs.getBoolean("active"));
        return m;
    }
    
    /** Insert **/    
    public static boolean insertMember(Register m) throws Exception {
        try {
            Connect.ConnectDB().setAutoCommit(false);
            Statement stmnt = Connect.ConnectDB().createStatement();
            int active = (m.isActive()) ? 1 : 0;
            boolean hasTeam = m.getTeam() != null && !m.getTeam().equals("");
            
            String insertquery = String.format("INSERT INTO member values('%s','%s','%s','%s',%d,%d,%d,%d)",
                    m.getId(), m.getGivenName(), m.getFamilyName(), m.getEmail(), m.getGender(), m.getBirth(), m.getMemberSince(), active);
            stmnt.executeUpdate(insertquery);                                                

            if (hasTeam) {
                String teamquery = String.format("INSERT INTO team_members values('%s','%s')", m.getTeam(), m.getId());
                stmnt.executeUpdate(teamquery);
            }
            
            if (!m.getRoles().isEmpty()) {
                for (Integer i : m.getRoles()) {
                    switch (i) {
                        case 0: { // Child
                            String q = String.format("INSERT INTO child values('%s')", m.getId());
                            stmnt.executeUpdate(q);
                            break;
                        }
                        case 1: { // Parent
                            String q = String.format("INSERT INTO parent values('%s')", m.getId());
                            stmnt.executeUpdate(q);
                            break;
                        }
                        case 2: { // Coach 
                            if (!hasTeam) {
                                Connect.ConnectDB().rollback();
                                Connect.ConnectDB().setAutoCommit(true);
                                Exception ex = new Exception("Coach must have a team", null);
                                throw ex;
                            }
                            String q = String.format("INSERT INTO coach values('%s','%s')", m.getId(), m.getTeam());
                            stmnt.executeUpdate(q);
                            break;
                        }
                    }
                }
            }            
        } catch (SQLException e) {
            System.out.println(e);
            Connect.ConnectDB().rollback();
            Connect.ConnectDB().setAutoCommit(true);
            return false;
        }
        Connect.ConnectDB().commit();
        Connect.ConnectDB().setAutoCommit(true);
        return true;
    }
    
    public static void insertTeam(String team) throws SQLException {
        try {
            Connect.ConnectDB().setAutoCommit(false);
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("INSERT INTO team values('%s')", team);
            
            stmnt.executeUpdate(query);
            Connect.ConnectDB().commit();
        } catch (SQLException e) {
            Connect.ConnectDB().rollback();
            throw e;
        }
    }
    
    /** Update **/
    public static boolean updateMember(Register m) {        
        try {
            int active = (m.isActive()) ? 1 : 0;
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("UPDATE medlem SET givenName='%s', "
                    + "familyName='%s', email='%s', gender=%d, birth=%d, "
                    + "memberSince=%s, active=%d WHERE id = '%s'",
                    m.getGivenName(), m.getFamilyName(),
                    m.getEmail(), m.getGender(), m.getBirth(), 
                    m.getMemberSince(), active, m.getId());
        } catch (SQLException e) {            
            return false;            
        }        
        return true;
    }
    
    
    /** Search
     * @return teams - The ArrayList<String> of all the teams in the database. **/
    public static ArrayList<String> getAllTeams() {
        ArrayList<String> teams = new ArrayList<>();

        try {
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("SELECT givenName FROM team");
            ResultSet rs = stmnt.executeQuery(query);
            
            while (rs.next()) {
                teams.add(rs.getString("givenName"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return teams;
    }
    
    public static Member getMemberWithId(String id) {
       Member m = new Member();
       try {
           Statement stmnt = Connect.ConnectDB().createStatement();
           String query = String.format("SELECT * FROM medlem WHERE id='%s'", id);
           ResultSet rs = stmnt.executeQuery(query);
           
           while (rs.next()) {
               m = memberFromRS(rs);
           }
       } catch (SQLException e) {
           System.out.println(e);
       }
       return m;
    }
    
    public static ArrayList<Member> getMemberWithSurname(String familyName) {
        ArrayList<Member> am = new ArrayList<>();
        try {
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("SELECT * FROM medlem WHERE familyName='%s'", familyName);
            ResultSet rs = stmnt.executeQuery(query);
            
            while (rs.next()) {
                am.add(memberFromRS(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return am;
    }
    
    public static ArrayList<Member> getAllMembers() {    
        ArrayList<Member> members = new ArrayList<>();
        try{
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("SELECT * FROM medlem");
            
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                members.add(memberFromRS(rs));
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return members;
    }
    
    public static ArrayList<String> getMemberTeamWithId(String id) {
        ArrayList<String> team = new ArrayList<>();
        try {
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("SELECT team FROM team_members WHERE mid='%s'", id);
            
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                team.add(rs.getString("team"));
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return team;
    }
    
    public static boolean isParent (String id) {
        boolean b = false;
        
        try {
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("SELECT id FROM parent WHERE id='%s'", id);
            
            ResultSet rs = stmnt.executeQuery(query);
            
            return rs.next();
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return b;
    }
    
    public static boolean isChild (String id) {
        boolean b = false;
        
        try {
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("SELECT id FROM children WHERE id='%s'", id);
            
            ResultSet rs = stmnt.executeQuery(query);
            
            return rs.next();
        } catch (SQLException e) {
            
        }
        return b;
    }
    
    public static boolean isCoach (String id) {
        boolean b = false;
        
        try {
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("SELECT id FROM coach WHERE id='%s'", id);
            
            ResultSet rs = stmnt.executeQuery(query);
            
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return b;
    }
    
    public static ArrayList<Member> getMembersForTeam(String team) {
        ArrayList<Member> tm = new ArrayList<>();
        try {
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("SELECT * FROM medlem "
                    + "JOIN (team_members) ON (team='%s' AND mid=member.id)"
                    + "WHERE member.id NOT IN (select id from parent) AND"
                    + "(select id from coach)", team);
            
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                tm.add(memberFromRS(rs));
            }            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return tm;
    }
    
    public static ArrayList<Member> getCoachesForTeam(String team) {
        ArrayList<Member> cm = new ArrayList<>();
        try {
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("SELECT * FROM medlem "
                    + "JOIN (SELECT id FROM coach WHERE team='%s') AS c ON (c.id=member.id)", team);
            
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                cm.add(memberFromRS(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return cm;
    }
    
    public static ArrayList<Member> getParentsForMember (String id) {
        ArrayList<Member> am = new ArrayList<>();
        
        try {
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("SELECT member.* FROM member" 
                    + " join(select parent.id from parent where parent.childid='%s') as pid"
                    + " on (pid.id=member.id)", id);
   
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                am.add(memberFromRS(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }        
        return am;
    }
    
    public static ArrayList<Member> getChildrenForMember (String id) {
        ArrayList<Member> am = new ArrayList<>();
        
        try {
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("SELECT medlem.* FROM medlem" 
                    + " join(select children.id from children where children.parentid='%s') as cid"
                    + " on (cid.id=member.id)", id);
   
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                am.add(memberFromRS(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }        
        return am;
    }
    
    public static ArrayList<String> getCoachTeams (String id) {
        ArrayList<String> ts = new ArrayList<>();
        
        try {
            Statement stmnt = Connect.ConnectDB().createStatement();
            String query = String.format("SELECT distinct cid.team FROM medlem" 
                    + " join(select coach.id from coach where coach.id='%s') as cid"
                    + " on (cid.id=member.id)", id);
   
            ResultSet rs = stmnt.executeQuery(query);
            while (rs.next()) {
                ts.add(rs.getString("team"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }        
        return ts;
    }
}