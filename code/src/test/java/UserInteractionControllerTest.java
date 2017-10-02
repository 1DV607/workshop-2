import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;

import model.Registry;
import model.JsonParser;
import model.Member;
import model.Boat;
import model.BoatType;
import io.Dao;
import controller.UserInteractionObserver;
import controller.UserInteractionController;
import view.UserInterface;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonArray;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserInteractionControllerTest {

    private static final File DAO_TEST_OUT = new File("./build/test/controller_out.json");

    private static JsonParser parser;
    private static Registry registry;
    private static DummyUserInterface dummyUI = new DummyUserInterface();
    private static UserInteractionController controller;

    private static boolean displayVerboseListCalled;
    private static boolean displayCompactListCalled;
    private static boolean displayAddMemberCalled;
    private static boolean displayMemberInformationCalled;
    private static boolean displayEditMemberCalled;
    private static boolean displayAddBoatCalled;
    private static boolean displayEditBoatCalled;
    private static boolean displayWelcomeCalled;
    private static boolean displayErrorCalled;

    @BeforeClass
    public static void beforeAll() {
        parser = new JsonParser();
        dummyUI = new DummyUserInterface();
    }

    @Before
    public void beforeEach() {
        displayVerboseListCalled = false;
        displayCompactListCalled = false;
        displayAddMemberCalled = false;
        displayMemberInformationCalled = false;
        displayEditMemberCalled = false;
        displayAddBoatCalled = false;
        displayEditBoatCalled = false;
        displayWelcomeCalled = false;
        displayErrorCalled = false;

        registry = new Registry(new Dao(DAO_TEST_OUT), parser);
        controller = new UserInteractionController(dummyUI, registry); 
        dummyUI.observer = controller;
        controller.launch();
    }

    @Test
    public void displayVerboseListTest() {
        assertTrue(displayVerboseListCalled);
    }

    @Test
    public void displayCompactListTest() {
        dummyUI.observer.onCommandSelected("8");
        assertTrue(displayCompactListCalled);
    }

    @Test
    public void displayAddMemberTest() {
        dummyUI.observer.onCommandSelected("1");
        assertTrue(displayAddMemberCalled);
    }

    @Test
    public void displayMemberInformationTest() {
        dummyUI.observer.onCommandSelected("1");
        dummyUI.observer.onSubmitted(Json.createObjectBuilder()
                .add("firstName", "Abel")
                .add("lastName", "Abelsson")
                .add("address", "Abelgatan 1")
                .add("socialSecurityNumber", "3802110201")
                .build());
        
        dummyUI.observer.onCommandSelected("2 1");
        assertTrue(displayMemberInformationCalled);
    }

    @Test
    public void displayEditMemberTest() {
        dummyUI.observer.onCommandSelected("1");
        dummyUI.observer.onSubmitted(Json.createObjectBuilder()
                .add("firstName", "Abel")
                .add("lastName", "Abelsson")
                .add("address", "Abelgatan 1")
                .add("socialSecurityNumber", "3802110201")
                .build());

        dummyUI.observer.onCommandSelected("3 1");
    }

    @Test
    public void displayAddBoatTest() {
        dummyUI.observer.onCommandSelected("1");
        dummyUI.observer.onSubmitted(Json.createObjectBuilder()
                .add("firstName", "Abel")
                .add("lastName", "Abelsson")
                .add("address", "Abelgatan 1")
                .add("socialSecurityNumber", "3802110201")
                .build());

        dummyUI.observer.onCommandSelected("5 1");
        assertTrue(displayAddBoatCalled);
    }

    @Test
    public void displayEditBoatTest() {
        dummyUI.observer.onCommandSelected("1");
        dummyUI.observer.onSubmitted(Json.createObjectBuilder()
                .add("firstName", "Abel")
                .add("lastName", "Abelsson")
                .add("address", "Abelgatan 1")
                .add("socialSecurityNumber", "3802110201")
                .build());


        dummyUI.observer.onCommandSelected("5 1");
        dummyUI.observer.onSubmitted(Json.createObjectBuilder()
                .add("size", "5")
                .add("boatType", "Motorsailer")
                .build());

        dummyUI.observer.onCommandSelected("6 1 1");
        assertTrue(displayEditBoatCalled);
    }

    @Test
    public void displayErrorOnEmptyInputTest() {
        dummyUI.observer.onCommandSelected("");
        assertTrue(displayErrorCalled);
    }
    @Test
    public void displayErrorOnMissingArgumentsTest() {
        dummyUI.observer.onCommandSelected("6");
        assertTrue(displayErrorCalled);
    }

    @Test
    public void displayErrorOnInvalidCommandTest() {
        dummyUI.observer.onCommandSelected("0");
        assertTrue(displayErrorCalled);

        dummyUI.observer.onCommandSelected("a");
        assertTrue(displayErrorCalled);
    }

    @Test
    public void displayWelcomeTest() {
        assertTrue(displayWelcomeCalled);
    }

    @Test
    public void addMemberTest() {
        dummyUI.observer.onCommandSelected("1");
        dummyUI.observer.onSubmitted(Json.createObjectBuilder()
            .add("socialSecurityNumber", "7501010505")
            .add("firstName", "Olof")
            .add("lastName", "Olofsson")
            .add("address", "Olofsgatan 1")
            .build());

        try {
            TestUtils.getMemberBySsn(registry, "7501010505");
        } catch (NoSuchElementException ex) {
            fail();
        }
    }

    @Test
    public void editMemberTest() {
        dummyUI.observer.onCommandSelected("1");
        dummyUI.observer.onSubmitted(Json.createObjectBuilder()
            .add("socialSecurityNumber", "7501010505")
            .add("firstName", "Olof")
            .add("lastName", "Olofsson")
            .add("address", "Olofsgatan 1")
            .build());

        dummyUI.observer.onCommandSelected("3 1");
        dummyUI.observer.onSubmitted(Json.createObjectBuilder()
            .add("firstName", "Olof")
            .add("lastName", "Olofsson")
            .add("address", "Olofsgatan 10")
            .build());

        Member member = TestUtils.getMemberBySsn(registry, "7501010505");
        assertEquals("Olof", member.getFirstName());
        assertEquals("Olofsson", member.getLastName());
        assertEquals("Olofsgatan 10", member.getAddress());
    }

    @Test
    public void addBoatTest() {
        dummyUI.observer.onCommandSelected("1");
        dummyUI.observer.onSubmitted(Json.createObjectBuilder()
            .add("socialSecurityNumber", "7501010505")
            .add("firstName", "Olof")
            .add("lastName", "Olofsson")
            .add("address", "Olofsgatan 1")
            .build());

        dummyUI.observer.onCommandSelected("5 1");
        dummyUI.observer.onSubmitted(Json.createObjectBuilder()
            .add("size", "5")
            .add("boatType", "Motorsailer")
            .build());

        Member member = TestUtils.getMemberBySsn(registry, "7501010505");
        List<Boat> boats = parser.jsonToBoatList(registry.getMemberBoats(member.getMemberID()));
        assertEquals(1, boats.size());
        assertEquals(5, boats.get(0).getSize());
        assertEquals(BoatType.Motorsailer, boats.get(0).getBoatType());
    }

    @Test
    public void editBoatTest() {
        dummyUI.observer.onCommandSelected("1");
        dummyUI.observer.onSubmitted(Json.createObjectBuilder()
            .add("socialSecurityNumber", "7501010505")
            .add("firstName", "Olof")
            .add("lastName", "Olofsson")
            .add("address", "Olofsgatan 1")
            .build());

        dummyUI.observer.onCommandSelected("5 1");
        dummyUI.observer.onSubmitted(Json.createObjectBuilder()
            .add("size", "5")
            .add("boatType", "Motorsailer")
            .build());

        dummyUI.observer.onCommandSelected("6 1 1");
        dummyUI.observer.onSubmitted(Json.createObjectBuilder()
            .add("size", "6")
            .add("boatType", "Motorsailer")
            .build());
        
        Member member = TestUtils.getMemberBySsn(registry, "7501010505");
        List<Boat> boats = parser.jsonToBoatList(registry.getMemberBoats(member.getMemberID()));
        assertEquals(1, boats.size());
        assertEquals(6, boats.get(0).getSize());
        assertEquals(BoatType.Motorsailer, boats.get(0).getBoatType());
    }

    private static class DummyUserInterface implements UserInterface {
        private UserInteractionObserver observer;

        public void displayVerboseList(JsonArray jsonArray) {
            displayVerboseListCalled = true;
        }

        public void displayCompactList(JsonArray jsonArray) {
            displayCompactListCalled = true;
        }

        public void displayAddMember() { 
            displayAddMemberCalled = true;
        }

        public void displayMemberInformation(JsonObject jsonMember, JsonArray jsonBoats) {
            displayMemberInformationCalled = true;
            observer.onContinue();
        }

        public void displayEditMember(JsonObject jsonMember) {
            displayEditMemberCalled = true;
        }

        public void displayAddBoat(JsonObject jsonMember) {
            displayAddBoatCalled = true;
        }

        public void displayEditBoat(JsonObject jsonMember, JsonObject jsonBoat) {
            displayEditBoatCalled = true;
        }

        public void displayError(String message) {
            displayErrorCalled = true;
            observer.onContinue();
        }

        public void displayWelcome() {
            displayWelcomeCalled = true;
        }
    }
}
