package com.sens.pond.utils.csv;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lowagie.text.pdf.AcroFields.Item;
import com.sens.pond.utils.csv.FileMaker.EncodingType;

import org.apache.poi.ss.formula.functions.T;


public class CSVUtils {
    private static CSVUtils maker;

    // 기본 싱글톤 디자인 패턴

    // 기본생성자를 private로 생성자를 통한 객체생성 금지
    private CSVUtils() {
    }

    // getInstanse() 로 싱글톤 객체 생성
    public static final CSVUtils getInstance() {
        synchronized (CSVUtils.class) {
            if (maker == null) {
                maker = new CSVUtils();
            }
        }
        return maker;
    }

    private boolean __makeCsvOrTxtFile(String path, List<?> list, EncodingType encoding, String... headers) {
        PrintWriter pw = null;
        StringBuilder sb = null;
        try {
            sb = new StringBuilder();
            pw = new PrintWriter(new File(path), encoding.toString());
            if (headers != null && headers.length > 0) {
                for (String header : headers) {
                    sb.append(header);
                    if (headers.length > 1) {
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
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (sb != null) {
                sb = null;
            }
        }
        return true;
    }

    // csv 또는 text요청에 따른 데이터 더하기 함수
    private void __innerAppenderCsvAndSingle(List<?> list, StringBuilder sb) throws Exception {
        for (Object T : list) {
            if (T instanceof String || T instanceof Integer || T instanceof Boolean || T instanceof Character) { // 단일
                sb.append(T.toString());
                sb.append("\n");
            } else if (T instanceof Map) { // 맵 형태
                @SuppressWarnings("unchecked")
                Map<Object, Object> map = (Map<Object, Object>) T;
                Iterator<Object> keys = map.keySet().iterator();
                while (keys.hasNext()) {
                    Object key = keys.next();
                    Object value = map.get(key);
                    if (value == null) {
                        continue;
                    }
                    sb.append(value);
                    sb.append(",");
                }
                sb.append("\n");
            } else {// 일반 vo형태
                BeanInfo info = Introspector.getBeanInfo(T.getClass());
                for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                    Method reader = pd.getReadMethod();
                    if (reader != null && (!pd.getName().equals("class"))) {
                        Object value = reader.invoke(T);
                        if (value == null) {
                            continue;
                        }
                        sb.append(value.toString());
                        sb.append(",");
                    }
                }
                sb.append("\n");
            }
        }
    }

    public static void main(String[] args) {

      /*  Object[] ar = { "123", "1242", "4242", "sdfd" };

        List<Object> list = new ArrayList<>();

        list.add(Arrays.asList(ar));
        list.add(Arrays.asList(ar));
        System.out.println(list);
        Object[][] data = list.stream()
                              .map(item -> ((Collection<?>) item).stream().toArray())
                              .toArray(Object[][]::new);

         int[] iArr = {1,2,3,4,5,6,7,8,9,10,11};
         char[] cArr = new char[iArr.length];*/

         Object[][] obj = {
                            {1,2,3,4,5,6,7},
                            {"dfdf","sdf",11,"feef"},
                            {12312,1},
                            {"fef","fefef"}
                          };
         // 2차원배열 to ExcelMap
         Map<String,Object> cell = new LinkedHashMap<String,Object>();
    
    
         for(int i=0; i < obj.length; i++){
            for(int j=0; j < obj[i].length; j++){
                cell.put(Character.toString((char)(j+65)).concat(Integer.toString(i+1)), obj[i][j]);
            }
         }

         System.out.println(cell);
         System.out.println("B1 cell :" + cell.get("B1"));
         System.out.println("A4 cell :" + cell.get("A4"));

         // ExcelMap to 2차원배열
         char[] c = new char[26];
         for(int i =0; i <c.length;i++){
             c[i] = (char)(i + 65);
         }

         
         int v = 701;
         String s ="";
         if(v > 702){
             System.out.println("최대 702 열까지만가능");
         }
         if(v == 702){
            s = "ZZ";
         }

        // 26 * ( 26 + 1) -1 = 701 만큼
         if(v < 26){
            s = Character.toString(c[(v % 26)]-1);
         }else{
            s = Character.toString(c[(v/26)-1]).concat(Character.toString(c[(v % 26)]-1));
         }
 
         System.out.println(s);

    //  System.out.println(Character.toString((char)(90)));
        //  Object[][] d = new Object[4][];
     
        a("A123");

}

public static int a(String col) {
        if(col.equals("ZZ")){
            return 702;
        }
        Pattern pattern = Pattern.compile("([A-Z]*)");
        
        Matcher mather = pattern.matcher(col);mather.find();
        System.out.println( mather.group());
        return 0;
    }
}
