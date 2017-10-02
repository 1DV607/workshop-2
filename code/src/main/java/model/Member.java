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

    /**
     *  Compares this Member to another Object for equality. This Member is considered
     *  equal to the argument if 
     *
     *      1) this Member and the argument Object points to the same instance of Member
     *      2) The Object is an instance of Member AND it has the same hashcode as this Member
     *
     *  @param o - Object to compare this Member to
     *
     *  @return true if this Member and argument o are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if ( !(o instanceof Member) ) { return false; }

        return this.hashCode() == ((Member)o).hashCode();
    }

    /**
     *  Calculates a hashcode for this Member from its memberID, socialSecurityNumber,
     *  firstName, lastName and address.
     *
     *  @return a hashcode of this Member
     *  @see java.util.Objects#hash(Object...) Objects.hash
     */
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
