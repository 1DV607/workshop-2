import model.Boat;
import model.JsonParser;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoatTest {

    private static final String JSON_FILE_DIR = "./src/test/boat_test_files/";

    private static JsonParser parser;

    @BeforeClass
    public static void beforeAll() {
        parser = new JsonParser();
    }

    @Test
    public void equalsEqualTest() {
        List<Boat> boats = parser.jsonToBoatList(TestUtils.readJsonArrayFromFile(JSON_FILE_DIR + "few_equal.json"));

        for (int i = 0; i < boats.size() - 1; i++) {
            Boat b1 = boats.get(i);
            Boat b2 = boats.get(i + 1);
            assertEquals(b1, b2);
        }
    }

    @Test
    public void equalsNotEqualTest() {
        List<Boat> boats = parser.jsonToBoatList(TestUtils.readJsonArrayFromFile(JSON_FILE_DIR + "few_not_equal.json"));

        for (int i = 0; i < boats.size() - 1; i++) {
            Boat b1 = boats.get(i);
            Boat b2 = boats.get(i + 1);
            assertNotEquals(b1, b2);
        }
    }
}
