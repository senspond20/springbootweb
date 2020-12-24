package com.sens.pond.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sens.pond.entity.Board;

@Repository
public interface MemberRepository extends JpaRepository<Board, Long>{
	
}
