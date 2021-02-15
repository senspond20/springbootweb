package com.sens.pond.board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.sens.pond.board.entity.Board;
import com.sens.pond.board.repository.BoardMapper;
import com.sens.pond.board.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/mybatis/one")
    @ResponseBody
    public Object selectOne(){
        return mapper.selectOne();
    }

    @GetMapping("/mybatis/insert")
    @ResponseBody
    public void insertBoardBatch(){
        List<Board> list = new ArrayList<Board>();
        for(int i =1; i < 10000; i++){
            list.add(new Board("제목입니다." + i, "내용입니다." + i, "작성자입니다." + i));
        }
        mapper.insertBoard_Batch(list);
    }

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/jpa/insert")
    @ResponseBody
    public void insertBoardJPA(){
        // for(int i =1; i < 10000; i++){
        //     boardRepository.save(new Board("제목입니다." + i, "내용입니다." + i, "작성자입니다." + i));
        // }

        IntStream.rangeClosed(1, 10000).forEach(i -> {
            Board board = Board.builder()
                    .title("test" + i)
                    .content("내용입니다." + i)
                    .author("작성자" + i)
                    .build();
            boardRepository.save(board);
        });
    }
}
