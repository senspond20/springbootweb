package com.sens.pond.web.board.repository;

import java.util.List;

import com.sens.pond.web.board.entity.Board;

import org.springframework.stereotype.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
	
	// findAll
	List<Board> findAll();
	
	List<Board> findAll(Sort sort);
	
	Page<Board> findAll(Pageable pageable);
	
//	Page<Board> findAll(Sort sort, Pageable pageable); 존재하지 않음
	
	Long save(Long id);


}

