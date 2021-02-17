package com.sens.pond.web.board.repository;

import java.util.List;
import com.sens.pond.web.board.entity.Board;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface BoardMapper {
    
    //public List<Map<String, Object>> selectBoardAll();
    public List<Board> selectBoardAll();
    public int selectOne();

    public int insertBoard_Batch(List<Board> list);
}
