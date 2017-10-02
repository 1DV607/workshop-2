package model;

import java.util.Objects;
/**
 * Represents a Boat,
 * Contains information about the Boat's type and size
 * Generates and contains the Boat's unique Boat ID
 */
public class Boat {

    private BoatType boatType;
    private int size;
    private long boatID;

    public Boat(long boatID) {
        this.boatID = boatID;
    }

    public Boat(long memberID, int numberOfBoats) {
        boatID = generateBoatID(memberID, numberOfBoats);
    }

    public Boat(long boatID, int size, BoatType type) {
        this.boatID = boatID;
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

    public long getBoatID() {
        return boatID;
    }

    /**
     *  Compares this Boat to another Object for equality. This Boat is considered
     *  equal to the argument if 
     *
     *      1) the argument Object points to the same instance of this Boat 
     *      2) The Object is an instance of Boat AND it has the same hashcode as this Boat
     *
     *  @param o - Object to compare this Boat to
     *
     *  @return true if this Boat and argument o are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if ( !(o instanceof Boat) ) { return false; }

        return this.hashCode() == ((Boat)o).hashCode();
    }

    /**
     *  Calculates a hashcode for this Boat from its boatID, size and boatType.
     *
     *  @return a hashcode of this Boat
     *  @see java.util.Objects#hash(Object...) Objects.hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(boatID, size, boatType);
    }

    /**
     * Takes a MemberID and the number of Boats currently owned by the Member to create
     * an unique boat ID
     * @param memberID - ID of the Member who owns the boat
     * @param numberOfBoats - number of boats the Member owns
     * @return boatID, long
     */
    private long generateBoatID(long memberID, int numberOfBoats) {
        String createBoatID;
        createBoatID = Long.toString(memberID) + Integer.toString(numberOfBoats);

        return Long.parseLong(createBoatID);
    }

}
