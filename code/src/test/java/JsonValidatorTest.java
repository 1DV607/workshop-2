import model.JsonValidator;
import org.junit.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import static org.junit.Assert.*;

public class JsonValidatorTest {

    private static JsonValidator jsonValidator;
    private static JsonArray correctMember;
    private static JsonArray correctBoat;
    private static JsonArray incorrectMember;
    private static JsonArray incorrectBoat;

    private final static JsonObject CORRECT_BOAT = Json.createObjectBuilder()
            .add("boatID", "111")
            .add("size", "10")
            .add("boatType", "Kayak")
            .build();
    private final static JsonObject INCORRECT_BOAT1 = Json.createObjectBuilder()
            .add("boatType", "Kayak")
            .build();
    private final static JsonObject INCORRECT_BOAT2 = Json.createObjectBuilder()
            .add("boatID", "222")
            .add("size", "aa")
            .add("boatType", "Kayak")
            .build();
    private final static JsonObject INCORRECT_BOAT3 = Json.createObjectBuilder()
            .add("boatID", "333")
            .add("size", "10")
            .build();

    private final static JsonObject CORRECT_MEMBER = Json.createObjectBuilder()
            .add("socialSecurityNumber", "1234512345")
            .add("memberID", "1234")
            .add("firstName", "Daniel")
            .add("lastName", "Danielsson")
            .add("address", "Danielgatan 1")
            .build();

    private final static JsonObject INCORRECT_MEMBER1 = Json.createObjectBuilder()
            .add("socialSecurityNumber", "incorrect")
            .add("memberID", "1234")
            .add("firstName", "Daniel")
            .add("lastName", "Danielsson")
            .add("address", "Danielgatan 1")
            .build();

    private final static JsonObject INCORRECT_MEMBER2 = Json.createObjectBuilder()
            .add("socialSecurityNumber", "1234512345")
            .add("memberID", "1234")
            .add("firstName", "")
            .add("lastName", "Danielsson")
            .add("address", "Danielgatan 1")
            .build();

    private final static JsonObject INCORRECT_MEMBER3 = Json.createObjectBuilder()
            .add("socialSecurityNumber", "1234512345")
            .add("memberID", "1234")
            .add("firstName", "Daniel")
            .add("address", "Danielgatan 1")
            .build();

    private final static JsonObject INCORRECT_MEMBER4 = Json.createObjectBuilder()
            .add("firstName", "Daniel")
            .add("lastName", "Danielsson")
            .add("address", "Danielgatan 1")
            .build();

    @BeforeClass
    public static void beforeAll() {
        jsonValidator = new JsonValidator();
        correctMember = TestUtils.readJsonArrayFromFile("./src/test/parser_test/member_multiple.json");
        correctBoat = TestUtils.readJsonArrayFromFile("./src/test/parser_test/boat_multiple.json");

        incorrectMember = Json.createArrayBuilder()
                .add(CORRECT_MEMBER)
                .add(INCORRECT_MEMBER1)
                .add(INCORRECT_MEMBER2)
                .add(INCORRECT_MEMBER4)
                .build();

        incorrectBoat = Json.createArrayBuilder()
                .add(CORRECT_BOAT)
                .add(INCORRECT_BOAT1)
                .add(INCORRECT_BOAT2)
                .add(INCORRECT_BOAT3)
                .build();
    }

    @Test
    public void isValidBoatTest() {
        assertTrue(jsonValidator.isValidBoat(CORRECT_BOAT));
        assertFalse(jsonValidator.isValidBoat(INCORRECT_BOAT1));
        assertFalse(jsonValidator.isValidBoat(INCORRECT_BOAT2));
        assertFalse(jsonValidator.isValidBoat(INCORRECT_BOAT3));
    }

    @Test
    public void isValidMemberTest() {
        assertTrue(jsonValidator.isValidMember(CORRECT_MEMBER));
        assertFalse(jsonValidator.isValidMember(INCORRECT_MEMBER1));
        assertFalse(jsonValidator.isValidMember(INCORRECT_MEMBER2));
        assertFalse(jsonValidator.isValidMember(INCORRECT_MEMBER3));
        assertFalse(jsonValidator.isValidMember(INCORRECT_MEMBER4));
    }

    @Test
    public void isValidMemberArray() {
        assertTrue(jsonValidator.isValidMemberArray(correctMember));
        assertFalse(jsonValidator.isValidMemberArray(incorrectMember));
    }

    @Test
    public void isValidBoatArray() {
        assertTrue(jsonValidator.isValidBoatArray(correctBoat));
        assertFalse(jsonValidator.isValidBoatArray(incorrectBoat));
    }

}