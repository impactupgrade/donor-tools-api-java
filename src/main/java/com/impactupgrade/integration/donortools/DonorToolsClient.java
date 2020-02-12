package com.impactupgrade.integration.donortools;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import com.impactupgrade.integration.donortools.model.Donation;
import com.impactupgrade.integration.donortools.model.Persona;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

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
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Donor Tools Java Client
 * 
 * @author Oleg Gorobets
 * @author Brett Meyer
 */
public class DonorToolsClient {
	
	private final Client client = ClientBuilder.newClient();

	private final String endpoint;

    /**
     * Constructor
     *
     * @param endpoint Typically "https://[USERNAME].donortools.com"
     * @param username Donor Tools username
     * @param password Donor Tools password
     */
	public DonorToolsClient(String endpoint, String username, String password) {
		this.endpoint = endpoint;

		HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.basic(username, password);
		client.register(authenticationFeature);
	}

	private WebTarget getTarget(String path) {
		return client
				.target(endpoint)
				.path(path);
	}
	
	public Integer create(Persona persona) throws Exception {
		
		WebTarget target = getTarget("/people.xml");
		
		Document document = buildDocument("/persona_new.xml");
		setValue(document, "//company-name", persona.getCompanyName());
		setValue(document, "//first-name", persona.getNames().get(0).getFirstName());
		setValue(document, "//last-name", persona.getNames().get(0).getLastName());
		
		if (persona.getEmailAddresses() != null && persona.getEmailAddresses().size() > 0) {
			setValue(document, "//email-addresses/email-address/email-address",
					persona.getEmailAddresses().get(0).getEmailAddress());
		}

		if (persona.getAddresses() != null && persona.getAddresses().size() > 0) {
			setValue(document, "//addresses/address/city",
					persona.getAddresses().get(0).getCity());
			setValue(document, "//addresses/address/country",
					persona.getAddresses().get(0).getCountry());
			setValue(document, "//addresses/address/postal-code",
					persona.getAddresses().get(0).getPostalCode());
			setValue(document, "//addresses/address/state",
					persona.getAddresses().get(0).getState());
			setValue(document, "//addresses/address/street-address",
					persona.getAddresses().get(0).getStreetAddress());
		}

	
		if (persona.getPhoneNumbers() != null && persona.getPhoneNumbers().size() > 0) {
			setValue(document, "//phone-numbers/phone-number/phone-number",
					persona.getPhoneNumbers().get(0).getPhoneNumber());
			setValue(document, "//phone-numbers/phone-number/extension",
					persona.getPhoneNumbers().get(0).getExtension());
		}

		
		Entity<String> entity = Entity.entity(getString(document), MediaType.APPLICATION_XML_TYPE);
		// create persona
		Response resp = target.request(MediaType.APPLICATION_XML_TYPE).post(entity);
		
		String result = resp.readEntity(String.class);

		Document doc = buildDocumentFromString(result);
		
		return Integer.valueOf(getValue(doc, "//id"));
		
	}

	public List<Persona> listPersonas() {
		List<Persona> people = new ArrayList<Persona>();
		
		WebTarget target = getTarget("/people.xml");
		Response resp = target.request(MediaType.APPLICATION_XML).get();
		
		String result = resp.readEntity(String.class);
		
		SAXBuilder builder1 = new SAXBuilder();
		try {
			org.jdom2.Document document = builder1.build(new StringReader(result));
			Element rootNode = document.getRootElement();
			List<?> personas = rootNode.getChildren("persona");
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

	
	public Integer create(Donation donation) throws Exception {
		
		WebTarget target = getTarget("/donations.xml");
		
		Document document = buildDocument("/donation_new.xml");
		
		setValue(document, "/donation/amount-in-cents", donation.getAmountInCents());
		setValue(document, "/donation/donation-type-id", donation.getDonationTypeId());
		setValue(document, "/donation/persona-id", donation.getPersonaId());
		setValue(document, "/donation/source-id", donation.getSourceId());
		setValue(document, "/donation/memo", donation.getMemo());

		for (int i = 0; i < donation.getSplits().size(); i++) {
			// Oh so hacky...
			if (i > 0) {
				cloneNode(document, "//splits/split[1]");
			}

			setValue(document, "//splits/split[" + (i+1) + "]/amount-in-cents",
					donation.getSplits().get(i).getAmountInCents());
			setValue(document, "//splits/split[" + (i+1) + "]/fund-id",
					donation.getSplits().get(i).getFundId());
			setValue(document, "//splits/split[" + (i+1) + "]/memo",
					donation.getSplits().get(i).getMemo());
		}
		
		String xml = getString(document);
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML_TYPE);
		
		// create donation
		Response resp = target.request(MediaType.APPLICATION_XML_TYPE).post(entity);
		
		String result = resp.readEntity(String.class);
		
		Document doc = buildDocumentFromString(result);
		
		return Integer.valueOf(getValue(doc, "//id"));
	}
	
	private static XPathFactory xfactory = XPathFactory.newInstance();
	
	private static void setValue(Document document, String xpath, Object value) {
		try {
			XPath xpathObj = xfactory.newXPath();
			Node node = (Node)xpathObj.evaluate(xpath, document, XPathConstants.NODE);
			if (value != null)
				node.setTextContent(value.toString());
		} catch (XPathExpressionException e) {
		    throw new RuntimeException(e);
		}
	}
	
	private static String getValue(Document document, String xpath) {
		try {
			XPath xpathObj = xfactory.newXPath();
			Node node = (Node)xpathObj.evaluate(xpath, document, XPathConstants.NODE);
			return node.getTextContent();
		} catch (XPathExpressionException e) {
		    throw new RuntimeException(e);
		}
	}

	private static void cloneNode(Document document, String xpath) {
		try {
			XPath xpathObj = xfactory.newXPath();
			Node node = (Node)xpathObj.evaluate(xpath, document, XPathConstants.NODE);
			Node clone = node.cloneNode(true);
			node.getParentNode().appendChild(clone);
		} catch (XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Document buildDocument(String file) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		return documentBuilder.parse(DonorToolsClient.class.getResourceAsStream(file));
	}

	private static Document buildDocumentFromString(String xml) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		return documentBuilder.parse( IOUtils.toInputStream(xml) );
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
	
//	private static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
//	    TransformerFactory tf = TransformerFactory.newInstance();
//	    Transformer transformer = tf.newTransformer();
//	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
//	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
//
//	    transformer.transform(new DOMSource(doc), 
//	         new StreamResult(new OutputStreamWriter(out, "UTF-8")));
//	}
}
