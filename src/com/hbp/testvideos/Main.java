package com.hbp.testvideos;

import org.xml.sax.SAXException;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Main {

    private static VideoTranscriptFileLocator videoTranscriptFileLocator = VideoTranscriptFileLocator.getVideoTranscriptFileLocatorInstance();
    private static VideoXmlFileBuilder videoXmlFileBuilder = new VideoXmlFileBuilder();

    public static void main(String[] args) {
	// write your code here
    generateAllVideoXmls();
    generateAllVideoPlaylistXmls();

    }

    public static void generateAllVideoXmls(){

        HashMap <String, Video>kalturaIdMasterListMap = videoTranscriptFileLocator.getKalturaIdMasterListMap();

        try {

            for (Map.Entry<String, Video> entry : kalturaIdMasterListMap.entrySet()) {

                String kalturaId = entry.getKey();
                Video videoObj = entry.getValue();

                videoXmlFileBuilder.generateVideoXmlForKalturaId(videoObj, "es_LA");
                videoXmlFileBuilder.generateVideoXmlForKalturaId(videoObj, "pt_BR");
                videoXmlFileBuilder.generateVideoXmlForKalturaId(videoObj, "zh_CN");

            }
        }catch(IOException io){
            io.printStackTrace();
        }


    }

    public static void generateAllVideoPlaylistXmls(){

        HashMap <String, Video> playlistMasterListMap  = videoTranscriptFileLocator.getPlaylistMasterListMap();

        try {
            for (Map.Entry<String, Video> entry : playlistMasterListMap.entrySet()) {

                String videoTitle = entry.getKey();
                Video videoObj = entry.getValue();


                videoXmlFileBuilder.generateVideoPlaylistXml(videoObj, "es_LA");
                videoXmlFileBuilder.generateVideoPlaylistXml(videoObj, "pt_BR");
                videoXmlFileBuilder.generateVideoPlaylistXml(videoObj, "zh_CN");


            }
        }catch(SAXException sxe){
            sxe.printStackTrace();
        }catch(XPathExpressionException xe){
            xe.printStackTrace();
        }
        catch(IOException io){
            io.printStackTrace();
        }


    }




}
