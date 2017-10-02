import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

import javax.json.*;

import io.Dao;
import model.Member;
import model.Boat;
import model.BoatType;
import model.Registry;
import model.JsonParser;

public class RegistryTest {

    private static final String JSON_FILE_DIR = "./src/test/registry_test/";
    private static final File DAO_TEST_OUT = new File("./build/test/registry_test/registry_test_out.json");
    private static Dao dao;
    private static JsonParser parser;
    private static Registry registry;

    @BeforeClass
    public static void beforeAll() {
        dao = new Dao(DAO_TEST_OUT);
        parser = new JsonParser();
    }

    @Before
    public void beforeEach() {
        registry = new Registry(dao, parser);
    }

    @Test
    public void getMemberTest() {
        JsonObject expected = Json.createObjectBuilder()
                .add("firstName", "Zack")
                .add("lastName", "Zackberg")
                .add("address", "Z-Gatan 22")
                .add("socialSecurityNumber", "2020202020")
                .add("memberID", "22222")
                .build();

        registry.addMember(expected);
        JsonObject actual = registry.getMember(22222);
        assertTrue(TestUtils.jsonObjectsEqual(expected, actual));
    }

    @Test
    public void getMemberBoatsTest() {
        registry.addMember(Json.createObjectBuilder()
                .add("firstName", "Jonas")
                .add("lastName", "Jonasson")
                .add("address", "Jonasson gatan 1")
                .add("memberID", "22224")
                .add("socialSecurityNumber", "1234012124")
                .build());

        JsonArray expected = TestUtils.readJsonArrayFromFile(JSON_FILE_DIR + "boat_multiple.json");
        for (JsonValue val : expected) {
            registry.addBoat(22224, (JsonObject)val);
        }

        JsonArray actual = registry.getMemberBoats(22224);
        assertTrue(TestUtils.jsonArraysEqual(expected, actual));
    }

    @Test
    public void getBoatTest() {
        JsonObject expected = Json.createObjectBuilder()
                .add("boatID", "1234567")
                .add("size", "10")
                .add("boatType", "Sailboat")
                .build();

        registry.addMember(Json.createObjectBuilder()
                .add("firstName", "Jonas")
                .add("lastName", "Jonasson")
                .add("address", "Jonasson gatan 1")
                .add("memberID", "22223")
                .add("socialSecurityNumber", "1234012124")
                .build());

        registry.addBoat(22223, expected);
        JsonObject actual = registry.getBoat(22223, 1234567);
        assertTrue(TestUtils.jsonObjectsEqual(expected, actual));
    }

    @Test
    public void addMemberTest() {
        JsonObject memberJson = TestUtils.readJsonObjectFromFile(JSON_FILE_DIR + "member.json");
        assertTrue(registry.addMember(memberJson));
        long memberID = TestUtils.getMemberBySsn(registry, memberJson.getString("socialSecurityNumber")).getMemberID();

        Member expected = parser.jsonToMember(memberJson);
        Member actual = parser.jsonToMember(registry.getMember(memberID));
        assertEquals(expected.getSocialSecurityNumber(), actual.getSocialSecurityNumber());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAddress(), actual.getAddress());
    }

    @Test
    public void editMemberTest() {
        JsonObject memberJson = TestUtils.readJsonObjectFromFile(JSON_FILE_DIR + "member.json");
        registry.addMember(memberJson);
        long memberID = TestUtils.getMemberBySsn(registry,
                memberJson.getString("socialSecurityNumber")).getMemberID();

        JsonObject memberJsonEdited = TestUtils.readJsonObjectFromFile(JSON_FILE_DIR + "member_edited.json");
        assertTrue(registry.editMember(memberID, memberJsonEdited));

        Member expected = parser.jsonToMember(memberJsonEdited);
        Member actual = parser.jsonToMember(registry.getMember(memberID));
        assertEquals(expected.getSocialSecurityNumber(), actual.getSocialSecurityNumber());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAddress(), actual.getAddress());
    }

    @Test
    public void removeMemberTest() {
        JsonObject memberJson = TestUtils.readJsonObjectFromFile(JSON_FILE_DIR + "member.json");
        registry.addMember(memberJson);
        long memberID = TestUtils.getMemberBySsn(registry,
                memberJson.getString("socialSecurityNumber")).getMemberID();

        registry.removeMember(memberID);
        assertNull(registry.getMember(memberID));
    }

    @Test
    public void addBoatTest() {
        JsonObject memberJson = TestUtils.readJsonObjectFromFile(JSON_FILE_DIR + "member.json");
        registry.addMember(memberJson);
        long memberID = TestUtils.getMemberBySsn(registry,
                memberJson.getString("socialSecurityNumber")).getMemberID();

        JsonObject boatJson = TestUtils.readJsonObjectFromFile(JSON_FILE_DIR + "boat.json");
        assertTrue(registry.addBoat(memberID, boatJson));

        List<Boat> boats = parser.jsonToBoatList(registry.getMemberBoats(memberID));
        assertNotNull(boats);
        assertEquals(1, boats.size());
        Boat expected = parser.jsonToBoat(boatJson, Optional.of(memberID),
                Optional.of(registry.getMemberBoats(memberID).size()));
        Boat actual = boats.get(0);
        assertEquals(expected.getSize(), actual.getSize());
        assertEquals(expected.getBoatType(), actual.getBoatType());
    }

    @Test
    public void editBoatTest() {
        JsonObject memberJson = TestUtils.readJsonObjectFromFile(JSON_FILE_DIR + "member.json");
        registry.addMember(memberJson);
        long memberID = TestUtils.getMemberBySsn(registry,
                memberJson.getString("socialSecurityNumber")).getMemberID();

        JsonObject boatJson = TestUtils.readJsonObjectFromFile(JSON_FILE_DIR + "boat.json");
        registry.addBoat(memberID, boatJson);
        long boatId = parser.jsonToBoatList(registry.getMemberBoats(memberID)).get(0).getBoatID();

        JsonObject boatJsonEdit = TestUtils.readJsonObjectFromFile(JSON_FILE_DIR + "boat_edited.json");
        assertTrue(registry.editBoat(memberID, boatId, boatJsonEdit));

        List<Boat> boats = parser.jsonToBoatList(registry.getMemberBoats(memberID));
        assertNotNull(boats);
        assertEquals(1, boats.size());
        Boat expected = parser.jsonToBoat(boatJsonEdit, Optional.of(memberID), Optional.of(registry.getMemberBoats(memberID).size()));
        Boat actual = boats.get(0);
        assertEquals(expected.getSize(), actual.getSize());
        assertEquals(expected.getBoatType(), actual.getBoatType());
    }

    @Test
    public void removeBoatTest() {
        JsonObject memberJson = TestUtils.readJsonObjectFromFile(JSON_FILE_DIR + "member.json");
        registry.addMember(memberJson);
        long memberID = TestUtils.getMemberBySsn(registry,
                memberJson.getString("socialSecurityNumber")).getMemberID();

        JsonObject boatJson = TestUtils.readJsonObjectFromFile(JSON_FILE_DIR + "boat.json");
        registry.addBoat(memberID, boatJson);
        long boatId = parser.jsonToBoatList(registry.getMemberBoats(memberID)).get(0).getBoatID();

        registry.removeBoat(memberID, boatId);
        assertNull(registry.getBoat(memberID, boatId));
    }

}
