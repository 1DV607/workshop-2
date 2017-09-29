package model;


/**
 *
 */
public class MemberNode extends Node {

    private Member member;

    public MemberNode(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

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
