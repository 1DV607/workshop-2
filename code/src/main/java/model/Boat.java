package model;

/**
 *
 */
public class Boat {

    private BoatType boatType;
    private int size;
    private long boatID;

    public Boat(long boatID) {
        this.boatID = boatID;
    }

    public Boat() {

    }

    public BoatType getBoatType() {
        return boatType;
    }

    public void setBoatType(BoatType boatType) {
        this.boatType = boatType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getBoatID() {
        return boatID;
    }

}
