package com.sens.pond.repository;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.sens.pond.entity.Board;

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

