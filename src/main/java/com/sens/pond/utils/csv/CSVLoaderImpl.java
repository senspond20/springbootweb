package com.sens.pond.utils.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.ValidationException;
import com.sens.pond.utils.csv.FileInfoCSV.FileInfoCSVBuilder;

import org.springframework.stereotype.Component;

/**
 * @author senshig 2021-02-17
 **/
@Component
public class CSVLoaderImpl implements CSVLoader {

    /**
     * @return Stromg : 현재 Application 작업 디렉토리의 classpath:/resource/data 경로를 가져온다.
     **/
    @Override
    public String getAppDataPath() {
        return Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "data").toString();
    }

    /**
     * @param fileName : 파일 이름
     * @return FileInfoCSV
     **/
    @Override
    public FileInfoCSV loadCSV(String fileName) throws ValidationException, IOException {
        return loadCSV(getAppDataPath(), fileName, ",", false);
    }

    /**
     * @param fileName : 파일 이름
     * @param isHeader : header 존재유무
     * @return FileInfoCSV
     **/
    @Override
    public FileInfoCSV loadCSV(String fileName, boolean isHeader) throws ValidationException, IOException {
        return loadCSV(getAppDataPath(), fileName, ",", isHeader);
    }

    /**
     * @param path     : 파일 경로
     * @param fileName : 파일 이름
     * @param rgx      : 구분자
     * @return FileInfoCSV
     **/
    @Override
    public FileInfoCSV loadCSV(String path, String fileName, String rgx) throws ValidationException, IOException {
        return loadCSV(path, fileName, rgx, false);
    }

    /**
     * @param path     : 파일 경로
     * @param fileName : 파일 이름
     * @param rgx      : 구분자
     * @param isHeader : header 존재 유무
     * @return FileInfoCSV
     **/
    @Override
    public FileInfoCSV loadCSV(String path, String fileName, String rgx, boolean isHeader)
            throws ValidationException, IOException {

        // 1. csv 파일 확장자인지 검사한다.
        String last = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (!last.toLowerCase().equals("csv")) {
            throw new ValidationException("해당 파일의 확장자가 csv 형식이 아닙니다");
        }

        // 2. 파일 경로에 해당 파일이 존재하는지 검사한다.
        Path filePath = Paths.get(path, fileName);
        File file = new File(filePath.toUri());
        if (!file.exists()) {
            throw new FileNotFoundException("해당 파일이 존재하지 않습니다.");
        }
        FileInfoCSVBuilder builder = FileInfoCSV.builder();

        // 3. csv 파일을 읽는다.
        
        try ( // try ~ catch ~ resources
                BufferedReader br = new BufferedReader(new FileReader(file));
            ) {

            List<Object> list = new ArrayList<Object>();
            String line = "";
            int maxRow = 0, maxCol = 0, tempCol = 0;

            // BufferedReader 에서 데이터를 읽어오면서 최대 행과 열수를 계산하며 list에 담는다.
            
            while ((line = br.readLine()) != null) {

                Object curr[] = line.split(rgx);
                tempCol = curr.length;
                // 현재 열의 크기가 더 크면 maxCol에 담는다.
                if (tempCol > maxCol) {
                    maxCol = tempCol;
                }
                //  첫 Row에 header가 존재하면 builder에 header 정보를 담고
                if (isHeader && maxRow == 0) {
                    builder.header(curr);
            
                // 해더가 아닌 Row는 데이터 리스트 셋에 담는다.
                } else { 
                    list.add(Arrays.asList(curr));
                }
                // 행의 카운트를 센다.
                maxRow++;
            }
            br.close();

            // list를 2차원 배열로 할당하며 변환
            // @SuppressWarnings("unchecked")
            Object[][] data = list.stream()
                                  .map(   item -> ((Collection<?>) item).stream().toArray()  )
                                  .toArray(Object[][]::new);
            list = null;

        // 4. builder에 공통 fileInfo 정보를 담는다.
            builder.fileName(fileName)
                    .mimeType(Files.probeContentType(filePath))
                    .maxRow(maxRow)
                    .maxCol(maxCol)
                    .data(data);

        } catch (IOException e) {
            throw new IOException("파일을 불러오는데 실패하였습니다.");
        }
        // 5. fileInfo 객체를 리턴한다.
        return builder.build();
    }
    
}
