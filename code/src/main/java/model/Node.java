package model;


/**
 * Represents a Node (Abstract Class)
 * Used to store Nodes in a linked list
 * Contains information about the Node's next node and previous node (within the linked list)
 */
public abstract class Node {

    private Node nextNode;
    private Node previousNode;


    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    /**
     * Tskes a Node and add it to the end of the linked list
     *
     *  ...<-> last_Node <-> new_Node -> null
     *
     * @param node - the Node to add to the list
     */
    public void append(Node node) {
        Node lastNode = findEnd();
        lastNode.setNextNode(node);
        node.setPreviousNode(lastNode);
    }

    public abstract Boolean remove();

    /**
     * Help method to find the end of a linked list by selecting next Node until
     * next Node is null
     * @return  - Node, the last Node of the linked list
     */
    Node findEnd() {
        Node currentNode = this;

        while(currentNode.nextNode != null) {
            currentNode = currentNode.nextNode;
        }
        return currentNode;
    }

}
