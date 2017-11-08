package model.data;

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

    /**
     *  Parses a String into a BoatType by comparing each BoatType's name to the argument String.
     *  Cases insensitive.
     *
     *  @param str - String to parse to BoatType
     *
     *  @return the parsed BoatType
     *
     *  @throws IllegalArgumentException if the string could not be parsed into a BoatType
     */
    public static BoatType fromString(String str) throws IllegalArgumentException {
        for (BoatType type : BoatType.values()) {
            if (type.name.toLowerCase().equals(str.toLowerCase())) {
                return type;
            }
        }

        throw new IllegalArgumentException("No BoatType with name '" + str + "'.");
    }
}
