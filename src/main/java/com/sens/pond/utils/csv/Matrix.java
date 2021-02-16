package com.sens.pond.utils.csv;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author senshig 2021-02-17
 **/
@Getter
@Setter
@ToString
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