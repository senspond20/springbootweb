package com.sens.pond.tenserflow.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileInfoCSV extends Matrix {
    private String fileName;
    private String mimeType;

    public FileInfoCSV(){
        super();
    }
    public FileInfoCSV(String fileName, String mimeType){
        super();
        this.fileName = fileName;
        this.mimeType = mimeType;
    }
    public FileInfoCSV(String fileName, String mimeType, int maxRow, int maxCol, Object[][] data){
        super(maxRow, maxCol, data);
        this.fileName = fileName;
        this.mimeType = mimeType;
    }
}
