package model;

import java.util.Objects;

/**
 * Represents a Boat as a BoatNode, inherits from Node Class
 * Used to store Boats in a linked list
 */
public class BoatNode extends Node {

   private Boat boat;

   public BoatNode(Boat boat) {
       this.boat = boat;

   }

   public Boat getBoat() {
       return boat;
   }

    /**
     * Removes this BoatNode from the linked list by connecting previous and next nodes to each other
     *
     * @return true if the Node was successfully removed otherwise false
     */
    @Override
    public Boolean remove() {
        Node previous = this.getPreviousNode();
        Node next = this.getNextNode();

        try {
            previous.setNextNode(next);

            if (next != null) {
                next.setPreviousNode(previous);
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;

    }

    /**
     *  Compares this BoatNode to another Object for equality. This BoatNode is considered
     *  equal to the argument if 
     *
     *      1) this BoatNode and the argument Object points to the same instance of BoatNode
     *      2) The Object is an instance of BoatNode AND it has the same hashcode as this BoatNode
     *
     *  @param o - Object to compare this BoatNode to
     *
     *  @return true if this BoatNode and argument o are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if ( !(o instanceof BoatNode) ) { return false; }

        return this.hashCode() == ((BoatNode)o).hashCode();
    }

    /**
     *  Calculates a hashcode for this BoatNode from its Boat Object
     *
     *  @return a hashcode of this BoatObject
     *  @see model.Boat#hashCode() Boat.hashCode
     */
    @Override
    public int hashCode() {
        return boat.hashCode();
    }
}
