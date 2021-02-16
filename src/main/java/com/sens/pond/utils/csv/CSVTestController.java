package com.sens.pond.utils.csv;

import java.io.IOException;

import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CSVTestController {
    
    @Autowired
    private CSVLoader cs;

    @GetMapping("/api/csv/{fileName}")
    @ResponseBody
    public ResponseEntity<?> test(@PathVariable String fileName) {
        try {
            FileInfoCSV fileInfo = cs.loadCSV(fileName,false);           
           return ResponseEntity.ok().body(fileInfo);
        } catch (ValidationException | IOException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
