package model;

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
