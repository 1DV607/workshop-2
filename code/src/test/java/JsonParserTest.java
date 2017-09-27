import java.util.Map;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import model.MemberNode;
import model.JsonParser;
import model.Member;


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
                    "\"firstName\":"            +   "\"Lars\"" +
                    "\"lastName\":"             +   "\"Larsson\"" +
                    "\"memberID\":"             +   "\"1111\"" +
                    "\"socialSecurityNumber\":" +   "\"5010010202\"" +
                    "\"adress\":" + "\"Lars\""  +   "\"Larsgatan 10\"" +
                  "}," + 
                "\"boats\":" + 
                  "[" +
                  "]" +
              "}" +
            "]";

        Member member = new Member(1111);
        member.setFirstName("Lars");
        member.setLastName("Larsson");

    }
}
