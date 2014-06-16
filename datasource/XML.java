package datasource;


import finalproject.Reader;
import finalproject.ThreadRunner;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Keisuke Ueda on 2014/05/31.
 * This class reads an XML file and parses, then creates ThreadRunner objects.
 */
public class XML implements DataSource {

    private Document document = null;

    /**
     * Constructor
     *
     * @throws Exception is thrown when the specified file is not found.
     */
    public XML() throws Exception {
        String filename = Reader.readString("Enter XML file name: ");
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filename));
        } catch (SAXException e) {
            throw new Exception();
        } catch (IOException e) {
            System.out.println("File: " + filename + " is not found.");
            System.out.println("");
            throw new Exception();
        } catch (ParserConfigurationException e) {
            throw new Exception();
        }
    }


    /**
     * Get ArrayList object which contains ThreadRunner objects.
     *
     * @return is an ArrayList object which contains ThreadRunner objects.
     */
    @Override
    public ArrayList<ThreadRunner> getRunners() {

        ArrayList<ThreadRunner> runnersArrayList = null;

        if (document.hasChildNodes()) {
            Node child = document.getFirstChild();

            while (child != null) {
                runnersArrayList = findRunnersNode(child);
                child = child.getNextSibling();
            }
        }

        return runnersArrayList;
    }

    /**
     * Look for a Runners node, then pass it to traceRunnersNode() function.
     *
     * @param child is a child node.
     * @return is an ArrayList object which contains ThreadRunner objects.
     */
    private ArrayList<ThreadRunner> findRunnersNode(Node child) {
        ArrayList<ThreadRunner> runnersArrayList = null;

        if (child.getNodeName().equals("Runners")) {
            runnersArrayList = traceRunnersNode(child);
        }

        return runnersArrayList;
    }

    /**
     * Look for some Runner nodes which Runners node contains, then pass them to traceEachRunnerNode() function.
     *
     * @param child is a Runners node because findRunnersNode looks for a Runners node then pass it to this function.
     * @return is an ArrayList object which contains ThreadRunner objects.
     */
    private ArrayList<ThreadRunner> traceRunnersNode(Node child) {
        ArrayList<ThreadRunner> runnersArrayList = new ArrayList<ThreadRunner>();

        NodeList runners = child.getChildNodes();
        for (int i = 0; i < runners.getLength(); i++) {
            Node eachRunner = runners.item(i);
            if (eachRunner.getNodeName().equals("Runner")) {
                ThreadRunner tr = traceEachRunnerNode(eachRunner);
                if (tr != null) {
                    runnersArrayList.add(tr);
                }
            }
        }

        return runnersArrayList;
    }

    /**
     * Creates a ThreadRunner instance from a Runner node.
     *
     * @param eachRunner is a Runner node.
     * @return ThreadRunner instance.
     */
    private ThreadRunner traceEachRunnerNode(Node eachRunner) {
        String name;
        String speed;
        String rest;

        name = getName(eachRunner);
        if (name == null)
            return null;

        speed = getChild(eachRunner, "RunnersMoveIncrement");
        if (speed == null)
            return null;

        rest = getChild(eachRunner, "RestPercentage");
        if (rest == null)
            return null;

        return createThreadRunner(name, speed, rest);
    }

    /**
     * Gets a node value from a node named "Name".
     *
     * @param eachRunner is a Runner node.
     * @return Runner name.
     */
    private String getName(Node eachRunner) {
        String runnerName = null;
        NamedNodeMap attrs = eachRunner.getAttributes();
        if (attrs != null) {
            Node name = attrs.getNamedItem("Name");
            runnerName = name.getNodeValue();
        }
        return runnerName;
    }

    /**
     * Looks for nodeName node and returns textContent of the node named nodeName.
     *
     * @param eachRunner is a Runner node.
     * @param nodeName   is a child node named nodeName.
     * @return textContent.
     */
    private String getChild(Node eachRunner, String nodeName) {
        NodeList parameters = eachRunner.getChildNodes();

        for (int i = 0; i < parameters.getLength(); i++) {
            Node n = parameters.item(i);
            if (n.getNodeName().equals(nodeName)) {
                return n.getTextContent();
            }
        }

        return null;
    }


    /**
     * Creates a ThreadRunner instance.
     *
     * @param runnerName  is a name of the runner.
     * @param runnerSpeed is speed of the runner.
     * @param runnerRest  is a value how often the runner rests.
     * @return ThreadRunner instance.
     */
    private ThreadRunner createThreadRunner(String runnerName, String runnerSpeed, String runnerRest) {
        try {
            int speed = Integer.parseInt(runnerSpeed);
            int rest = Integer.parseInt(runnerRest);

            ThreadRunner tr;
            try {
                tr = new ThreadRunner(runnerName, rest, speed);
                return tr;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.err.println("Either " + runnerSpeed + " or " + runnerRest + " is not a number.");
                return null;
            }
        } catch (NumberFormatException ignored) {
            System.err.println("Either " + runnerSpeed + " or " + runnerRest + " is not a number.");
            return null;
        }
    }
}
