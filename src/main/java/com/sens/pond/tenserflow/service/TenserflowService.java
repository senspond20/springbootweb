package com.sens.pond.tenserflow.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import javax.xml.bind.ValidationException;

import com.sens.pond.tenserflow.model.FileInfoCSV;

public interface TenserflowService {
 
    public String getTenserflowVersion();

    public String getAppDataPath();

    // public FileInfoCSV loadCSV(String fileName, boolean isPrint) throws ValidationException, IOException;

    public FileInfoCSV loadCSV(String path, String fileName, String rgx, boolean isHeader) throws ValidationException, IOException;

    public FileInfoCSV loadCSV(String path, String fileName, String rgx) throws ValidationException, IOException;

    public FileInfoCSV loadCSV(String fileName, boolean isHeader) throws ValidationException, IOException;

    public FileInfoCSV loadCSV(String fileName) throws ValidationException, IOException;

    // public Object[][] transMatrix(Object[][] obj);
}
