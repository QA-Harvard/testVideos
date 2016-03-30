package com.hbp.testvideos;

import java.io.IOException;

/**
 * Created by jun.chen on 3/9/16.
 */
public class VideoTranscriptParser {


    private static TxtReader txtReader = new TxtReader();
    private static final String transcriptFileParentFolder = "/Users/jun.chen/Videos/";

    public String parserVideoTranscriptFile(Video videoObj, String locale) {
        StringBuffer videoTranscriptContent = new StringBuffer();
        String transcriptFileName = videoObj.getVideoTranscriptFileName();
        String transcriptFileFullPath ="";

        if(transcriptFileName != null && !transcriptFileName.equalsIgnoreCase("NA") ) {
            transcriptFileFullPath = transcriptFileParentFolder + locale + "_transcripts/" + videoObj.getVideoTranscriptFileName();

            try {
                videoTranscriptContent = txtReader.readTxtContent(transcriptFileFullPath, locale);

            } catch (IOException ie) {
                ie.printStackTrace();
                System.out.println("KalturaId: " + videoObj.getKalturaId() + " Video Title: " + "'" + videoObj.getVideoTitle() + "'" + " File: " + transcriptFileFullPath);
            }
        }else {
            System.out.println("Transcript file name is NA: " + "KalturaId: " + videoObj.getKalturaId() + " Video Titl: " + videoObj.getVideoTitle() + " File: " + transcriptFileFullPath);
        }
        return videoTranscriptContent.toString();
    }
}
