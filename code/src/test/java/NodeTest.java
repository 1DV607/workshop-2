import model.Member;
import model.MemberNode;
import model.Node;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NodeTest {

    private final static Member MEMBER = new Member();

    private static ArrayList<Node> nodeArray10;
    private static ArrayList<Node> nodeArray1000;

    @BeforeClass
    public static void beforeAll() {
        nodeArray10 = new ArrayList<Node>();
        nodeArray1000 = new ArrayList<Node>();

        for (int i = 0; i < 10; i++) {
            nodeArray10.add(new MemberNode(MEMBER));
        }
        for (int i = 0; i < 1000; i++) {
            nodeArray1000.add(new MemberNode(MEMBER));
        }

    }

    @After
    public void after() {
        for (Node n : nodeArray10) {
            n.setNextNode(null);
            n.setPreviousNode(null);
        }

        for (Node n : nodeArray1000) {
            n.setNextNode(null);
            n.setPreviousNode(null);
        }
    }

    @Test
    public void appendTest() {
        Node startingNode = nodeArray10.get(0);
        Node expected;
        Node actual;

        for (int i = 1; i < 10; i++) {
            startingNode.append(nodeArray10.get(i));
        }

        actual = startingNode;
        for (int i = 1; i < 10 ; i++) {
            actual = actual.getNextNode();
            expected = nodeArray10.get(i);
            assertEquals(expected, actual);
        }

        startingNode = nodeArray1000.get(0);

        for (int i = 1; i < 1000; i++) {
            startingNode.append(nodeArray1000.get(i));
        }

        actual = startingNode;
        for (int i = 1; i < 1000 ; i++) {
            actual = actual.getNextNode();
            expected = nodeArray1000.get(i);
            assertEquals(expected, actual);
        }
    }

}