package com.sens.pond.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sens.pond.domain.Board;
import com.sens.pond.repository.BoardRepository;
import com.sens.pond.service.dto.BoardResponseDto;
import com.sens.pond.service.dto.BoardSaveRequestDto;
import com.sens.pond.service.dto.BoardUpdateRequestDto;

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
		pageable = PageRequest.of(page, 10);
		return boardRepository.findAll(pageable);
	}
}
