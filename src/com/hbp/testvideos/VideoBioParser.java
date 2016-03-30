package com.hbp.testvideos;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

/**
 * Created by jun.chen on 3/9/16.
 */
public class VideoBioParser {


    private static DocReader docReader = new DocReader();

    private static final String bioFileParentFolder = "/Users/jun.chen/Videos/";

    public String parseBioContent(Video videoObj, String locale){

        String bioFileFullPath = "";
        StringBuffer bioContent = new StringBuffer();

        try {
            String bioFileName = videoObj.getVideoBioFileName();


            bioFileFullPath = bioFileParentFolder + locale + "_bios/" + bioFileName;

            //Read docx content using DocReader.
            //System.out.println("KalturaId: " + videoObj.getKalturaId() + " Bio: " + videoObj.getVideoBio() + " File: " + bioFileFullPath);

            if(bioFileName != null && !bioFileName.equalsIgnoreCase("NA")) {
                bioContent = docReader.readMyDocument(bioFileFullPath);
            }else{
                if(videoObj.getKalturaId() != null && !videoObj.getKalturaId().equalsIgnoreCase("")) {
                    System.out.println("Bio file name is 'NA': " + "KalturaId: " + "'" + videoObj.getKalturaId()
                            + "'" + " Video Title: " +"'"+videoObj.getVideoTitle() + "'"+  " Bio: " + "'" +videoObj.getVideoBio() +"'"+ " File: " +"'" + bioFileFullPath + "'");
                }else{
                    System.out.println("Playlist Bio file name is 'NA': " + " Playlist:" + "'"+videoObj.getPlaylistFileName() +"'"+ " File: " + bioFileFullPath);
                }
            }

       /// }catch (InvalidFormatException fe){
        //    System.out.println("KalturaId: " + videoObj.getKalturaId() + " Bio: " + videoObj.getVideoBio() + " File: " + bioFileFullPath);
        //    fe.printStackTrace();
        }
        catch (IOException ie){
            System.out.println("KalturaId: " + videoObj.getKalturaId() + " Bio: " + videoObj.getVideoBio() + " File: " + bioFileFullPath);
           ie.printStackTrace();
        }

        return bioContent.toString();
    }
}
