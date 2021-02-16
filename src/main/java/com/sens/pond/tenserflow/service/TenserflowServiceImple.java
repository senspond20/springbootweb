package com.sens.pond.tenserflow.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.ValidationException;


import com.sens.pond.tenserflow.model.FileInfoCSV;
import com.sens.pond.tenserflow.model.FileInfoCSV.FileInfoCSVBuilder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.tensorflow.TensorFlow;

@Service
public class TenserflowServiceImple implements TenserflowService {

    @Override
    public String getTenserflowVersion() {
        return TensorFlow.version();
    }

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
        return loadCSV(getAppDataPath(),fileName,",",false);
    }

    /**
     * @param fileName : 파일 이름
     * @param isHeader : header 존재유무
     * @return FileInfoCSV
     **/
    @Override
    public FileInfoCSV loadCSV(String fileName, boolean isHeader) throws ValidationException, IOException{
        return loadCSV(getAppDataPath(),fileName,",",isHeader);
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
    public FileInfoCSV loadCSV(String path, String fileName, String rgx, boolean isHeader) throws ValidationException, IOException{

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
        try (  // try ~ catch ~ resources
                BufferedReader br = new BufferedReader(new FileReader(file));
            ){

            List<Object> list = new ArrayList<Object>();
            String line = "";
            int maxRow = 0, maxCol = 0, tempCol = 0;
            
            // BufferedReader 에서 데이터를 읽어오면서 최대 행과 열수를 계산하고 list에 담는다.
            while ((line = br.readLine()) != null) {
                Object curr[] = line.split(rgx);
                tempCol = curr.length;
                if (tempCol > maxCol) {
                    maxCol = tempCol;
                }
                // 첫 Row에 header가 존재하면 builder에 header 정보를 담고
                if(isHeader && maxRow == 0){
                    builder.header(curr);
                }else{ // 해더가 아닌 Row는 데이터 리스트 셋에 담는다.
                    list.add(Arrays.asList(curr));
                }
                maxRow++;
            }
            br.close();

            // list를 2차원 배열로 할당하며 변환
            @SuppressWarnings("unchecked")
            Object[][] data = list.stream()
                                    .map(
                                    item -> ((Collection<Object>) item).stream().toArray()
                                    )
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



    public void printMatrix(Object[][] obj) {
        for (int i = 0; i < obj.length; i++) {
            for (int j = 0; j < obj[i].length; j++) {
                System.out.print(obj[i][j]);
            }
            System.out.println();
        }
    }

    private final int MAX_BUFFERSIZE = 10000;

    /*
    @Override
    public FileInfoCSV loadCSV(String fileName, boolean isPrint) throws ValidationException, IOException {

        // 파일이름에서 확장자를 뽑아 csv 형식인지 유효성 검사
        String last = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (!last.toLowerCase().equals("csv")) {
            throw new ValidationException("해당 파일의 확장자가 csv 형식이 아닙니다");
        }

        Path filePath = Paths.get(getAppDataPath(), fileName);

        // 경로에 해당 파일이 존재하는지 체크
        File file = new File(filePath.toUri());
        FileInfoCSV fileInfo = new FileInfoCSV();

        if (!file.exists()) {
            throw new FileNotFoundException("해당 파일이 존재하지 않습니다.");
        }
        fileInfo.setFileName(fileName);
        // try ~ catch ~ resouces
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            Object[][] buffer = new Object[10000][10000];
            fileInfo.setMimeType(Files.probeContentType(filePath));

            String line = "";
            int maxCol = 0;
            int maxRow = 0;
            while ((line = br.readLine()) != null) {
                String colStr[] = line.split(",");
                int tempCol = colStr.length;
                if (tempCol > maxCol) {
                    maxCol = tempCol;
                }

                for (int col = 0; col < tempCol; col++) {
                    buffer[maxRow][col] = colStr[col];
                }
                maxRow++;
            }

            Object[][] obj = new Object[maxRow][maxCol];
            if (isPrint)
                System.out.printf("==== [%s] ====\n", fileName);
            for (int i = 0; i < obj.length; i++) {
                for (int j = 0; j < obj[i].length; j++) {
                    obj[i][j] = (buffer[i][j] != null) ? buffer[i][j] : "";
                    if (isPrint)
                        System.out.printf("%s ", obj[i][j]);
                }
                if (isPrint)
                    System.out.println();
            }
            if (isPrint)
                System.out.println("====================");
            br.close();

            fileInfo.setMaxRow(maxRow);
            fileInfo.setMaxCol(maxCol);
            fileInfo.setData(obj);
        } catch (IOException e) {
            throw new IOException("파일을 불러오는데 실패하였습니다.");
        }
        return fileInfo;
    }
    */

    public void loadPDF() throws IOException {

        File source = new File(Paths.get(getAppDataPath().toString(), "test.pdf").toUri());
        PDDocument pdfDoc = PDDocument.load(source);
        String text = new PDFTextStripper().getText(pdfDoc);
        System.out.println(text);
    }

    public static void main(String[] args) {
        File source = new File(
                Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "data", "test2.pdf").toUri());
        File save = new File(
                Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "data", "test2.txt").toUri());

        PDDocument pdfDoc;
        try {
            pdfDoc = PDDocument.load(source);
            String text = new PDFTextStripper().getText(pdfDoc);
            // System.out.println(text);
            FileWriter myWriter = new FileWriter(save);
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
    // public void excelReader() {

    //     try {
    //         FileInputStream file = new FileInputStream("D:/tmp/upload/right_excel/test.xlsx");
    //         XSSFWorkbook workbook = new XSSFWorkbook(file);
    //         int rowindex = 0;
    //         int columnindex = 0; 
    //         //시트 수 (첫번째에만 존재하므로 0을 준다) 
    //         //만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다 XSSFSheet sheet=workbook.getSheetAt(0); //행의 수 int rows=sheet.getPhysicalNumberOfRows(); for(rowindex=0;rowindex<rows;rowindex++){ //행을읽는다 XSSFRow row=sheet.getRow(rowindex); if(row !=null){ //셀의 수 int cells=row.getPhysicalNumberOfCells(); for(columnindex=0; columnindex<=cells; columnindex++){ //셀값을 읽는다 XSSFCell cell=row.getCell(columnindex); String value=""; //셀이 빈값일경우를 위한 널체크 if(cell==null){ continue; }else{ //타입별로 내용 읽기 switch (cell.getCellType()){ case XSSFCell.CELL_TYPE_FORMULA: value=cell.getCellFormula(); break; case XSSFCell.CELL_TYPE_NUMERIC: value=cell.getNumericCellValue()+""; break; case XSSFCell.CELL_TYPE_STRING: value=cell.getStringCellValue()+""; break; case XSSFCell.CELL_TYPE_BLANK: value=cell.getBooleanCellValue()+""; break; case XSSFCell.CELL_TYPE_ERROR: value=cell.getErrorCellValue()+""; break; } } System.out.println(rowindex+"번 행 : "+columnindex+"번 열 값은: "+value); } } } }catch(Exception e) { e.printStackTrace(); } }

    //     }


    // }
