package xpath;

import javax.xml.xpath.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;

class XPATH {

    static void print ( Node e ) {
	if (e instanceof Text)
	    System.out.print(((Text) e).getData());
	else {
	    NodeList c = e.getChildNodes();
	    System.out.print("<"+e.getNodeName());
	    NamedNodeMap attributes = e.getAttributes();
	    for (int i = 0; i < attributes.getLength(); i++)
		System.out.print(" "+attributes.item(i).getNodeName() +"=\""+attributes.item(i).getNodeValue()+"\"");
	    System.out.print(">");
	    for (int k = 0; k < c.getLength(); k++)
		print(c.item(k));
	    System.out.print("</"+e.getNodeName()+">");
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
//	eval("//gradstudent[name/lastname='Galanis']/name","cs.xml");
		//gives XML of suj = math and LIB-204 --> title
//	eval("//course[ subj='MATH' ]//place[building = 'LIB' and room='204']  ","http://aiweb.cs.washington.edu/research/projects/xmltk/xmldata/data/courses/reed.xml");

//		XML name who teaches MATH 412 --> instructor name
//	eval("//course[subj='MATH' and crse='412' ]  ","http://aiweb.cs.washington.edu/research/projects/xmltk/xmldata/data/courses/reed.xml");

//   XML Instructor = Wieting --> title
	eval("//course[instructor='Wieting']","http://aiweb.cs.washington.edu/research/projects/xmltk/xmldata/data/courses/reed.xml");


    }
}
