package model;


/**
 *
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

    public void append(Node node) {
        Node lastNode = findEnd();
        lastNode.setNextNode(node);
        node.setPreviousNode(lastNode);
    }

    public abstract Boolean remove();

    Node findEnd() {
        Node currentNode = this;

        while(currentNode.nextNode != null) {
            currentNode = currentNode.nextNode;
        }
        return currentNode;
    }

}
