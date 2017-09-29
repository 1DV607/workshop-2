package model;


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
}
