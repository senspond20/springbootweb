package com.sens.pond.repository;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.sens.pond.entity.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
	List<Board> findAll();
	
	Page<Board> findAll(Pageable pageable);
	
	Long save(Long id);
}

