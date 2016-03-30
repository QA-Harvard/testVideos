package com.hbp.testvideos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by jun.chen on 3/17/16.
 */
public class TxtReader {

    public StringBuffer readTxtContent(String fullFilePath, String locale) throws IOException {

        StringBuffer txtContentBuffer = new StringBuffer();

        BufferedReader reader;
        FileInputStream fis = new FileInputStream(fullFilePath);
        reader = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
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
            txtContentBuffer.append(line);

            lineCount++;
        }

        //Add last paragraph tag before returning.
        txtContentBuffer = endParagraph(txtContentBuffer);

        return txtContentBuffer;
    }

    public StringBuffer startParagraph(StringBuffer txtContentBuffer, Boolean firstParagraph){

        if(firstParagraph){
            txtContentBuffer.append("<P>");
        }else{
            txtContentBuffer.append("</P><P>");
        }
        return txtContentBuffer;

    }

    public StringBuffer endParagraph(StringBuffer txtContentBuffer){
        txtContentBuffer.append("</P>");
        return txtContentBuffer;
    }
}
