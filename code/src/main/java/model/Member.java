package model;

/**
 *
 */
public class Member extends User {

    private long memberID;

    public Member(long memberID) {
        this.memberID = memberID;
    }

    public long getMemberID() {
        return memberID;
    }

}
