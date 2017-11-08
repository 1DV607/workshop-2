package model.data;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Represents a Member, inherits from User Class
 * Contains the Boat Objects associated with the Member
 * Implements CRUD functionality for Boat Object
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

    /**
     * constructor that creates a Member object without a member ID
     * @param socialSecurityNumber  - String
     * @param firstName             - String
     * @param lastName              - String
     * @param address               - String
     */
    public Member(String socialSecurityNumber, String firstName,
                  String lastName, String address) {
        super(socialSecurityNumber, firstName, lastName, address);
    }

    public int getMemberID() {
        return memberID;
    }

    /**
     * Creates a boat and adds it to this members list of boats. Generates
     * an ID for the new boat which is unique compared to this member's other
     * boats.
     * @param boatCopy      - Boat Object, containing the information of the new boat to add
     */
    public void addBoat(Boat boatCopy) {
        int id = generateBoatID();
        addBoat(id, boatCopy);
    }

    /**
     * Creates a boat and adds it to this members list of boats.
     *
     * @param boatID,       - ID of the boat to add
     * @param boatCopy      - Boat Object, containing the information of the new boat to add
     */
    public void addBoat(int boatID, Boat boatCopy) {
        Boat boat = new Boat(boatID, boatCopy.getSize(), boatCopy.getBoatType());
        boats.add(boat);
    }

    public Boat getBoat(int boatID) {
        return findBoat(boatID);

    }

    /**
     * Updates the information of the boat with the specified 'boatID'.
     *
     * @param boatID,       - ID of boat to edit
     * @param boatCopy      - Boat Object, containing the new boat information
     */
    public void editBoat(int boatID, Boat boatCopy) {
        Boat boat = findBoat(boatID);
        boat.setBoatType(boatCopy.getBoatType());
        boat.setSize(boatCopy.getSize());

    }

    /**
     * Finds the boat connected to the boat ID and removes it from the Member boat list
     * @param boatID        - int, id of the boat to remove
     */
    public void removeBoat(int boatID) {
        Boat boat = findBoat(boatID);
        boats.remove(boat);

    }

    public ArrayList<Boat> getAllBoats() {
        return boats;
    }

    /**
     * Finds the boat with the matching boat ID within the Member list
     * @param boatID        - int, ID of the boat to find
     * @return Boat - object with matching ID
     * @throws NoSuchElementException - if no boat with matching ID is found
     */
    private Boat findBoat(int boatID) {
        for (Boat boat : boats) {
            if (boat.getBoatID() == boatID) {
                return boat;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Generates a new ID by finding the highest ID within the list, copy it and increase it by 1
     * @return int - the new unique boat ID
     */
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
