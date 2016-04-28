package com.hbp.testvideos;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.util.List;

/**
 * Created by jun.chen on 3/10/16.
 */
public class DocReader {

    public StringBuffer readMyDocument(String fileName) throws IOException{

        StringBuffer paragraphSB = new StringBuffer();
//try {

    FileInputStream fis = new FileInputStream(fileName);

    XWPFDocument document = new XWPFDocument(fis);

    List<XWPFParagraph> paragraphs = document.getParagraphs();


    for (XWPFParagraph para : paragraphs) {
        paragraphSB.append("<p><span>");
        paragraphSB.append(para.getText());
        paragraphSB.append("</span></p>");

    }
    fis.close();
//}catch (IOException ie) {

    //ie.printStackTrace();
//}
        return paragraphSB;
    }

}

