package model;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Represents a Member, inherits from User Class
 * Generates and contains the Member's unique member ID
 */
public class Member extends User {

    private int memberID;
    private ArrayList<Boat> boats;

    public Member(int memberID) {
        this.memberID = memberID;
        boats = new ArrayList<>();
    }

    public Member(int memberID, String socialSecurityNumber, String firstName,
                  String lastName, String address) {
        super(socialSecurityNumber, firstName, lastName, address);
        this.memberID = memberID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void addBoat(BoatType boatType, int boatSize) {
        int id = boats.size() +1;
        Boat boat = new Boat(id, boatSize, boatType);
        boats.add(boat);
    }

    public Boat getBoat(int boatID) {
        return findBoat(boatID);

    }

    public void editBoat(int boatID, BoatType boatType, int boatSize) {
        Boat boat = findBoat(boatID);
        boat.setBoatType(boatType);
        boat.setSize(boatSize);

    }

    public void removeBoat(int boatID) {
        Boat boat = findBoat(boatID);
        boats.remove(boat);

    }

    public ArrayList<Boat> getAllBoats() {
        return boats;
    }

    private Boat findBoat(int boatID) {
        for (Boat boat : boats) {
            if (boat.getBoatID() == boatID) {
                return boat;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     *  Compares this Member to another Object for equality. This Member is considered
     *  equal to the argument if 
     *
     *      1) this Member and the argument Object points to the same instance of Member
     *      2) The Object is an instance of Member AND it has the same hashcode as this Member
     *
     *  @param o - Object to compare this Member to
     *
     *  @return true if this Member and argument o are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if ( !(o instanceof Member) ) { return false; }

        return this.hashCode() == ((Member)o).hashCode();
    }

}
