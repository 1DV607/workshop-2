package model;

import java.util.*;

import io.Dao;


/**
 * Contains all Member Objects
 * Implements CRUD functionality for Member
 */
public class Registry {

    private ArrayList<Member> members;
    private Dao dao;

    public Registry(Dao dao) {
        this.dao = dao;

        members = dao.load();
    }

    /**
     * Creates a new Member and adds it to the members ArrayList
     * @param memberID,
     * @param socialSecurityNr
     * @param firstName
     * @param lastName
     * @param address
     * @return true of the Member was successfully created and added otherwise false
     */
    public boolean addMember(int memberID, String socialSecurityNr, String firstName, String lastName, String address) {
        try {
            Member member = new Member(memberID, socialSecurityNr, firstName, lastName, address);
            members.add(member);
            saveChanges();
            return true;
        }
        catch (Exception exception) {
            return false;
        }

    }

    /**
     * Generates an member id and calls addMember to create and add the new Member
     * @param socialSecurityNr
     * @param firstName
     * @param lastName
     * @param address
     * @return true if the Member was successfully created and added otherwise false
     */
    public boolean addMember(String socialSecurityNr, String firstName, String lastName, String address) {
        return addMember(generateMemberID(), socialSecurityNr, firstName, lastName, address);
    }

    /**
     * Removes a Member and all Boats connected to this member
     * @param memberID - id of the Member to remove
     * @return true if the Member was successfully removed, otherwise false
     */
    public boolean removeMember(int memberID) {
        try {
            Member member = findMember(memberID);
            members.remove(member);
            saveChanges();
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    /**
     * Takes a member id and change the Members information to the information
     * contained within the Member Object
     * @param memberID - long, the id of the Member to change information
     * @param newMemberInfo - Member, temporary Member Object that contains new information
     * @return true if edit was successful otherwise false
     */
    public boolean editMember(long memberID, Member newMemberInfo) {
        try {
            Member member = findMember(memberID);
            if (newMemberInfo.getSocialSecurityNumber().length() != 0) {
                member.setSocialSecurityNumber(newMemberInfo.getSocialSecurityNumber());
            }
            if (newMemberInfo.getFirstName().length() != 0) {
                member.setFirstName(newMemberInfo.getFirstName());
            }
            if (newMemberInfo.getLastName().length() != 0) {
                member.setLastName(newMemberInfo.getLastName());
            }
            if (newMemberInfo.getAddress().length() != 0) {
                member.setAddress(newMemberInfo.getAddress());
            }
            saveChanges();
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }



    /**
     * Returns an ArrayList containing all Members
     * @return - ArrayList, containing all Members
     */
    public ArrayList<Member> getAllMembers() {
        return members;
    }

    /**
     * Returns the Member object
     * @param memberID - ID of the Member
     * @return Member
     * @throws NoSuchElementException
     */
    public Member getMember(long memberID) {
        return findMember(memberID);

    }


    /**
     * Takes a member id and uses the id as key to finding the Member in the ArrayList
     * @param memberID - id of the Member to find
     * @return Member, belonging to the member id
     * @throws NoSuchElementException
     */
    private Member findMember(long memberID) {
        for (Member member : members) {
            if (member.getMemberID() == memberID) {
                return member;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * finds the highest id among the members in the ArrayList and returns a
     * member id that is +1
     * @return int, id generated for the new member
     */
    private int generateMemberID() {
        int id = 0;
        if (members.size() == 0) {
            return id +1;
        }
        else {
            for (Member member : members) {
                if (member.getMemberID() >= id) {
                    id = member.getMemberID() +1;
                }
            }
            return id;
        }
    }


    /**
     * Calls the method save() in dao to save new information.
     */
    public void saveChanges() {
        dao.save(members);
    }


}
