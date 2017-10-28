import model.Boat;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class BoatNodeTest {

    private final static Boat BOAT = new Boat(45);

    private static ArrayList<Node> nodeArray10;
    private static ArrayList<Node> nodeArray1000;

    @BeforeClass
    public static void beforeAll() {
        nodeArray10 = new ArrayList<Node>();
        nodeArray1000 = new ArrayList<Node>();

        for (int i = 0; i < 10; i++) {
            nodeArray10.add(new BoatNode(BOAT));
        }
        for (int i = 0; i < 1000; i++) {
            nodeArray1000.add(new BoatNode(BOAT));
        }

    }

    @Test
    public void removeTest() {
        Node startingNode = nodeArray1000.get(0);
        Node nodeToRemove;
        Node nextNode;
        Node previousNode;

        for (int i = 1; i < 1000; i++) {
            startingNode.append(nodeArray1000.get(i));
        }

        nodeToRemove = nodeArray1000.get(100);
        nextNode = nodeToRemove.getNextNode();
        previousNode = nodeToRemove.getPreviousNode();

        nodeToRemove.remove();
        assertEquals(previousNode, nextNode.getPreviousNode());
        assertEquals(nextNode, previousNode.getNextNode());


        nodeToRemove = nodeArray1000.get(500);
        nextNode = nodeToRemove.getNextNode();
        previousNode = nodeToRemove.getPreviousNode();

        nodeToRemove.remove();
        assertEquals(previousNode, nextNode.getPreviousNode());
        assertEquals(nextNode, previousNode.getNextNode());


    }
}
