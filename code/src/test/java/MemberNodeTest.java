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

public class MemberNodeTest {
    private final static Member MEMBER = new Member();

    private static ArrayList<MemberNode> nodeArray10;
    private static ArrayList<MemberNode> nodeArray1000;

    @BeforeClass
    public static void beforeAll() {
        nodeArray10 = new ArrayList<>();
        nodeArray1000 = new ArrayList<>();

    }

    @Before
    public void before() {
        for (int i = 0; i < 10; i++) {
            nodeArray10.add(new MemberNode(MEMBER));
        }
        for (int i = 0; i < 1000; i++) {
            nodeArray1000.add(new MemberNode(MEMBER));
        }
    }

    @After
    public void after() {
        nodeArray10.clear();
        nodeArray1000.clear();
    }

    @Test
    public void removeTest() {
        Node startingNode = nodeArray10.get(0);

        for (int i = 1; i < 10; i++) {
            startingNode.append(nodeArray10.get(i));

        }

        startingNode.remove();

        for (Node node : nodeArray10) {
            assertNull(node.getNextNode());
            assertNull(node.getPreviousNode());
        }

        startingNode = nodeArray1000.get(0);

        for (int i = 1; i < 1000; i++) {
            startingNode.append(nodeArray1000.get(i));
        }

        startingNode.remove();

        for (Node node : nodeArray1000) {
            assertNull(node.getNextNode());
            assertNull(node.getPreviousNode());
        }
    }
}