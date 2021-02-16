package com.sens.pond.tenserflow.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.ValidationException;

import com.fasterxml.jackson.databind.ObjectMapper;
// import com.sens.pond.tenserflow.model.FileInfoCSV;
import com.sens.pond.tenserflow.service.TenserflowService;
import com.sens.pond.utils.csv.CSVLoader;
import com.sens.pond.utils.csv.FileInfoCSV;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
// @RequiredArgsConstructor
public class TenserflowController {

    private TenserflowService tenserflowService;
    @Autowired
    private CSVLoader cs;

    @GetMapping("/api/csv/{fileName}")
    @ResponseBody
    public ResponseEntity<?> test(@PathVariable String fileName) {
        final boolean DEBUG_PRINT = true;
        try {
            // FileInfoCSV fileInfo = tenserflowService.loadCSV(fileName, DEBUG_PRINT);
            // FileInfoCSV fileInfo = tenserflowService.loadCSV(fileName);

            //  CSVLoader cs = new CSVLoader();
            FileInfoCSV fileInfo = cs.loadCSV(fileName,false);
            
           return ResponseEntity.ok().body(fileInfo);
        } catch (ValidationException | IOException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
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
