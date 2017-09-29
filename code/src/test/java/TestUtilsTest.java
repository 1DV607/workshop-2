import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

import javax.json.Json;
import javax.json.JsonValue;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

public class TestUtilsTest {
    
    @Test
    public void jsonObjectsEqualEmptyTest() {
        assertTrue(TestUtils.jsonObjectsEqual(
                Json.createObjectBuilder().build(),
                Json.createObjectBuilder().build()));

        assertFalse(TestUtils.jsonObjectsEqual(
                Json.createObjectBuilder().build(),
                Json.createObjectBuilder()
                    .add("name", "Henrik")        
                    .build()));

        assertFalse(TestUtils.jsonObjectsEqual(
                Json.createObjectBuilder()
                    .add("name", "Henrik") 
                    .build(),
                Json.createObjectBuilder().build()));
    }

    public void jsonObjectsEqualSimpleTest() {
        assertTrue(TestUtils.jsonObjectsEqual(
                Json.createObjectBuilder()
                    .add("name", "Daniel").build(),
                Json.createObjectBuilder()
                    .add("name", "Daniel").build()));

        assertFalse(TestUtils.jsonObjectsEqual(
                Json.createObjectBuilder()
                    .add("name", "Daniel")
                    .add("age", 27).build(),
                Json.createObjectBuilder()
                    .add("name", "Daniel").build()));

        assertFalse(TestUtils.jsonObjectsEqual(
                Json.createObjectBuilder()
                    .add("name", "Daniel").build(),
                Json.createObjectBuilder()
                    .add("name", "Daniel")
                    .add("age", 27).build()));
    }

    @Test
    public void jsonObjectsEqualSimpleComplex() {
        assertTrue(TestUtils.jsonObjectsEqual(
                Json.createObjectBuilder()
                    .add("address", Json.createObjectBuilder()
                        .add("street", "Kyrkogatan")
                        .add("number", 13)
                        .add("postCode", 34143))
                    .build(),
                Json.createObjectBuilder()
                    .add("address", Json.createObjectBuilder()
                        .add("number", 13)
                        .add("street", "Kyrkogatan")
                        .add("postCode", 34143))
                    .build()));
    }

    @Test
    public void createJsonMemberArrayTest() {
        JsonArray expected = Json.createArrayBuilder()
            .add(Json.createObjectBuilder()
                    .add("member", Json.createObjectBuilder()
                        .add("socialSecurityNumber", "1111111111")
                        .add("memberID", "1111")
                        .add("firstName", "Lars")
                        .add("lastName", "Larsson")
                        .add("address", "Larsgatan 1"))
                    .add("boats", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                            .add("boatID", "111")
                            .add("size", "10")
                            .add("boatType", "0"))
                        .add(Json.createObjectBuilder()
                            .add("boatID", "222")
                            .add("size", "11")
                            .add("boatType", "0"))))
            .add(Json.createObjectBuilder()
                    .add("member", Json.createObjectBuilder()
                        .add("socialSecurityNumber", "2222222222")
                        .add("memberID", "2222")
                        .add("firstName", "Kalle")
                        .add("lastName", "Kallesson")
                        .add("address", "Kallegatan 2"))
                    .add("boats", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                            .add("boatID", "333")
                            .add("size", "12")
                            .add("boatType", "1"))))
            .add(Json.createObjectBuilder()
                    .add("member", Json.createObjectBuilder()
                        .add("socialSecurityNumber", "3333333333")
                        .add("memberID", "3333")
                        .add("firstName", "Per")
                        .add("lastName", "Persson")
                        .add("address", "Persgatan 3"))
                    .add("boats", Json.createArrayBuilder()))
            .build();

        JsonArray actual = TestUtils.createJsonMemberArray(
            new String[][] {
                new String[] { "1111111111", "2222222222", "3333333333" },
                new String[] { "1111", "2222", "3333" },
                new String[] { "Lars", "Kalle", "Per" },
                new String[] { "Larsson", "Kallesson", "Persson" },
                new String[] { "Larsgatan 1", "Kallegatan 2", "Persgatan 3" },
                new String[] { "111 222", "333", ""}
            },
            new String[][] {
                new String[] { "111", "222", "333" },
                new String[] { "10", "11", "12" },
                new String[] { "0", "0", "1" }
            });

        assertEquals(expected, actual);
    }
}
