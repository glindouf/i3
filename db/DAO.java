/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clubmanager.dao.access;

import clubmanager.dao.domain.Member;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author phcr
 * This interface serves for the request performed towards the persistent storage of some sort.
 * The class implementing this interface handles the persistance and retrieval of information.
 */
public interface DAO {    
    public ArrayList<Member> getAllMembers();
    public ArrayList<Member> getAllMembersSortedBySurname();
    public ArrayList<Member> getAllMembersSortedById();
    public ArrayList<Member> getMembersFromTeam(String team);
    public ArrayList<String> getAllTeams();
   
    public Member getMemberWithId(String id);
    public Member getMemberWithLastName(String surname);
    public ArrayList<Member> getCoachesForTeam(String team);
    
    public boolean doesIdExist(String id);
   
    public boolean insertMember(Member m) throws SQLException;
    public boolean insertTeam(String s) throws SQLException;
      
    public boolean updateMemberActive(Member m) throws Exception, SQLException;
    public boolean updateMemberEmail(Member m) throws Exception, SQLException;
    public boolean updateMemberRole(Member m) throws Exception, SQLException;
    public boolean addParentChildRelation(Member parent, Member child) throws Exception,SQLException;
   
    public boolean deleteMember(Member m) throws Exception,SQLException;
}
