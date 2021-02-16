package com.sens.pond.utils.csv;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author senshig 2021-02-17
 **/
@Getter
@Setter
public class FileInfoCSV extends Matrix {
    private String fileName;
    private String mimeType;
    private Object[] header;

    public FileInfoCSV(){
        super();
    }
    public FileInfoCSV(String fileName, String mimeType){
        super();
        this.fileName = fileName;
        this.mimeType = mimeType;
    }
    @Builder
    public FileInfoCSV(String fileName, String mimeType, int maxRow, int maxCol, Object[] header, Object[][] data){
        super(maxRow, maxCol, data);
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.header = header;
    }
}
