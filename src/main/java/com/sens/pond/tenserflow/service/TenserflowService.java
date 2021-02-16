package com.sens.pond.tenserflow.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import javax.xml.bind.ValidationException;

import com.sens.pond.tenserflow.model.FileInfoCSV;

public interface TenserflowService {
 
    public String getTenserflowVersion();

    public Path getAppDataPath();

    public FileInfoCSV loadCSV(String fileName) throws ValidationException, FileNotFoundException;
}
