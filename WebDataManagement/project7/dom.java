
// Print titles of all MATH courses that are taught in room LIB 204
// Math courses --> course:[subj::'Math']
// to get Room:LIB-204 -->   course>place[building,room]
/*
course
     sub :    MATH
     titles
     place
        building
        room LIB 204
 */
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.net.URL;

public class dom {
    static void getNodes( Node e){
//        if (e instanceof Text){
//            getName(e);
//        }
            NodeList c = e.getChildNodes();
            for (int k = 0; k < c.getLength(); k++){
                Node childNode = c.item(k);
                if (childNode.getNodeName() == "subj"){
                    if (childNode.getFirstChild().getTextContent().equals("MATH"))
                    {
                        NodeList place = c.item(19).getChildNodes();
                        if ( place.item(1).getTextContent().equals("LIB") && place.item(3).getTextContent().equals("204"))
                         System.out.println(
                                "\t" + c.item(9).getFirstChild().getTextContent()
//                                 " Building " + place.item(1).getTextContent() +
//                                 " Room " + place.item(3).getTextContent()

                         );
                    }
                }
                getNodes(c.item(k));
            }
    }

//    static void getName(Node e){
//        System.out.print(((Text) e).getData());
//    }


    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuil = dbf.newDocumentBuilder();
        Document doc = docBuil.parse((new URL("http://aiweb.cs.washington.edu/research/projects/xmltk/xmldata/data/courses/reed.xml")).openStream());
        Node root = doc.getDocumentElement();
        System.out.println("List of titles of Math courses taught in LIB 204: ");
        getNodes(root);

    }
}
