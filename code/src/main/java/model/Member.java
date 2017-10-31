package model;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Represents a Member, inherits from User Class
 * Contains the Boat Objects associated with the Member
 */
public class Member extends User {

    private int memberID;
    private ArrayList<Boat> boats;

    public Member(int memberID, String socialSecurityNumber, String firstName,
                  String lastName, String address) {
        super(socialSecurityNumber, firstName, lastName, address);
        this.memberID = memberID;
        boats = new ArrayList<>();
    }

    public int getMemberID() {
        return memberID;
    }

    /**
     * Creates a boat and adds it to this members list of boats. Generates
     * an ID for the new boat which is unique compared to this member's other
     * boats.
     *
     * @param boatType, type of new boat
     * @param boatSize, size in meters of the new boat
     */
    public void addBoat(BoatType boatType, int boatSize) {
        int id = generateBoatID();
        addBoat(id, boatType, boatSize);
    }

    /**
     * Creates a boat and adds it to this members list of boats.
     *
     * @param boatID, ID of the boat to add
     * @param boatType, type of the boat to add
     * @param size, size of the boat to add  
     */
    public void addBoat(int boatID, BoatType boatType, int size) {
        Boat boat = new Boat(boatID, size,  boatType);
        boats.add(boat);
    }

    public Boat getBoat(int boatID) {
        return findBoat(boatID);

    }

    /**
     * Updates the information of the boat with the specified 'boatID'.
     *
     * @param boatID, ID of boat to edit
     * @param boatType, new boat type
     * @param boatSize, new boat size  
     */
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

    private int generateBoatID() {
        int id = 0;
        if (boats.size() == 0) {
            return id + 1;
        }
        for (Boat boat : boats) {
            if (boat.getBoatID() >= id) {
                id = boat.getBoatID() +1;
            }
        }
        return id;
    }

    /**
     *  Compares this Member to another Object for equality. This Member is considered
     *  equal to the argument if memberID, socialSecurityNumber, firstName, lastName and address is equal.
     *
     *  @param o - Object to compare this Member to
     *  @return true if this Member and argument o are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
       try {
           Member memberObject = (Member) o;
           if (memberObject.getMemberID() == memberID &&
                   memberObject.getSocialSecurityNumber().equals(this.getSocialSecurityNumber()) &&
                   memberObject.getFirstName().equals(this.getFirstName()) &&
                   memberObject.getLastName().equals(this.getLastName()) &&
                   memberObject.getAddress().equals(this.getAddress())) {
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
