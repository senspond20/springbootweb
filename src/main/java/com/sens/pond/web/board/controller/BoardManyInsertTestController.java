package com.sens.pond.web.board.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;
import com.sens.pond.web.board.entity.Board;
import com.sens.pond.web.board.repository.BoardMapper;
import com.sens.pond.web.board.repository.BoardRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardManyInsertTestController {
 
    private Logger logger = LoggerFactory.getLogger(BoardManyInsertTestController.class);

    @Autowired
    private BoardMapper mapper;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private JdbcTemplate JdbcTemplate;

    final int count = 10000;
    
    @GetMapping("/mybatis/board")
    @ResponseBody
    public Object selectBoardAll(){
        return mapper.selectBoardAll();
    }

    @GetMapping("/mybatis/one")
    @ResponseBody
    public Object selectOne(){
        return mapper.selectOne();
    }

    
    /**
     * @apiNote : Mybatis 
     * @param
     */
    @GetMapping("/board/test/mybatis")
    @ResponseBody
    public void insertBoardBatch(){
    	 Long beforeTime = System.currentTimeMillis();
        List<Board> list = new ArrayList<Board>();
        for(int i =1; i <= count; i++){
            list.add(new Board("제목입니다" + i, "내용입니다" + i, "작성자입니다" + i));
        }
        Long afterTime = System.currentTimeMillis();
        logger.info(mapper.insertBoard_Batch(list) + "건이 INSERT 되었습니다. 수행시간 {}ms",(afterTime -beforeTime));
    }

    /**
     * @apiNote : JPA 
     * @param
     */
    @GetMapping("/board/test/jpa")
    @ResponseBody
    public void insertBoardJPA(){

        Long beforeTime = System.currentTimeMillis();
        for(int i =1; i <= count; i++){
            boardRepository.save(new Board("제목입니다" + i, "내용입니다" + i, "작성자입니다" + i));
        }
        Long afterTime = System.currentTimeMillis();
        logger.info(count + "건이 INSERT 되었습니다. 수행시간 {}ms", (afterTime -beforeTime));

    }

    /**
     * @apiNote : JDBC 
     * @param
     */
    @GetMapping("/board/test/jdbc")
    @ResponseBody
    public void insertBoardJDBC(){
        final int count = 10000;
        Long beforeTime = System.currentTimeMillis();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO BOARD (TITLE,CONTENT,AUTHOR) VALUES ");
     
        for(int i =1; i <= count; i++){
            Board b = new Board("제목입니다" + i, "내용입니다" + i, "작성자입니다" + i);
 
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
        JdbcTemplate.execute(sql.toString());
        sql = null;
        Long afterTime = System.currentTimeMillis();
        logger.info(count + "건이 INSERT 되었습니다. 수행시간 {}ms", (afterTime -beforeTime));
    }

    
    private String convertStr(List<String> list) {

        String str = list.stream()
                         .map(item -> "'".concat(item).concat("'")).collect(Collectors.toList())
                         .toString().replace("[", "").replace("]", "");
        return str;
    }
 
}
