package model;


/**
 *
 */
public class BoatNode extends Node {

   private Boat boat;

   public BoatNode(Boat boat) {
       this.boat = boat;

   }

   public Boat getBoat() {
       return boat;
   }

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
