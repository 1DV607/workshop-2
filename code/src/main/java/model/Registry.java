package model;

import java.util.*;

import io.Dao;
import model.data.Member;


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
     * @param memberID      - ID of the member to add
     * @param memberCopy    - Member, contains the information for the new Member
     */
    public void addMember(int memberID, Member memberCopy) {
        Member member = new Member(memberID,
                memberCopy.getSocialSecurityNumber(),
                memberCopy.getFirstName(),
                memberCopy.getLastName(),
                memberCopy.getAddress());

        members.add(member);
    }

    /**
     * Generates an member id and calls addMember to create and add the new Member
     * @param memberCopy        - Member, contains the information of the new Member
     */
    public void addMember(Member memberCopy) {
        addMember(generateMemberID(), memberCopy);
    }

    /**
     * Removes a Member from the ArrayList
     * @param memberID - id of the Member to remove
     * @return true if the Member was successfully removed, otherwise false
     */
    public boolean removeMember(int memberID) {
        try {
            Member member = findMember(memberID);
            members.remove(member);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    /**
     * Takes a member id and change the Members information to the information
     * contained within the Member Object
     * @param memberID - the id of the Member to change information
     * @param newMemberInfo - Member, temporary Member Object that contains new information
     * @return true if edit was successful otherwise false
     */
    public boolean editMember(int memberID, Member newMemberInfo) {
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
     * @return the member with the specified memberID, if found.
     * @throws NoSuchElementException if no member with the specified memberID was found
     */
    public Member getMember(int memberID) {
        return findMember(memberID);
    }


    /**
     * Takes a member id and uses the id as key to finding the Member in the ArrayList
     * @param memberID - id of the Member to find
     * @return Member, belonging to the member id
     * @throws NoSuchElementException if no member with the specified memberID was found
     */
    private Member findMember(int memberID) {
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
