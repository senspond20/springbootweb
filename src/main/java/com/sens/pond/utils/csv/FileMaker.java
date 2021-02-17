package com.sens.pond.utils.csv;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sens.pond.web.board.entity.Board;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FileMaker {
	
	public static enum EncodingType{  //인코딩 타입
		UTF8, EUCKR, Cp1252
	}
	
	public static enum BuildType{  //만드는 형식 타입
		CSV, EXCEL, TXT
	}
	
	private static FileMaker maker;
	private FileMaker(){ }
	
	public static final FileMaker getInstance(){
		synchronized (FileMaker.class) {
			if(maker == null){
				maker = new FileMaker();
			}
		}
		return maker;
	}
	
	//파일 만들기 메소드
	public boolean ListTypeToFile(String path, List<?> list,BuildType type, EncodingType encoding,String... headers) throws Exception{
        //headers는 가변인자, 옵셔널개념
		if(type.toString().equals("CSV") || type.toString().equals("TXT")){ 
			boolean firstJob = __makeCsvOrTxtFile(path, list, encoding, headers);
			if(firstJob){
				unix2dos(path);	
			}
			return true;
		} else if(type.toString().equals("EXCEL")){
			return __makeExcelFile(path, list, encoding, headers);
		} else {
			return false;
        }
	}
	
	//엑셀파일용 내부 메소드
	private boolean __makeExcelFile(String path, List<?> list, EncodingType encoding, String... headers) throws Exception{
		FileOutputStream output = null;
		
		int rowNumber = 0;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = null;
		
		if(headers != null && headers.length > 0){//헤더설정이 있다면..
			row = sheet.createRow(rowNumber);
			for(int i=0; i < headers.length;i++){
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(headers[i]);				
			}
			rowNumber ++;
		}		
		for(Object T : list){
			row = sheet.createRow(rowNumber);
			if(T instanceof Map){ //맵 형태
				@SuppressWarnings("unchecked")
				Map<Object,Object> map = (Map<Object, Object>)T;
				Iterator<Object> keys = map.keySet().iterator();
				int idx_inner = 0;
		        while( keys.hasNext() ){
		            Object value = map.get(keys.next()); 
		            if(value == null){ continue; }
					row.createCell(idx_inner).setCellValue(value.toString());
					idx_inner++;
		        }
			} else {//일반 vo형태
				BeanInfo info = Introspector.getBeanInfo(T.getClass());
				int idx_inner = 0;
				for(PropertyDescriptor pd : info.getPropertyDescriptors()){
					Method reader = pd.getReadMethod();
					if(reader != null && (!pd.getName().equals("class"))){
						Object value = reader.invoke(T);
						if(value == null){ continue; }
						row.createCell(idx_inner).setCellValue(value.toString());
						idx_inner++;	
					}
				}
			}
			rowNumber++;
		}
		try {
			output = new FileOutputStream(path);
			workbook.write(output);		
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally{
			output.close();
			workbook.close();
		}
		return true;
	}
	
	//csv또는 일반 text만들기 내부 메소드
	private boolean __makeCsvOrTxtFile(String path, List<?> list, EncodingType encoding, String... headers){
		PrintWriter pw = null;
		StringBuilder sb = null;
		try {
			sb = new StringBuilder();
			pw = new PrintWriter(new File(path), encoding.toString());
			if(headers != null && headers.length > 0){
				for(String header : headers){
					sb.append(header);
					if(headers.length > 1){
						sb.append(",");
					}
				}
				sb.append("\n");
			}
			__innerAppenderCsvAndSingle(list, sb);
			pw.println(sb.toString());
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally{
	        if(pw != null){pw.close();}
	        if(sb != null){sb = null;}
		}
		return true;
	}
	
	//csv 또는 text요청에 따른 데이터 더하기 함수
	private void __innerAppenderCsvAndSingle(List<?> list, StringBuilder sb) throws Exception{
		for(Object T : list){
			if(T instanceof String || T instanceof Integer || T instanceof Boolean || T instanceof Character){ //단일 
				sb.append(T.toString());
				sb.append("\n");
			} else if(T instanceof Map){ //맵 형태
				@SuppressWarnings("unchecked")
				Map<Object,Object> map = (Map<Object, Object>)T;
				Iterator<Object> keys = map.keySet().iterator();
		        while( keys.hasNext() ){
		            Object key = keys.next();
		            Object value = map.get(key); 
		            if(value == null){ continue; }
					sb.append(value);
					sb.append(",");		            
		        }
		        sb.append("\n");
			} else {//일반 vo형태
				BeanInfo info = Introspector.getBeanInfo(T.getClass());
				for(PropertyDescriptor pd : info.getPropertyDescriptors()){
					Method reader = pd.getReadMethod();
					if(reader != null && (!pd.getName().equals("class"))){
						Object value = reader.invoke(T);
						if(value == null){ continue; }
						sb.append(value.toString());
						sb.append(",");
					}
				}
				sb.append("\n");
			}
		}
	}
	
	//유닉스형태 파일을 일반 dos형태 파일로 바꾸는 함수(인코딩방지)
	private void unix2dos(String fname) {
		try {
			File f1 = new File(fname);
			File f2 = new File(fname+".csv");
			BufferedInputStream inp = new BufferedInputStream(new FileInputStream(f1));
			BufferedOutputStream outp = new BufferedOutputStream(new FileOutputStream(f2));
			int c;
			while ((c = inp.read()) > 0) {
				if (c == '\n')
					outp.write('\r');
				outp.write(c);
			}
			inp.close();
			outp.close();
			if (!f1.delete()) {
				System.out.printf("File %s is read-only; converted result can't be saved\n", fname);
				return;
			}
			if (!f2.renameTo(f1))
				System.out.printf("File %s is removed; convert result is saved in %s\n",fname, f2.getAbsolutePath());
		} catch (Exception e) {
			System.out.printf("i/o error while processing %s\n", fname);
		}
	}	
	

	public static void main(String[] args) {
		
		FileMaker maker = FileMaker.getInstance();

		List<Board> list = new ArrayList<Board>();
          //원하는 vo객체를 만든다. 어떤vo이던지 간에 상관 없다.
		Board vo = new Board(1L,"제목입니다","안녕하세요","작성자");

		list.add(vo);
		list.add(vo);
		
		
		try {
			maker.ListTypeToFile("D:/test.xls", list, FileMaker.BuildType.EXCEL, FileMaker.EncodingType.EUCKR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		List<HashMap<Object, Object>> list2 = new ArrayList<HashMap<Object,Object>>();
		HashMap<Object, Object> item = new HashMap<Object, Object>();
		item.put("1", "1번데이터");
		item.put("2", "2번데이터");
		list2.add(item);

		try {
			maker.ListTypeToFile("D:/test.csv", list2, FileMaker.BuildType.CSV, FileMaker.EncodingType.EUCKR, "1번째","2번째");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}