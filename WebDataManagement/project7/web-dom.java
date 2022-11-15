import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.net.URL;

class DOMTest {
    static void print ( Node e ) {
	if (e instanceof Text)
	{
		System.out.print(( (Text) e).getData());
	}
	else {
	    NodeList c = e.getChildNodes();
	    System.out.print("<"+e.getNodeName()+">");
	    for (int k = 0; k < c.getLength(); k++)
			print(c.item(k));
	    System.out.print("</"+e.getNodeName()+">");
	}
    }
    public static void main ( String args[] ) throws Exception {
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = dbf.newDocumentBuilder();
//	Document doc = db.parse((new URL("https://isbndb.com/api/books.xml?access_key=*****&index1=full&results=prices&value1=elmasri")).openStream());
	Document doc = db.parse((new URL("http://aiweb.cs.washington.edu/research/projects/xmltk/xmldata/data/courses/reed.xml")).openStream());
	Node root = doc.getDocumentElement();
	print(root);
    }
}
