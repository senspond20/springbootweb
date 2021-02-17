package com.sens.pond.web.convert.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConvertController {

    @PostMapping("/input/test1")
    public ResponseEntity<?> test1(@RequestBody String param) {
        System.out.println("============ test1 ============= ");
        Pattern pattern = Pattern.compile("\\{[^\\{\\}]*\\}");
        Matcher matcher = pattern.matcher(param);
    
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
      
        ObjectMapper mapper = new ObjectMapper();

        while(matcher.find()){
            // System.out.println(matcher.group());
            try {
                list.add(mapper.readValue(matcher.group(), Map.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        System.out.println(list);
        return ResponseEntity.ok().body("ok");

    }

    @PostMapping(value = "/input/test2")
    public ResponseEntity<?> test2(@RequestBody String param) {
        System.out.println("============ test2 ============= ");
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> list = null;
        try {
            list = mapper.readValue(
                param, 
                new TypeReference<ArrayList<Map<String, Object>>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(list);
        return ResponseEntity.ok().body("ok");

    }
}
