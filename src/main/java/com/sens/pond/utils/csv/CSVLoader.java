package com.sens.pond.utils.csv;

import java.io.IOException;

import javax.xml.bind.ValidationException;

/**
 * @author senshig 2021-02-17
 */

public interface CSVLoader {
    
    public String getAppDataPath();

    public FileInfoCSV loadCSV(String fileName) throws ValidationException, IOException;

    public FileInfoCSV loadCSV(String path, String fileName, String rgx, boolean isHeader) throws ValidationException, IOException;

    public FileInfoCSV loadCSV(String path, String fileName, String rgx) throws ValidationException, IOException;

    public FileInfoCSV loadCSV(String fileName, boolean isHeader) throws ValidationException, IOException;

}
