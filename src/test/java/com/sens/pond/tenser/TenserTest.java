package com.sens.pond.tenser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tensorflow.TensorFlow;

public class TenserTest {

    @BeforeEach
    void setup() {
        System.out.println("TensorFlow version : " + TensorFlow.version());
    }

    @Test
    void test() throws IOException {
        String path = Paths.get(System.getProperty("user.dir"), "src","main","resources","data").toString();
    
        Path p = Paths.get(System.getProperty("user.dir"), "src","main","resources","data","test.csv");
       
        File file = new File(p.toUri());

        if(file.exists()){
        System.out.println(file.toPath());
        }
		System.out.println("### :" + file.exists());

        
    }

    @Test
    void test2(){
        String fileName ="test.csv";
        String dott = fileName.substring(fileName.lastIndexOf("."), fileName.length()-1);
        System.out.println(dott);
    }
}
