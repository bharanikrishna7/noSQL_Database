package dbCore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import dataTypes.ArrayListDataType;
import dataTypes.BaseDataType;
import dataTypes.BooleanDataType;
import dataTypes.CharDataType;
import dataTypes.HashMapDataType;
import dataTypes.HashSetDataType;
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
 * 0.2a : 07/28/2017
 * - Added to save and load databases with collection type objects.
 * - Changed ElementToData. Now it uses two helper functions to retrieve
 *   data from XML Node.
 * - Tags are now added using GetAllTagValues.
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
	
	@SuppressWarnings("rawtypes")
	private static void AddArrayListData(Document dom, Element keyNode, BaseDataType value) {
		ArrayListDataType object = (ArrayListDataType) value;
		for(String dat : object.GetData()) {
			Element data = dom.createElement("data");
			data.appendChild(dom.createTextNode(StringEscapeUtils.escapeXml(dat)));
			keyNode.appendChild(data);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static void AddHashSetData(Document dom, Element keyNode, BaseDataType value) {
		HashSetDataType object = (HashSetDataType) value;
		for(String dat : object.GetData()) {
			Element data = dom.createElement("data");
			data.appendChild(dom.createTextNode(StringEscapeUtils.escapeXml(dat)));
			keyNode.appendChild(data);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static void AddHashMapData(Document dom, Element keyNode, BaseDataType value) {
		HashMapDataType object = (HashMapDataType) value;
		for(String datKey : object.GetData().keySet()) {
			Element data = dom.createElement("data");
			Attr attrKey = dom.createAttribute("datakey");
			attrKey.setValue(StringEscapeUtils.escapeXml(datKey));
			data.setAttributeNode(attrKey);
			data.appendChild(dom.createTextNode(StringEscapeUtils.escapeXml(object.GetData().get(datKey))));
			keyNode.appendChild(data);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static void AddData(Document dom, Element keyNode, DBEngine db, String key) {
		String type = db.GetRaw(key).GetType();
		BaseDataType value = db.GetRaw(key);
		if(!DataTypesConstants.COLLECTIONDATATYPES.contains(type)) {
			Element data = dom.createElement("data");
			data.appendChild(dom.createTextNode(StringEscapeUtils.escapeXml(value.DataToString())));
			keyNode.appendChild(data);
		} else {
			if(type.equals(DataTypesConstants.ARRAYLISTDATATYPE)) {
				AddArrayListData(dom, keyNode, value);
			} else if(type.equals(DataTypesConstants.HASHSETDATATYPE)) {
				AddHashSetData(dom, keyNode, value);
			} else if(type.equals(DataTypesConstants.HASHMAPDATATYPE)) {
				AddHashMapData(dom, keyNode, value);
			} else {
				logger.Log("Warning Primitive Data Type in Database. Not storing it.");
			}
		}
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
        	
        	AddData(dom, keyNode, db, key);
        	
        	Element timestamp = dom.createElement("timestamp");
        	timestamp.appendChild(dom.createTextNode(Long.toString(db.GetRaw(key).GetTimestamp())));
        	keyNode.appendChild(timestamp);
        	
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
				@SuppressWarnings("rawtypes")
				BaseDataType value = ElementToData(node);
				if(value == null) {
					logger.Log("Warning Data may be corrupted. Encountered an unidentified Data Type");
				} else {
					db.Insert(key, value);
				}
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
	
	private static String GetFirstTagValue(String sTag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    return nValue.getNodeValue();
	}
	
	private static ArrayList<String> GetAllTagValue(String sTag, Element eElement) {
	    NodeList nList = eElement.getElementsByTagName(sTag);
	    ArrayList<String> collection = new ArrayList<>();
	    for(int i = 0; i < nList.getLength(); i++) {
	    	Element object = (Element)nList.item(i);
	    	String value = object.getTextContent();
	    	collection.add(StringEscapeUtils.unescapeXml(value));
	    }
	    return collection;
	}
	
	private static HashMap<String, String> GetHashMapValues(Element eElement) {
		NodeList nList = eElement.getElementsByTagName("data");
		HashMap<String, String> map = new HashMap<>();
		for(int i = 0; i < nList.getLength(); i++) {
			Element object = (Element)nList.item(i);
			String key = StringEscapeUtils.unescapeXml(object.getAttribute("datakey"));
			String val = StringEscapeUtils.unescapeXml(object.getTextContent());
			map.put(key, val);
		}
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	private static BaseDataType SingleElementToData(String type, Element element) {
		BaseDataType object = null;
		//String type  = StringEscapeUtils.unescapeXml(element.getAttribute("type"));
		String value = StringEscapeUtils.unescapeXml(GetFirstTagValue("data", element));
		
		HashSet<String> tagList = new HashSet<>(GetAllTagValue("tag", element));
		
		if (type.equals(DataTypesConstants.INTDATATYPE))
			object = new IntDataType(Integer.parseInt(value), tagList);
		if (type.equals(DataTypesConstants.STRINGDATATYPE))
			object = new StringDataType(value, tagList);
		if (type.equals(DataTypesConstants.BOOLEANDATATYPE))
			object = new BooleanDataType(Boolean.parseBoolean(value), tagList);
		if (type.equals(DataTypesConstants.CHARDATATYPE))
			object = new CharDataType(value.charAt(0), tagList);
		
		return object;
	}
	
	@SuppressWarnings("rawtypes")
	private static BaseDataType CollectionElementToData(String type, Element element) {
		BaseDataType object = null;
		//String type  = StringEscapeUtils.unescapeXml(element.getAttribute("type"));
		
		HashSet<String> tagList = new HashSet<>(GetAllTagValue("tag", element));
		
		if (type.equals(DataTypesConstants.ARRAYLISTDATATYPE)) {
			ArrayList<String> collection = GetAllTagValue("data", element);
			object = new ArrayListDataType(collection, tagList);
		}
		if (type.equals(DataTypesConstants.HASHSETDATATYPE)) {
			HashSet<String> collection = new HashSet<>(GetAllTagValue("data", element));
			object = new HashSetDataType(collection, tagList);
		}
		if (type.equals(DataTypesConstants.HASHMAPDATATYPE)) {
			HashMap<String, String> collection = GetHashMapValues(element);
			object = new HashMapDataType(collection, tagList);
		}
		
		return object;
	}
	
	@SuppressWarnings("rawtypes")
	private static BaseDataType ElementToData(Element element) {
		String type  = StringEscapeUtils.unescapeXml(element.getAttribute("type"));
		
		if(!DataTypesConstants.COLLECTIONDATATYPES.contains(type)) {
			return SingleElementToData(type, element);
		} else {
			return CollectionElementToData(type, element);
		}
	}
}
