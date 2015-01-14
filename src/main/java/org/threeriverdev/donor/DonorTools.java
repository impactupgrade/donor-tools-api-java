package org.threeriverdev.donor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.threeriverdev.donor.model.Donation;
import org.threeriverdev.donor.model.Persona;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Donor tools API.
 * 
 * @author Oleg Gorobets
 *
 */
public class DonorTools {
	
	public static String username = "oleg.goro@gmail.com";
	public static String password = "3river213";
	
	private static Client client = ClientBuilder.newClient();
	
	private static WebTarget getTarget(String path) {
		
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
		
		WebTarget target = client
				.register(feature)
				.target("https://3riverdev.donortools.com")
				.path(path);
		
		return target;
	}
	
	public static Persona create(Persona persona) throws Exception {
		
		WebTarget target = getTarget("/people.xml");
		
		Document document = buildDocument("/persona_new.xml");
		setValue(document, "//company-name", persona.getCompanyName());
		setValue(document, "//first-name", persona.getNames().get(0).getFirstName());
		setValue(document, "//last-name", persona.getNames().get(0).getLastName());
		
		Entity<String> entity = Entity.entity(getString(document), MediaType.APPLICATION_XML_TYPE);
		// create persona
		Response resp = target.request(MediaType.APPLICATION_XML_TYPE).post(entity);
		
		return persona;
	}

	public static List<Persona> listPersonas() {
		List<Persona> people = new ArrayList<Persona>();
		
		WebTarget target = getTarget("/people.xml");
		Response resp = target.request(MediaType.APPLICATION_XML).get();
		
		String result = resp.readEntity(String.class);
		
		SAXBuilder builder1 = new SAXBuilder();
		try {
			org.jdom2.Document document = builder1.build(new StringReader(result));
			Element rootNode = document.getRootElement();
			List personas = rootNode.getChildren("persona");
			for (Object personaObj: personas) {
				Persona persona = new Persona();
				Element xmlPersona = (Element) personaObj;
				String companyName = xmlPersona.getChild("company-name").getValue();
				persona.setCompanyName(companyName);
				people.add(persona);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return people;
	}

	
	public static Donation create(Donation donation) {
		
		return donation;
	}
	
	private static XPathFactory xfactory = XPathFactory.newInstance();
	
	private static void setValue(Document document, String xpath, String value) {
		try {
			XPath xpathObj = xfactory.newXPath();
			Node node = (Node)xpathObj.evaluate(xpath, document, XPathConstants.NODE);
		    node.setTextContent(value);
		} catch (XPathExpressionException e) {
		    throw new RuntimeException(e);
		}
	}
	
	private static Document buildDocument(String file) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		return documentBuilder.parse(Test.class.getResourceAsStream(file));
	}
	
	private static String getString(Document doc) throws TransformerException {
		StringWriter writer = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	    transformer.transform(new DOMSource(doc), new StreamResult(writer));
	    return writer.getBuffer().toString().replaceAll("\n|\r", "");
	}
	
	private static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	    transformer.transform(new DOMSource(doc), 
	         new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	}
}
