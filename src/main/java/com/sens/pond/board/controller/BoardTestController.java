package com.sens.pond.board.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sens.pond.board.entity.Board;
import com.sens.pond.board.repository.BoardMapper;
import com.sens.pond.board.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardTestController {
 
    @Autowired
    private BoardMapper mapper;

    @GetMapping("/mybatis/board")
    @ResponseBody
    public Object selectBoardAll(){
        return mapper.selectBoardAll();
    }

    @GetMapping("/junit5/test")
    @ResponseBody
    public Object junit5Test(){
        return "testsss";
    }

    @GetMapping("/mybatis/one")
    @ResponseBody
    public Object selectOne(){
        return mapper.selectOne();
    }

    @GetMapping("/board/test/mybatis")
    @ResponseBody
    public void insertBoardBatch(){
        final int count = 10000;
        List<Board> list = new ArrayList<Board>();
        for(int i =1; i <= count; i++){
            list.add(new Board("제목입니다" + i, "내용입니다" + i, "작성자입니다" + i));
        }
        System.out.println(mapper.insertBoard_Batch(list) + "건이 INSERT 되었습니다.");
    }

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/board/test/jpa")
    @ResponseBody
    public void insertBoardJPA(){
        final int count = 10000;
        for(int i =1; i <= 10000; i++){
            boardRepository.save(new Board("제목입니다" + i, "내용입니다" + i, "작성자입니다" + i));
        }
        System.out.println(count + "건이 INSERT 되었습니다.");

        // IntStream.rangeClosed(1, 10000).forEach(i -> {
        //     Board board = Board.builder()
        //             .title("test" + i)
        //             .content("내용입니다." + i)
        //             .author("작성자" + i)
        //             .build();
        //     boardRepository.save(board);
        // });
    }

    @Autowired
    private JdbcTemplate JdbcTemplate;

    @GetMapping("/board/test/jdbc")
    @ResponseBody
    public void insertBoardJDBC(){
        final int count = 10000;

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO BOARD (TITLE,CONTENT,AUTHOR) VALUES ");
     
        for(int i =1; i <= count; i++){
            Board b = new Board("제목입니다" + i, "내용입니다" + i, "작성자입니다" + i);
            // List<String> list = Arrays.asList("'" + b.getTitle() +"'", "'" +b.getContent() +"'", "'" +b.getAuthor() +"'");
            // sb.append("(").append(list.toString().replace("[", "").replace("]", ""));

            // convertStr(Arrays.asList(b.getTitle(), b.getContent(), b.getAuthor()));

           /* sql.append("(").append(
                Arrays.asList(b.getTitle(), b.getContent(), b.getAuthor())
                    .stream().map(item -> "'".concat(item).concat("'")).collect(Collectors.toList())
                    .toString().replace("[", "").replace("]", "")
            );*/

            sql.append("(")
               .append(convertStr(Arrays.asList(
                    b.getTitle(), 
                    b.getContent(), 
                    b.getAuthor())));
            if(i != count){
                sql.append("),");
            }else{
                sql.append(")");
            }
        }
        //System.out.println(sb.toString());
        JdbcTemplate.execute(sql.toString());
        sql = null;
        System.out.println(count + "건이 INSERT 되었습니다.");
    }

    private String convertStr(List<String> list) {

        String str = list.stream()
                         .map(item -> "'".concat(item).concat("'")).collect(Collectors.toList())
                         .toString().replace("[", "").replace("]", "");
        return str;
    }

    @GetMapping("/board/test/jdbc2")
    @ResponseBody
    public void insertBoardJDBCLow(){
        // PreparedStateme ps = 
        //Connection con = PreparedStateme
    }

}
