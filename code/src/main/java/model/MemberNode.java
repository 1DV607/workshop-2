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
     *      1) this MemberNode and the argument Object points to the same instance of MemberNode
     *      2) The Object is an instance of MemberNode AND it has the same hashcode as this MemberNode
     *
     *  @param o - Object to compare this MemberNode to
     *
     *  @return true if this MemberNode and argument o are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if ( !(o instanceof MemberNode) ) { return false; }

        return this.hashCode() == ((MemberNode)o).hashCode();
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
