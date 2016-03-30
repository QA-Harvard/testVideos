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

        while ((line = reader.readLine()) != null) {
            // process the line- just appending to the buffer for now.

            //removing bomb:
            txtContentBuffer.append(line);

        }

        return txtContentBuffer;
    }
}
