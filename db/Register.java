import java.util.ArrayList;

/**
 *
 * @author phcr
 */
public class Register extends Member {
    
    public Register () {
        super();
        this.roles = new ArrayList<>();
    }
    
    private ArrayList<Integer> roles;
    private String team;

    public ArrayList<Integer> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Integer> roles) {
        this.roles = roles;
    }
   
    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
    
    public boolean validate () {
        return (
                (this.getId() != null && !this.getId().equals("")) &&
                (this.getGivenName() != null && !this.getGivenName().equals("")) &&
                (this.getFamilyName() != null && !this.getFamilyName().equals("")) &&
                (this.getEmail() != null && !this.getEmail().equals("")) &&
                (this.getGender() > -1) &&
                (this.getBirth() > -1) &&
                (this.getMemberSince() > -1)                
                );
    }
        
}