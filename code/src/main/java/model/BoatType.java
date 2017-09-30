package model;

/**
 * Represents the different types of Boats
 */
public enum BoatType {
    Sailboat("Sailboat"),
    Motorsailer("Motorsailer"),
    Canoe("Canoe"),
    Kayak("Kayak"),
    Other("Other");

    private String name;

    private BoatType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BoatType fromString(String str) throws IllegalArgumentException {
        for (BoatType type : BoatType.values()) {
            if (type.name.toLowerCase().equals(str.toLowerCase())) {
                return type;
            }
        }

        throw new IllegalArgumentException("No BoatType with name '" + str + "'.");
    }
}
