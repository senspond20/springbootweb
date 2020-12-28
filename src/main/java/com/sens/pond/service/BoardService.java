package com.sens.pond.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sens.pond.dto.BoardResponseDto;
import com.sens.pond.dto.BoardSaveRequestDto;
import com.sens.pond.dto.BoardUpdateRequestDto;
import com.sens.pond.entity.Board;
import com.sens.pond.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;

	@Transactional
	public Board save(BoardSaveRequestDto requestDto) {
		return boardRepository.save(requestDto.toEntity());
	}

	@Transactional
	public Long update(Long id, BoardUpdateRequestDto requestDto) {
		Board board = boardRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		board.update(requestDto.getTitle(), requestDto.getContent());
		return id;
	}

	public List<Board> findAll() {
		return boardRepository.findAll();
	}

	public BoardResponseDto findById(Long id) {
		Board entity = boardRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		return new BoardResponseDto(entity);
	}

	// paging 
	public Page<Board> getBoardList(Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
		pageable = PageRequest.of(page, (int) pageable.getOffset(), Sort.by("id").descending());
		// pageable.getOffset() 기본 20
		return boardRepository.findAll(pageable);
	}
	
	public Page<Board> getBoardListFromPageRequest(int page, int size, Sort sort){
		Pageable pageable = PageRequest.of(page, size,sort);
		return boardRepository.findAll(pageable);
	}

//	public Page<Board> getBoardList(Sort sort, Pageable pageable, int boardLimit) {
//		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
//		pageable = PageRequest.of(page, boardLimit);
//		return boardRepository.findAll(sort, pageable);
//	}
	
	public Object findAll(Sort sort) {
		return boardRepository.findAll(sort);
	}
}
