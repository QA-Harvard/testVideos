package com.hbp.testvideos;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
/**
 * Created by jun.chen on 3/9/16.
 */
public class VideoXmlFileBuilder {


    public VideoXmlFileBuilder(){

    }


    private static VideoTranscriptFileLocator videoTranscriptFileLocator = VideoTranscriptFileLocator.getVideoTranscriptFileLocatorInstance();
    private static VideoTranscriptParser videoTranscriptParser = new VideoTranscriptParser();
    private static VideoBioParser videoBioParser = new VideoBioParser();
    private static HashMap <String, String> videoPlaylistDescriptions_zhcn = videoTranscriptFileLocator.getVideoPlaylistDescriptions_zhcn();
    private static HashMap <String, String> videoPlaylistDescriptions_esla = videoTranscriptFileLocator.getVideoPlaylistDescriptions_esla();
    private static HashMap <String, String> videoPlaylistDescriptions_ptbr = videoTranscriptFileLocator.getVideoPlaylistDescriptions_ptbr();

    public static final String videoPlaylistFolder = "/Users/jun.chen/Videos/playlist/";
    public static final String videoXmlFolder = "/Users/jun.chen/Videos/video/";
    public static final String videoPlaylistXmlFolder = "/Users/jun.chen/Videos/video_playlist/";


    //Method to create the video playlist xml file.
    public void generateVideoPlaylistXml(Video videoObj, String locale) throws IOException, SAXException, XPathExpressionException {



        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        //1.Read in the English playlist xml file.
        File playlistFile = new File(videoPlaylistFolder+videoObj.getPlaylistFileName());
        Document doc = docBuilder.parse(playlistFile);

        //2. Parse the video bio file to get the bio content.
        String videoDescription = getVideoDescriptionContent(videoObj.getVideoTitle(), locale);
        String bioContent = videoBioParser.parseBioContent(videoObj, locale);

        //3. Update the document with bio content.
        XPath xpath = XPathFactory.newInstance().newXPath();

        NodeList description_nodes = (NodeList) xpath.evaluate("//*[local-name()='description']", doc,
                    XPathConstants.NODESET);

        for (int idx = 0; idx < description_nodes.getLength(); idx++) {
            description_nodes.item(idx).setTextContent(videoDescription);
        }

        NodeList biography_nodes = (NodeList) xpath.evaluate("//*[local-name()='biography']", doc,
                XPathConstants.NODESET);

        for (int idx = 0; idx < biography_nodes.getLength(); idx++) {
            biography_nodes.item(idx).setTextContent(bioContent);
        }

        //4. Send to writer.
        String xmlFileName = videoObj.getPlaylistFileName();
        String outputXmlPath = videoPlaylistXmlFolder  + locale + "/" + xmlFileName;

        File xmlFile = new File(outputXmlPath);

        writeDocumentToFile(doc, xmlFile);


    }


    //Method to create the video xml file.
    public void generateVideoXmlForKalturaId(Video videoObj, String locale) throws IOException{

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document doc = docBuilder.newDocument();

        //Root asset:video element...
        Element rootElement = doc.createElement("asset:video");


    /*Add the following attributes.
        xmlns:alf="http://www.alfresco.org"
        xmlns:asset="http://cl.hbsp.org/asset/content"
        xmlns:chiba="http://chiba.sourceforge.net/xforms"
        xmlns:ev="http://www.w3.org/2001/xml-events"
        xmlns:ns1="http://www.w3.org/2001/XMLSchema"
        xmlns:xf="http://www.w3.org/2002/xforms"
        xmlns:xhtml="http://www.w3.org/1999/xhtml"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    */

        rootElement.setAttribute("xmlns:alf", "http://www.alfresco.org");
        rootElement.setAttribute("xmlns:asset", "http://cl.hbsp.org/asset/content");
        rootElement.setAttribute("xmlns:chiba", "http://chiba.sourceforge.net/xforms");
        rootElement.setAttribute("xmlns:ev", "http://www.w3.org/2001/xml-events");
        rootElement.setAttribute("xmlns:ns1", "http://www.w3.org/2001/XMLSchema");
        rootElement.setAttribute("xmlns:xf", "http://www.w3.org/2002/xforms");
        rootElement.setAttribute("xmlns:xhtml", "http://www.w3.org/1999/xhtml");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");


        doc.appendChild(rootElement);

        //<asset:name> element..
        Element nameElement = doc.createElement("asset:name");
        String assetName = videoObj.getKalturaId();
        nameElement.appendChild(doc.createTextNode(assetName));
        rootElement.appendChild(nameElement);


        //<asset:learningInstruction xsi:nil="false"><p>video</p></asset:learningInstruction>
        Element learningElement = doc.createElement("asset:learningInstruction");

        /*Add the following attributes.
         xsi:nil="false"
        */
        learningElement.setAttribute("xsi:nil", "false");
        learningElement.appendChild(doc.createTextNode("<p>video</p>"));
        rootElement.appendChild(learningElement);

        //1. Parse the video transcript file to get the transcript content.
        String transcriptContent = videoTranscriptParser.parserVideoTranscriptFile(videoObj, locale);

        Element transcriptElement = doc.createElement("asset:transcript");
        transcriptElement.appendChild(doc.createTextNode(transcriptContent));
        rootElement.appendChild(transcriptElement);


        // 2. Parse the video bio file to get the bio content.
        String bioContent = videoBioParser.parseBioContent(videoObj, locale);
        //<asset:biography xsi:nil="false">
        Element biographyElement = doc.createElement("asset:biography");

        /*Add the following attributes.
         xsi:nil="false"
        */
        biographyElement.setAttribute("xsi:nil","false");

        biographyElement.appendChild(doc.createTextNode(bioContent));
        rootElement.appendChild(biographyElement);

        /*
        Add to xml dom.
        Output to xml file based on kaltura id and locale.
        */

        String xmlFileName = videoObj.getKalturaId()+".xml";
        String outputXmlPath = videoXmlFolder + locale + "/" + xmlFileName;

        File xmlFile = new File(outputXmlPath);

        writeDocumentToFile(doc, xmlFile);


    }

    private String getAssetName(Video videoObj){
        String assetName = null;
        String[] kalturaId_parts = videoObj.getKalturaId().split(".");
        assetName = kalturaId_parts[0].trim();

        return assetName;
    }

    private String getVideoDescriptionContent(String videoTitle, String locale){
        String videoDescription;

        if(locale.equalsIgnoreCase("zh_CN")){
            videoDescription =  videoPlaylistDescriptions_zhcn.get(videoTitle);

        }else if (locale.equalsIgnoreCase("pt_BR")){
            videoDescription =  videoPlaylistDescriptions_ptbr.get(videoTitle);
        }else{ //es_LA
            videoDescription =  videoPlaylistDescriptions_esla.get(videoTitle);
        }

        return videoDescription;
    }

    private static void writeDocumentToFile(Document document, File file) {

        // Make a transformer factory to create the Transformer
        TransformerFactory tFactory = TransformerFactory.newInstance();

        // Make the Transformer
        Transformer transformer = null;
        try {
            transformer = tFactory.newTransformer();

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        // Mark the document as a DOM (XML) source
        DOMSource source = new DOMSource(document);

        // Say where we want the XML to go
        StreamResult result = new StreamResult(file);

        // Write the XML to file
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
