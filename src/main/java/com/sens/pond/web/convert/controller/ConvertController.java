package com.sens.pond.web.convert.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    @PostMapping("/input/test0")
    public ResponseEntity<?> test0(@RequestBody String param) {
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

   /**
    * @name test1
    * @apiNote 클라이언트에서 jsonArray를 직렬화 해서 보낼때 서버에서 받기 1
    *          <JSON 라이브러리 JSONArray 를 이용한 방법>
    * @param param
    * @return
    */
    @PostMapping(value = "/input/test1")
    public ResponseEntity<?> test1(@RequestBody String param) {
        System.out.println("============ test1 ============= ");
        JSONArray jsonArray;
        return ResponseEntity.ok().body("ok");
    }

    /**
     * @name test2
     * @apiNote 클라이언트에서 jsonArray를 직렬화 해서 보낼때 서버에서 받기 2
     *          <jackson 라이브러리 - ObjectMapper 를 이용한 방법>
     * @param param
     * @return
     */
    @PostMapping(value = "/input/test2")
    public ResponseEntity<?> test2(@RequestBody String param) {
        long before = System.currentTimeMillis();
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
        long after = System.currentTimeMillis();
        System.out.println(after-before +"ms");
        return ResponseEntity.ok().body("ok");
    }


    /**
     * @name test3
     * @apiNote 클라이언트에서 jsonArray를 직렬화 해서 보낼때 서버에서 받기 3
     *          <정규표현식을 이용한 문자열 파싱>
     * @param param
     * @return
     */
    @PostMapping("/input/test3")
    public ResponseEntity<?> test3(@RequestBody String param) {
        long before = System.currentTimeMillis();
        System.out.println("============ test3 ============= ");
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        
        Pattern pattern = Pattern.compile("\\{[^\\{\\}]*\\}");  // { } 리스트 
        Pattern sPattern = Pattern.compile("[\"][^\\,\\{\\}]*"); // " : " 리스트
        Matcher matcher = pattern.matcher(param);
        Matcher sMatcher = null;
        Map<String,Object> map = null;
        
        while(matcher.find()){
            // map = new HashMap<String, Object>();    hashMap은 순서 보장을 하지 않지만 속도가 더 빠르다.
            map = new LinkedHashMap<String, Object>(); 
            sMatcher = sPattern.matcher(matcher.group()); // { } 을 뽑고
            while (sMatcher.find()) {
                //" : " 을 뽑은 다음
                // "를 없애고 : 를 구분자로 나눈다.
                String[] mk = sMatcher.group().replaceAll("\"", "").split("\\:"); 
                map.put(mk[0], mk[1]);
            }
            list.add(map);
        }

        System.out.println(list);
        long after = System.currentTimeMillis();
        System.out.println(after-before +"ms");
        return ResponseEntity.ok().body("ok");
    }

}
