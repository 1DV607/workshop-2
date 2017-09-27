package model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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

    }

    public abstract Boolean remove();

    private Node findEnd() {
        throw new NotImplementedException();
    }

}
