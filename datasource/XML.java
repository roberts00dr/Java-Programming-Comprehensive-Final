package datasource;


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
 * Created by keta on 2014/05/31.
 */
public class XML implements DataSource {

    @Override
    public ArrayList getRunners() {
//        String filename = Reader.readString("Enter XML file name: ");

        ArrayList result = new ArrayList();

        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("FinalTextData.xml"));
            Node child = document.getFirstChild();

            if (child.getNodeName().equals("Runners"))
            {
                NodeList runners = child.getChildNodes();
                for (int i = 0; i < runners.getLength(); i++)
                {
                    Node eachRunner = runners.item(i);
                    if (eachRunner.getNodeName().equals("Runner"))
                    {
                        String runnerName = "";
                        String runnerSpeed = "";
                        String runnerRest = "";

                        NamedNodeMap attrs = eachRunner.getAttributes();
                        if (attrs != null)
                        {
                            Node name = attrs.getNamedItem("Name");
                            runnerName = name.getNodeValue();
                        }

                        NodeList parameters = eachRunner.getChildNodes();

                        for (int j = 0; j < parameters.getLength(); j++)
                        {
                            Node n = parameters.item(j);

                            if (n.getNodeName().equals("RunnersMoveIncrement"))
                            {
                                runnerSpeed = n.getTextContent();
                            }

                            if (n.getNodeName().equals("RestPercentage"))
                            {
                                runnerRest = n.getTextContent();
                            }
                        }

                        try
                        {
                            int speed = Integer.parseInt(runnerSpeed);
                            int rest = Integer.parseInt(runnerRest);

                            ThreadRunner tr = new ThreadRunner( runnerName, rest, speed );
                            result.add(tr);
                        }
                        catch (NumberFormatException e)
                        {

                        }
                    }
                }
            }


        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return result;
    }

}
