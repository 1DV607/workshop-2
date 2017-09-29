package model;

import java.util.Objects;

/**
 * Represents a Member, inherits from User Class
 * Generates and contains the Member's unique member ID
 */
public class Member extends User {

    private long memberID;

    public Member(long memberID) {
        this.memberID = memberID;
    }
    public Member(String socialSecurityNumber) {
        this.setSocialSecurityNumber(socialSecurityNumber);
        memberID = generateMemberID(socialSecurityNumber);
    }
    public Member(long memberID, String socialSecurityNumber, String firstName,
                  String lastName, String address) {
        super(socialSecurityNumber, firstName, lastName, address);
        this.memberID = memberID;
    }

    public long getMemberID() {
        return memberID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if ( !(o instanceof Member) ) { return false; }

        return this.hashCode() == ((Member)o).hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getMemberID(), this.getSocialSecurityNumber(),
                this.getFirstName(), this.getLastName(), this.getAddress()); 
    }

    /**
     * Takes the social security number of the member and creates a unique memberID
     * by using the String hashCode() method
     * @param socialSecurityNumber
     * @return memberID - long
     */
    private long generateMemberID(String socialSecurityNumber) {
        int id = socialSecurityNumber.hashCode();

        return (long)id;
    }

}
