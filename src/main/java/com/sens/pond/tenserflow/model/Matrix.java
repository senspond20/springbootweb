package com.sens.pond.tenserflow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Matrix {
    private int maxRow;
    private int maxCol;
    private Object[][] data;
    
    public Matrix(){}

    public Matrix(int maxRow, int maxCol, Object[][] data){
        this.maxRow = maxRow;
        this.maxCol = maxCol;
        this.data = data;
    }
}

