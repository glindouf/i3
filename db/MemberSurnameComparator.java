import java.util.Comparator;


public class MemberSurnameComparator implements Comparator<Member> {
    @Override
    public int compare(Member m1, Member m2) {
        if (m1.getSurname().equals(m2.getSurname())) {
            return m1.getGivenname().compareTo(m2.getGivenname());
        } else {
            return m1.getSurname().compareTo(m2.getSurname());
        }
    }
}