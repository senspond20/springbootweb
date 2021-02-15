package com.sens.pond.board;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;


public class ListToStrTest {
    
    @Test
    void test(){
        List<String> list = new ArrayList<>();
        list.add("안녕하세요");
        list.add("반갑습니다");
        list.add("갑시다");
        
        String str = list.toString();
        System.out.println(str.replaceAll("\s",""));
        
        String str1 ="        sdfdsfdsfdsfdfd \n sfdsfdfds              ";
        System.out.println(str1.trim());
    }
}
