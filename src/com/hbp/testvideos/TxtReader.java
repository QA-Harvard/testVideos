package com.hbp.testvideos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by jun.chen on 3/17/16.
 */
public class TxtReader {

    private static ArrayList<String> spanishIsoTranscriptList = new ArrayList<String>();
    public static final String mapping_folder= "/Users/jun.chen/Videos/mapping/";
    public static final String spanish_iso_list_file = "spanish_iso_transcripts.txt";

    static {
        try {
            FileInputStream fis = new FileInputStream(mapping_folder + spanish_iso_list_file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;

            while ((line = reader.readLine()) != null) {

                spanishIsoTranscriptList.add(line);
            }

        } catch (IOException io) {
            io.printStackTrace();

        }
    }


    public TxtReader(){

    }

    public StringBuffer readTxtContent(String fullFilePath, String locale, String KalturaId) throws IOException {

        StringBuffer txtContentBuffer = new StringBuffer();

        BufferedReader reader;
        FileInputStream fis = new FileInputStream(fullFilePath);
        if(locale.equalsIgnoreCase("es_LA") && spanishIsoTranscriptList.contains(KalturaId)){
            reader = new BufferedReader(new InputStreamReader(fis, Charset.forName("ISO-8859-1")));
        }else{
            reader = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
        }
        String line;
        int lineCount =1;
        while ((line = reader.readLine()) != null) {

            // process the line- just appending to the buffer for now.
            if(lineCount == 1 ){
                txtContentBuffer =  startParagraph(txtContentBuffer, true);
            }else if(line.startsWith("\t") && lineCount > 1){
                txtContentBuffer =  startParagraph(txtContentBuffer, false);
            }

            //Might need to removing bomb in the future: but ok for now.
            //Strip out tab so it would not fxck up JSON, Jackson.
            line = line.replaceAll("\t", "");
            txtContentBuffer.append(line);

            lineCount++;
        }

        //Add last paragraph tag before returning.
        txtContentBuffer = endParagraph(txtContentBuffer);

        return txtContentBuffer;
    }

    public StringBuffer startParagraph(StringBuffer txtContentBuffer, Boolean firstParagraph){

        if(firstParagraph){
            txtContentBuffer.append("<p><span>");
        }else{
            txtContentBuffer.append("</span></p><p><span>");
        }
        return txtContentBuffer;

    }

    public StringBuffer endParagraph(StringBuffer txtContentBuffer){
        txtContentBuffer.append("</span></p>");
        return txtContentBuffer;
    }
}
