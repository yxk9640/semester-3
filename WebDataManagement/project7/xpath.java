import javax.xml.xpath.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;

class XPATH {

    static void print ( Node e ) {
	if (e instanceof Text)
	    System.out.println(((Text) e).getData());
	else {
	    NodeList c = e.getChildNodes();
	    System.out.print("<"+e.getNodeName());
	    NamedNodeMap attributes = e.getAttributes();
	    for (int i = 0; i < attributes.getLength(); i++)
		System.out.print(" "+attributes.item(i).getNodeName() +"=\""+attributes.item(i).getNodeValue()+"\"");
	    System.out.print(">");
	    for (int k = 0; k < c.getLength(); k++)
		print(c.item(k));
	    System.out.print("</"+e.getNodeName()+">"+"\n");
	}
    }

    static void eval ( String query, String document ) throws Exception {
	XPathFactory xpathFactory = XPathFactory.newInstance();
	XPath xpath = xpathFactory.newXPath();
	InputSource inputSource = new InputSource(document); //InputSource
	NodeList result = (NodeList) xpath.evaluate(query,inputSource,XPathConstants.NODESET);
	System.out.println("XPath query: "+query);
	for (int i = 0; i < result.getLength(); i++)
	    print(result.item(i));
	System.out.println();
    }

    public static void main ( String[] args ) throws Exception {

	System.out.println("1. List of Titles of all \"MATH\" courses that are taught in room \"LIB 204\"");
	eval("//course[subj='MATH' and place[building = 'LIB' and room='204'] ]//title/text()","http://aiweb.cs.washington.edu/research/projects/xmltk/xmldata/data/courses/reed.xml");

	System.out.println("2. Instructor name who teaches \"MATH 412\" ");
	eval("//course[subj='MATH' and crse='412' ]//instructor/text()  ","http://aiweb.cs.washington.edu/research/projects/xmltk/xmldata/data/courses/reed.xml");

  	System.out.println("3. List of Titles of all courses taught by Instructor \"Wieting\" ");
	eval("//course[instructor='Wieting']//title/text()","http://aiweb.cs.washington.edu/research/projects/xmltk/xmldata/data/courses/reed.xml");


    }
}
