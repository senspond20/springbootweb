package com.sens.pond.utils.pdf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


public class PDFUtils {

    private final String PATH = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "data").toString();

    public PDFUtils(){ }

    public String readPDF(String fileName) {
        return readPDF(PATH, fileName);
    }
    public String readPDF(String filePath, String fileName) {
        Path path = Paths.get(filePath, fileName);
        File source = new File(path.toUri());
        PDDocument pdfDoc;
        String text ="";
        try {
            pdfDoc = PDDocument.load(source);
            text = new PDFTextStripper().getText(pdfDoc); 
        } catch (IOException e) {
           throw new RuntimeException("error");
        }
        return text;
    }


}
