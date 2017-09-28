import java.util.Map;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Arrays;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.json.JsonObject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import model.MemberNode;
import model.BoatNode;
import model.JsonParser;
import model.Member;
import model.Boat;
import model.BoatType;

// Utility class
class Array {
  public static <A> A[] of(A ... elements) {
    return elements;
  }
}

public class JsonParserTest {

    private final static String EMPTY_JSON_ARRAY = "[]";

    private static Map<Long, MemberNode> map;
    private static JsonParser parser;
    
    @BeforeClass
    public static void initAll() {
        map = new HashMap<Long, MemberNode>();
        parser = new JsonParser();
    }

    @AfterClass
    public static void tearDownAll() {

    }

    @Before
    public void init() {

    }

    @After
    public void tearDown() {
        map.clear();
    }

    @Test
    public void mapToJsonTestEmpty() {
        JsonArray jArr = parser.mapToJson(map);

        assertEquals(EMPTY_JSON_ARRAY, jArr.toString());
    }

    @Test
    public void mapToJsonSingleNoBoatsTest() {
        final String EXPECTED = 
            "[" + 
              "{" +
                "\"member\":" +
                  "{" +
                    "\"socialSecurityNumber\":" +   "\"5010010202\"," +
                    "\"memberID\":"             +   "\"1111\"," +
                    "\"firstName\":"            +   "\"Lars\"," +
                    "\"lastName\":"             +   "\"Larsson\"," +
                    "\"address\":"              +   "\"Larsgatan 10\"" +
                  "}," + 
                "\"boats\":" + 
                  "[" +
                  "]" +
              "}" +
            "]";

        Member member = new Member(1111);
        member.setFirstName("Lars");
        member.setLastName("Larsson");
        member.setSocialSecurityNumber(5010010202L);
        member.setAddress("Larsgatan 10");

        map.put(member.getMemberID(), new MemberNode(member));
        JsonArray jArr = parser.mapToJson(map);

        assertEquals(EXPECTED, jArr.toString());
    }

    @Test
    public void mapToJsonMultipleMembersTest() {
        Map<String, String[]> expected = new HashMap<>();
        expected.put("firstName", Array.of("Lars", "Kalle", "Per"));
        expected.put("lastName", Array.of("Larsson", "Kallesson", "Persson"));
        expected.put("address", Array.of("Larsgatan 1", "Kallegatan 1", "Persgatan 1"));
        expected.put("memberID", Array.of("1111", "2222", "3333"));
        expected.put("socialSecurityNumber", Array.of("1111111111", "2222222222", "3333333333"));

        Map<String, String[][]> expectedBoats = new HashMap<>();
        expectedBoats.put("1111", Array.of( 
            Array.of("111", "10", "0"),
            Array.of("222", "11", "0") 
        ));

        expectedBoats.put("2222", Array.of( 
            Array.of("333", "12", "1") 
        ));

        expectedBoats.put("3333", Array.of(
            Array.of("444", "13", "2" ),
            Array.of("555", "14", "2" ),
            Array.of("666", "15", "2" )
        ));

        final String[] MEMBER_PROPERTIES = { "firstName", "lastName", "address",
                "memberID", "socialSecurityNumber" };
        final String[] BOAT_PROPERTIES = { "boatID", "size", "boatType" };

        Member member = new Member(1111);
        member.setFirstName("Lars");
        member.setLastName("Larsson");
        member.setSocialSecurityNumber(1111111111L);
        member.setAddress("Larsgatan 1");

        Boat boat1 = new Boat(111);
        boat1.setSize(10);
        boat1.setBoatType(BoatType.SailBoat);

        Boat boat2 = new Boat(222);
        boat2.setSize(11);
        boat2.setBoatType(BoatType.SailBoat);

        MemberNode mNode = new MemberNode(member);
        mNode.append(new BoatNode(boat1));
        mNode.append(new BoatNode(boat2));

        map.put(member.getMemberID(), new MemberNode(member));

        member = new Member(2222);
        member.setFirstName("Kalle");
        member.setLastName("Kallesson");
        member.setSocialSecurityNumber(2222222222L);
        member.setAddress("Kallegatan 2");

        boat1 = new Boat(333);
        boat1.setSize(12);
        boat1.setBoatType(BoatType.Motorsailer);

        mNode = new MemberNode(member);
        mNode.append(new BoatNode(boat1));

        map.put(member.getMemberID(), new MemberNode(member));

        member = new Member(3333);
        member.setFirstName("Per");
        member.setLastName("Persson");
        member.setSocialSecurityNumber(3333333333L);
        member.setAddress("Persgatan 3");

        boat1 = new Boat(444);
        boat1.setSize(13);
        boat1.setBoatType(BoatType.Canoe);

        boat2 = new Boat(555);
        boat2.setSize(14);
        boat2.setBoatType(BoatType.Canoe);

        Boat boat3 = new Boat(666);
        boat3.setSize(15);
        boat3.setBoatType(BoatType.Canoe);

        mNode = new MemberNode(member);
        mNode.append(new BoatNode(boat1));
        mNode.append(new BoatNode(boat2));
        mNode.append(new BoatNode(boat3));

        map.put(member.getMemberID(), new MemberNode(member));

        JsonArray jArr = parser.mapToJson(map);

        SortedMap<String, String[]> actual = new TreeMap<>();
        SortedMap<String, String[]> actualBoats = new TreeMap<>();

        for (JsonValue obj : jArr) {
            JsonObject jMember = ((JsonObject)obj).getJsonObject("member");

            for (String prop : MEMBER_PROPERTIES) {
                actual.put(prop, jMember.getString(prop));
            }

            String[] boatInfo = new String[BOAT_PROPERTIES.length];

            for (int i = 0; i < BOAT_PROPERTIES.length; i++) {
                JsonObject jBoat = (JsonObject)bObj;

                boatInfo[i] = jBoat.getString(BOAT_PROPERTIES[i]);
            }

            actualBoats.put(jMember.getString("memberID"), boatInfo);
        }

        for (String mProp : MEMBER_PROPERTIES) {
            for (String expValue : expected.get(mProp)) {
                assertTrue(Arrays.asList(actual.get(mProp)).contains(expValue));
            }
        }

        for (int kIndex = 0; kIndex < expectedBoats.keySet().size(); kIndex++) {

            /*
             * Assert that boats belong to correct Member
             */
            String expectedKey = (String)expectedBoats.keySet().toArray()[kIndex];
            String actualKey = (String)actualBoats.keySet().toArray()[kIndex];
            assertEquals(expectedKey, actualKey);

            for (int vIndex = 0; vIndex < expectedBoats.get(expectedKey).length; vIndex++) {
                String expValue = expectedBoats.get(expectedKey)[vIndex];
                String actValue = actualBoats.get(expectedKey)[vIndex];
                assertEquals(expValue, actValue);
            }
        }
    }
}
