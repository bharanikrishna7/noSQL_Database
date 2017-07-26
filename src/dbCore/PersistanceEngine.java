package dbCore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import constants.DataTypesConstants;
import dataTypes.BaseDataType;
import dataTypes.BooleanDataType;
import dataTypes.IntDataType;
import dataTypes.StringDataType;
import utilities.DateHelper;

/**
 * Class Which Enables Writing and Reading the Database 
 * Contents From XML File.
 * 
 * 
 * Dependencies
 * ------------
 * DBEngine, dataTypes.BaseDataType, dataTypes.BooleanDataType, 
 * dataTypes.IntDataType, dataTypes.StringDataType, Logger, 
 * utilities.StringHelper
 * 
 * 
 * Version
 * -------
 * 0.1a : 07/24/2017
 * - Initial Release.
 * 
 * 0.1b : 07/25/2017
 * - Modified SaveToXML & LoadFromXML. XML now doesnot store
 *   taglist. This will help to keep XML file small. 
 *   
 * - Now when user loads DBEngine from XML file first the _dbEngine
 *   is populated. Tag indexing is done automatically.
 *   
 * 0.1c : 07/25/2017
 * - Logs operational information to log file using logger. Currently
 *   all exceptions and successful SaveToXML & LoadFromXML operations 
 *   are logged into log file.
 * 
 * @author Venkata Bharani Krishna, Chekuri
 *
 */
@SuppressWarnings("deprecation")
public class PersistanceEngine {
	static Logger logger = new Logger();
	
	public PersistanceEngine() {
		// do nothing
	}
	
	/* Returns Timestamp */
	public static long SaveToXML(DBEngine db, String file) {
		long lastmodified = -1;
		try {
			Document dom = DBToDocument(db);
	        Transformer tr = CreateXMLTransformer();
            File xmlFile = new File(file);
           	xmlFile.createNewFile();
			WriteXmlFile(xmlFile, dom, tr);
			
            lastmodified = DateHelper.GetCurrentTimestamp();
            logger.Log("Successfully Saved Database Contents to File : \"" + xmlFile.getAbsolutePath() + "\"");
            // System.out.println("[Logger] : Successfully Saved Database Contents to File : \"" + xmlFile.getAbsolutePath() + "\"");
		  } catch (ParserConfigurationException pce) {
			  logger.Log("Error while trying to instantiate DocumentBuilder");
	          // System.out.println("[ERR03] : Error while trying to instantiate DocumentBuilder");
		  } catch (TransformerException tfe) {
			  logger.Log("Error while trying to instantiate Transformer");
			  // System.out.println("[ERR04] : Error while trying to instantiate Transformer");;
		  } catch (IOException e) {
			  logger.Log("I/O Error while trying to write to File");
			  // System.out.println("[ERR05] : I/O Error while trying to write to File");
		  }
		return lastmodified;
	}
	
	public static long SaveToXML(DBEngine db) {
		return SaveToXML(db, db.GetDBName() + db.GetOwner() + ".xml");
	}
	
	private static Document DBToDocument(DBEngine db) throws ParserConfigurationException {
		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        dom = docBuilder.newDocument();
     
        // create the root element
        Element root = dom.createElement(StringEscapeUtils.escapeXml(db.GetOwner()));
        for(String key : db.KeySet()) {
        	Element keyNode = dom.createElement("key");
        	keyNode.appendChild(dom.createTextNode(StringEscapeUtils.escapeXml(key)));
        	
        	Attr type = dom.createAttribute("type");
        	type.setValue(StringEscapeUtils.escapeXml(db.GetRaw(key).GetType()));
        	keyNode.setAttributeNode(type);
        	
        	Element data = dom.createElement("data");
        	data.appendChild(dom.createTextNode(StringEscapeUtils.escapeXml(db.GetRaw(key).DataToString())));
        	keyNode.appendChild(data);
        	
        	@SuppressWarnings("unchecked")
			HashSet<String> tags = db.GetRaw(key).GetTags();
        	for(String tag : tags) {
        		Element tagNode = dom.createElement("tag");
        		tagNode.appendChild(dom.createTextNode(StringEscapeUtils.escapeXml(tag)));
        		keyNode.appendChild(tagNode);
        	}
        	
        	root.appendChild(keyNode);
        }
        
        dom.appendChild(root);
        
        return dom;
	}
	
	private static Transformer CreateXMLTransformer() throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
        return transformer;
	}
	
	private static void WriteXmlFile(File xmlFile, Document document, Transformer transformer) 
			throws FileNotFoundException, TransformerException {
        StreamResult result = new StreamResult(new PrintWriter(
                new FileOutputStream(xmlFile, false)));
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
    }
	
	public static DBEngine LoadFromXML(String file) throws FileNotFoundException {
		DBEngine db = null;
		File xmlFile = new File(file);
		if(!xmlFile.exists())
			throw new FileNotFoundException();
		Document dom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = dbf.newDocumentBuilder();
			dom = docBuilder.parse(xmlFile);
			dom.getDocumentElement().normalize();
			db = new DBEngine(dom.getDocumentElement().getNodeName());
			
			NodeList nodes = dom.getDocumentElement().getChildNodes();
			for(int i = 0; i < nodes.getLength(); i++) {
				Element node = (Element)nodes.item(i);
				String key   = StringEscapeUtils.unescapeXml(node.getChildNodes().item(0).getTextContent());
				db.Insert(key, ElementToData(node));
			}
			
			logger.Log("Successfully Loaded Database Contents From File : \"" + xmlFile.getAbsolutePath() + "\"");
			//  System.out.println("[Logger] : Successfully Loaded Database From File : \"" + file + "\"");
		} catch (ParserConfigurationException pce) {
			logger.Log("Error while trying to instantiate DocumentBuilder");
			//	System.out.println("[ERR03] : Error while trying to instantiate DocumentBuilder");
		} catch (SAXException | IOException ioe) {
			logger.Log("I/O Error while trying to write to File : \"" + file + "\"");
			//  System.out.println("[ERR05] : I/O Error while trying to write to File");
		}
		return db;
	}
	
	private static String GetTagValue(String sTag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    return nValue.getNodeValue();
	}
	
	@SuppressWarnings("rawtypes")
	private static BaseDataType ElementToData(Element element) {
		BaseDataType object = null;
		String type  = StringEscapeUtils.unescapeXml(element.getAttribute("type"));
		String value = StringEscapeUtils.unescapeXml(GetTagValue("data", element));
		
		NodeList tags = element.getElementsByTagName("tag");
		HashSet<String> tagList = new HashSet<>();
		for(int i = 0; i < tags.getLength(); i++) {
			Element tag = (Element)tags.item(i);
			String tagValue = tag.getTextContent();
			tagList.add(tagValue);
		}
		
		if (type.equals(DataTypesConstants.INTDATATYPE))
			object = new IntDataType(Integer.parseInt(value), tagList);
		if (type.equals(DataTypesConstants.STRINGDATATYPE))
			object = new StringDataType(value, tagList);
		if (type.equals(DataTypesConstants.BOOLEANDATATYPE))
			object = new BooleanDataType(Boolean.parseBoolean(value), tagList);
		
		return object;
	}
}
