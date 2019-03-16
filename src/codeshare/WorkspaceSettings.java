/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeshare;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author voleti
 */
public class WorkspaceSettings{
      private final String filepath = getClass().getResource("/resources/settings.xml").getPath();
      private String workspace ;
      private String attribute;
      private File xmlFile;
      private DocumentBuilderFactory dbFactory;
      private DocumentBuilder dBuilder;
      private Document doc;
      private Node node;
      private Element emp;
      private NodeList nodeList ;
      private Node node1;
      private TransformerFactory transformerFactory ;
      private Transformer transformer ;
      private DOMSource source ;
      private StreamResult result;
    public WorkspaceSettings(String workspace,String attribute) {
        try{
        this.workspace = workspace;
        this.attribute = attribute;
        xmlFile = new File(filepath);
        dbFactory = DocumentBuilderFactory.newInstance();
        transformerFactory = TransformerFactory.newInstance();
        dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
       transformer = transformerFactory.newTransformer();
        
        }catch(org.xml.sax.SAXException |ParserConfigurationException | IOException| TransformerConfigurationException e){
             
            }
            
        }
    
    
    public String getSettings(){
       node = doc.getElementsByTagName(workspace).item(0);
       emp = (Element) node;
       nodeList = emp.getElementsByTagName(attribute).item(0).getChildNodes();
        node1 = (Node) nodeList.item(0);
       return node1.getNodeValue();
    }
    
    public void setSettings(String value) {
        node1.setNodeValue(value);
	source = new DOMSource(doc);
        result = new StreamResult(new File(filepath));
          try {
              transformer.transform(source, result);
          } catch (TransformerException ex) {
              
          }
       
    }
    public void temporarySettings(String value){
        node.setNodeValue(value);
    }
      
}
