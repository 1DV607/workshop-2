import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

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

    //private static final File daoTestOut = new File("");
    private static Dao dao;
    private static JsonParser parser;
    private static Registry registry;

    @BeforeClass
    public static void beforeAll() {
        dao = new Dao();
        parser = new JsonParser();
    }

    @Before
    public void beforeEach() {
        registry = new Registry(dao, parser);
    }

    @Test
    public void addMemberTest() {
        JsonObject memberJson = Json.createObjectBuilder()
            .add("firstName", "Ernst")
            .add("lastName", "Kirchsteiger")
            .add("address", "Tralalaland 1")
            .add("socialSecurityNumber", "5711032040")
            .build();

        assertTrue(registry.addMember(memberJson));

        long memberID = RegistryTest.getMemberBySsn(registry, memberJson.getString("socialSecurityNumber")).getMemberID();

        assertTrue(RegistryTest.hasMember(registry, memberID));
        Member member = RegistryTest.getMemberById(registry, memberID);

        assertEquals("Ernst", member.getFirstName());
        assertEquals("Kirchsteiger", member.getLastName());
        assertEquals("Tralalaland 1", member.getAddress());
        assertEquals("5711032040", member.getSocialSecurityNumber());
    }

    @Test
    public void editMemberTest() {
        JsonObject member = Json.createObjectBuilder()
            .add("firstName", "Ernst")
            .add("lastName", "Kirchsteiger")
            .add("address", "Tralalaland 1")
            .add("socialSecurityNumber", "5711032040")
            .build();

        registry.addMember(member);
        long memberID = RegistryTest.getMemberBySsn(registry,
                member.getString("socialSecurityNumber")).getMemberID();

        JsonObject editedMember = Json.createObjectBuilder()
            .add("firstName", "")
            .add("lastName", "")
            .add("socialSecurityNumber", "")
            .add("address", "Lyckliga gatan 7")
            .build();

        registry.editMember(memberID, editedMember);

        Member edited = RegistryTest.getMemberById(registry, memberID);
        assertEquals("Ernst", edited.getFirstName());
        assertEquals("Kirchsteiger", edited.getLastName());
        assertEquals("5711032040", edited.getSocialSecurityNumber());
        assertEquals("Lyckliga gatan 7", edited.getAddress());
    }

    @Test
    public void removeMemberTest() {
        JsonObject member = Json.createObjectBuilder()
            .add("firstName", "Ernst")
            .add("lastName", "Kirchsteiger")
            .add("address", "Tralalaland 1")
            .add("socialSecurityNumber", "5711032040")
            .build();

        registry.addMember(member);
        long memberID = RegistryTest.getMemberBySsn(registry,
                member.getString("socialSecurityNumber")).getMemberID();

        registry.removeMember(memberID);
        assertFalse(RegistryTest.hasMember(registry, memberID));
    }

    @Test
    public void addBoatTest() {
        JsonObject member = Json.createObjectBuilder()
            .add("firstName", "Ernst")
            .add("lastName", "Kirchsteiger")
            .add("address", "Tralalaland 1")
            .add("socialSecurityNumber", "5711032040")
            .build();

        registry.addMember(member);
        long memberID = RegistryTest.getMemberBySsn(registry,
                member.getString("socialSecurityNumber")).getMemberID();

        JsonObject boatJson = Json.createObjectBuilder()
            .add("size", "10")
            .add("boatType", "0")
            .build();

        registry.addBoat(memberID, boatJson);

        Boat boat = RegistryTest.getBoats(registry, memberID).get(0);
        long boatId = boat.getBoatID();
        assertEquals(10, boat.getSize());
        assertEquals(BoatType.SailBoat, boat.getBoatType());
    }

    @Test
    public void editBoatTest() {
        JsonObject member = Json.createObjectBuilder()
            .add("firstName", "Ernst")
            .add("lastName", "Kirchsteiger")
            .add("address", "Tralalaland 1")
            .add("socialSecurityNumber", "5711032040")
            .build();

        registry.addMember(member);
        long memberID = RegistryTest.getMemberBySsn(registry,
                member.getString("socialSecurityNumber")).getMemberID();

        JsonObject boatJson = Json.createObjectBuilder()
            .add("size", "10")
            .add("boatType", "0")
            .build();

        registry.addBoat(memberID, boatJson);

        long boatId = RegistryTest.getBoats(registry, memberID).get(0).getBoatID();

        JsonObject boatJsonEdit = Json.createObjectBuilder()
            .add("size", "5")
            .add("boatType", "2")
            .build();

        registry.editBoat(memberID, boatId, boatJsonEdit);

        assertTrue(RegistryTest.hasBoat(registry, memberID, boatId));
        List<Boat> boats = RegistryTest.getBoats(registry, memberID);
        assertEquals(1, boats.size());
        Boat boat = boats.get(0);
        assertEquals(5, boat.getSize());
        assertEquals(BoatType.Motorsailer, boat.getBoatType());
    }

    @Test
    public void removeBoatTest() {
        JsonObject member = Json.createObjectBuilder()
            .add("firstName", "Ernst")
            .add("lastName", "Kirchsteiger")
            .add("address", "Tralalaland 1")
            .add("socialSecurityNumber", "5711032040")
            .build();

        registry.addMember(member);
        long memberID = RegistryTest.getMemberBySsn(registry,
                member.getString("socialSecurityNumber")).getMemberID();

        JsonObject boatJson = Json.createObjectBuilder()
            .add("size", "10")
            .add("boatType", "0")
            .build();

        registry.addBoat(memberID, boatJson);

        long boatId = RegistryTest.getBoats(registry, memberID).get(0).getBoatID();

        registry.removeBoat(memberID, boatId);
        assertFalse(RegistryTest.hasBoat(registry, memberID, boatId));
    }

    @Test
    public void getAllMembersInfoTest() {

    }

    private static boolean hasMember(Registry registry, long memberId) {
        try {
            Member member = RegistryTest.getMemberById(registry, memberId);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    private static boolean hasBoat(Registry registry, long memberId, long boatId) {
        try {
            List<Boat> boats = getBoats(registry, memberId);
            
            for (Boat boat : boats) {
                if (boat.getBoatID() == boatId) {
                    return true;
                }
            }

            return false;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    private static Member getMemberById(Registry registry, long memberID) throws NoSuchElementException {
        JsonParser parser = new JsonParser();

        for (JsonValue container : registry.getAllMembersInfo()) {
            JsonObject member = ((JsonObject)container).getJsonObject("member");
            long mID = Long.parseLong(member.getString("memberID"));

            if (memberID == mID) {
                return parser.jsonToMember(member);
            }
        }

        throw new NoSuchElementException();
    }

    private static Member getMemberBySsn(Registry registry, String ssn) throws NoSuchElementException {
        JsonParser parser = new JsonParser();

        for (JsonValue container : registry.getAllMembersInfo()) {
            JsonObject member = ((JsonObject)container).getJsonObject("member");
            String mSsn = member.getString("socialSecurityNumber");

            System.out.println("member: " + member);

            if (ssn.equals(mSsn)) {
                return parser.jsonToMember(member);
            }
        }

        throw new NoSuchElementException();
    }
    
    private static List<Boat> getBoats(Registry registry, long memberID) throws NoSuchElementException {
        JsonParser parser = new JsonParser();

        for (JsonValue container : registry.getAllMembersInfo()) {
            JsonObject member = ((JsonObject)container).getJsonObject("member");
            long mID = Long.parseLong(member.getString("memberID"));

            if (memberID == mID) {
                List<Boat> boats = new ArrayList<>();

                for (JsonValue boat : ((JsonObject)container).getJsonArray("boats")) {
                    boats.add(parser.jsonToBoat((JsonObject)boat));
                }

                return boats;
            }
        }

        throw new NoSuchElementException();
    }
}
