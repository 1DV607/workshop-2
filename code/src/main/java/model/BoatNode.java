package model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 */
class BoatNode extends Node {

   private Boat boat;

   public BoatNode(Boat boat) {
       this.boat = boat;
   }

   public Boat getBoat() {
       throw new NotImplementedException();
   }

    @Override
    public Boolean remove() {
        throw new NotImplementedException();
    }
}
