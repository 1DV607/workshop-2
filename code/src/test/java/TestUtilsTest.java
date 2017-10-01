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
}
