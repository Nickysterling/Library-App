package model.member;
import java.util.Objects;

public class Member {
    private static int nextID = 1;
    private final String firstName;
    private final String lastName;
    private final int memberID;

    public Member(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberID = nextID++;
    }

    @Override
    public String toString() {
        return "Member #" + memberID + ": " + firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return memberID == member.memberID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(memberID);
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public int getMemberID() { return memberID; }
}
