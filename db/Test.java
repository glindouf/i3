import javax.swing.*;
import java.util.*;
import java.awt.*;

public class Test extends JFrame {
	public static void main(String [] args ) {

		new Test(Query.getMemberWithSurname("Johansson"));
	}

	public Test(ArrayList<Member> familyMembers){
		setLayout(new GridLayout(familyMembers.size(), 8));	
		for(Member m:familyMembers){
			
			String[] member= m.toString().split(",");
			for(String part:member){	
				add(new JLabel(part));	
			}
		}	
		pack();
		this.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}