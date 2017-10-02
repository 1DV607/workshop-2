package model;

import java.util.Objects;

/**
 * Represents a Member as a MemberNode, inherits from Node Class
 * Used to store Members in a linked list
 */
public class MemberNode extends Node {

    private Member member;

    public MemberNode(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    /**
     * Removes this MemberNode by going through each linked Node and setting Next/Previous Nodes to null
     *
     * @return true if this Node was successfully removed otherwise false
     */
    @Override
    public Boolean remove() {
        Node lastNode = this.findEnd();
        Node nextToRemove;

        try {
            while (lastNode.getPreviousNode() != null) {
                nextToRemove = lastNode.getPreviousNode();
                lastNode.setPreviousNode(null);
                lastNode.setNextNode(null);
                lastNode = nextToRemove;
            }
            this.setNextNode(null);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /**
     *  Compares this MemberNode to another Object for equality. This MemberNode is considered
     *  equal to the argument if 
     *
     *      1) this MemberNode's Member is equal to the argument Object
     *
     *  @param o - Object to compare this MemberNode to
     *
     *  @return true if this Member's Member and argument o are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return this.member.equals(o);
    }

    /**
     *  Calculates a hashcode for this MemberNode by hashing its Member
     *
     *  @return a hashcode of this MemberNode's Member
     *  @see model.Member#hashCode() Member.hashCode
     */
    @Override
    public int hashCode() {
        return member.hashCode();
    }
}
