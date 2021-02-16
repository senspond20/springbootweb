package com.sens.pond.tenserflow.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoCSV {
    private String fileName;
    private String mimeType;
    private Long fileSize;
    private float[][] dataArray;
}
