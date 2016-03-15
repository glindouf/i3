public class Member {
    
    public static enum Gender {
        Male,
        Female 
    };
    
    public static enum Role {
        Child,
        Parent,
        Coach
    }
    
    private String id;
    private String givenName;
    private String familyName;
    private String email;
    private int gender;
    private long birth;
    private long memberSince;
    private boolean active = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getBirth() {
        return birth;
    }

    public void setBirth(long birth) {
        this.birth = birth;
    }

    public long getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(long memberSince) {
        this.memberSince = memberSince;
    }
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

/*    @ Override   
    public String toString() {

        return String.format(
               "\nid:\t%s\ngivenName:\t%s\nfamilyName:\t%s\nemail:\t%s\ngender:\t%d\nbirth:\t%d\nmemberSince:\t%d\nactive:\t%b",
                this.id,
                this.givenName,
                this.familyName,
                this.email,
                this.gender,
                this.birth,
                this.memberSince,
                this.active
        );
    }
    */

     @Override
    public String toString(){
    
    return      id + "," + 
                familyName + "," + 
                givenName + "," + 
                birth + "," + 
                this.memberSince + "," + 
                this.gender + "," + 
                this.email + "," + 
                this.active;
    }
    
}
