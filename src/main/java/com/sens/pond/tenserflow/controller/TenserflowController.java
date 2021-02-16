package com.sens.pond.tenserflow.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sens.pond.tenserflow.service.TenserflowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TenserflowController {

    @Autowired
    private TenserflowService tenserflowService;

    public void test() {
        
    }

    public static void main(String[] args) throws IOException {
    
       Path path = Paths.get(System.getProperty("user.dir"), "src","main","resources","data","test.csv");

    String mimeType = Files.probeContentType(path);

    System.out.println(mimeType);


        // String fileName = "test.csv";
        // String dott = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
        // System.out.println(dott);
    }
}
