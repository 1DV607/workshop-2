package model;

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

    public long getMemberID() {
        return memberID;
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
