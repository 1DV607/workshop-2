package model;


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
}
