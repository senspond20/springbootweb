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

import javax.xml.bind.ValidationException;

import com.sens.pond.tenserflow.model.FileInfoCSV;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.tensorflow.TensorFlow;

import javassist.bytecode.stackmap.BasicBlock.Catch;

@Service
public class TenserflowServiceImple implements TenserflowService {

    @Override
    public String getTenserflowVersion() {
        return TensorFlow.version();
    }

    @Override
    public Path getAppDataPath() {
        return Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "data");
    }

    @Override
    public FileInfoCSV loadCSV(String fileName) throws ValidationException, FileNotFoundException {

        // 파일이름에서 확장자를 뽑아 csv 형식인지 유효성 검사
        String last = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (!last.toLowerCase().equals("csv")) {
            throw new ValidationException("해당 파일의 확장자가 csv 형식이 아닙니다");
        }

        FileInfoCSV fileInfo = new FileInfoCSV();
        File directory = new File(getAppDataPath().toUri());
        // 폴더 미존재시 폴더 생성
        if (!directory.exists()) {
            directory.mkdir();
        }

        // 경로에 해당 파일이 존재하는지 체크       
        Path filePath = Paths.get(directory.toString(), fileName);

        File file = new File(filePath.toUri());
        if (!file.exists()) {
            throw new FileNotFoundException("해당 파일이 존재하지 않습니다.");
        } else {
            String mimeType = "";
            fileInfo.setFileName(fileName);
            fileInfo.setMimeType(mimeType);
            // try ~ catch ~ resouces 
            try (
                BufferedReader br = new BufferedReader(new FileReader(file));
            ) {
                mimeType = Files.probeContentType(filePath);
                br.close();
            } catch (IOException e) {

            }

            String line = "";
            String[] field = null;


        }

        return fileInfo;
    }

    public void loadPDF() throws IOException {
 
        File source = new File(Paths.get(getAppDataPath().toString(), "test.pdf").toUri()); 
        PDDocument pdfDoc = PDDocument.load(source); 
        String text = new PDFTextStripper().getText(pdfDoc); 
        System.out.println(text);
    }

    public static void main(String[] args) {
        File source = new File(Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "data","test2.pdf").toUri()); 
        File save = new File(Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "data","test2.txt").toUri());

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
