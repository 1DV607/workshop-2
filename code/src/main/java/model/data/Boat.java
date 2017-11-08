package model.data;

/**
 * Represents a Boat Object
 */
public class Boat {

    private BoatType boatType;
    private int size;
    private int boatID;

    public Boat(int boatID, int size, BoatType type) {
        this.boatID = boatID;
        setSize(size);
        setBoatType(type);
    }

    public Boat(int size, BoatType type) {
        setSize(size);
        setBoatType(type);
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

    public int getBoatID() {
        return boatID;
    }

    /**
     *  Compares this Boat to another Object for equality. This Boat is considered
     *  equal to the argument if boatID, boatType and size is equal
     *
     *  @param o - Object to compare this Boat to
     *  @return true if this Boat and argument o are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        try {
            Boat boatObject = (Boat) o;
            if (boatObject.getBoatID() == boatID &&
                    boatObject.getBoatType().equals(boatType) &&
                    boatObject.getSize() == size) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception exception) {
            return false;
        }
    }

}
