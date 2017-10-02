import java.io.File;
import java.util.NoSuchElementException;

import model.Registry;
import io.Dao;
import model.JsonParser;
import view.UserInterface;
import controller.UserInteractionObserver;
import controller.UserInteractionController;

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

        registry = new Registry(new Dao(DAO_TEST_OUT), new JsonParser());
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
        dummyUI.observer.onCommandSelected("3");
        assertTrue(displayAddMemberCalled);
    }

    @Test
    public void displayEditMemberTest() {
        registry.addMember(Json.createObjectBuilder()
                .add("firstName", "Abel")
                .add("lastName", "Abelsson")
                .add("address", "Abelgatan 1")
                .add("socialSecurityNumber", "3802110201")
                .build());

        dummyUI.observer.onCommandSelected("4 1");
        assertTrue(displayEditMemberCalled);
    }

    @Test
    public void displayAddBoatTest() {
        registry.addMember(Json.createObjectBuilder()
                .add("firstName", "Abel")
                .add("lastName", "Abelsson")
                .add("address", "Abelgatan 1")
                .add("socialSecurityNumber", "3802110201")
                .add("memberID", "1630662920")
                .build());

        registry.addBoat(1630662920L, Json.createObjectBuilder()
                .add("size", "10")
                .add("boatType", "Sailboat")
                .build());

        dummyUI.observer.onCommandSelected("5 1");
        assertTrue(displayAddBoatCalled);
    }

    @Test
    public void displayEditBoatTest() {
        registry.addMember(Json.createObjectBuilder()
                .add("firstName", "Abel")
                .add("lastName", "Abelsson")
                .add("address", "Abelgatan 1")
                .add("socialSecurityNumber", "3802110201")
                .add("memberID", "1630662920")
                .build());

        registry.addBoat(1630662920L, Json.createObjectBuilder()
                .add("boatID", "1630662921")
                .add("size", "10")
                .add("boatType", "Sailboat")
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
        JsonObject newMemberJson = Json.createObjectBuilder()
            .add("socialSecurityNumber", "7501010505")
            .add("firstName", "Olof")
            .add("lastName", "Olofsson")
            .add("address", "Olofsgatan 1")
            .build();

        dummyUI.observer.onCommandSelected("1");
        dummyUI.observer.onSubmitted(newMemberJson);

        try {
            TestUtils.getMemberBySsn(registry, "7501010505");
        } catch (NoSuchElementException ex) {
            fail();
        }
    }

    private static class DummyUserInterface implements UserInterface {
        private UserInteractionObserver observer;

        public void displayVerboseList(JsonArray jsonArray) {
            displayVerboseListCalled = true;
            System.out.println("In displayVerboseList()");
        }

        public void displayCompactList(JsonArray jsonArray) {
            displayCompactListCalled = true;
            System.out.println("In displayCompactList()");
        }

        public void displayAddMember() { 
            displayAddMemberCalled = true;
            System.out.println("In displayAddMember()");
        }

        public void displayMemberInformation(JsonObject jsonMember, JsonArray jsonBoats) {
            displayMemberInformationCalled = true;
            System.out.println("In displayMemberInformation()");
        }

        public void displayEditMember(JsonObject jsonMember) {
            displayEditMemberCalled = true;
            System.out.println("In displayEditMember()");
        }

        public void displayAddBoat(JsonObject jsonMember) {
            displayAddBoatCalled = true;
            System.out.println("In displayAddBoat()");
        }

        public void displayEditBoat(JsonObject jsonMember, JsonObject jsonBoat) {
            displayEditBoatCalled = true;
            System.out.println("In displayEditBoat()");
        }

        public void displayError(String message) {
            displayErrorCalled = true;
            System.out.println("In displayError()");
        }

        public void displayWelcome() {
            displayWelcomeCalled = true;
            System.out.println("In displayWelcome()");
        }
    }
}
