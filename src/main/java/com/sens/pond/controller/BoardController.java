package com.sens.pond.controller;

import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sens.pond.dto.BoardResponseDto;
import com.sens.pond.dto.BoardSaveRequestDto;
import com.sens.pond.dto.BoardUpdateRequestDto;
import com.sens.pond.entity.Board;
import com.sens.pond.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {
	private final BoardService boardService;
	
	// =======================================
	// API
	// =======================================
	// Insert
	@PostMapping("/api/posts")
	@ResponseBody
	public Board save(@RequestBody BoardSaveRequestDto requestDto) {
		return boardService.save(requestDto);
	}
			
	// Update
	@PutMapping("/api/posts/{id}")
	@ResponseBody
	public Long update(@PathVariable Long id, BoardUpdateRequestDto requestDto) {
		return boardService.update(id, requestDto);
	}
	
	// Select All
	@GetMapping("/api/posts")
	@ResponseBody
	public List<Board> findAll() {
		return boardService.findAll();
	}
		
	// Select
	@GetMapping("/api/posts/{id}")
	@ResponseBody
	public BoardResponseDto findbyId(@PathVariable Long id) {
		return boardService.findById(id);
	}
	
	// 테스트용 : insert 더미데이터 
	public void InsertDummy() {
       IntStream.range(1, 5000).forEach(i -> {
    	   boardService.save(new BoardSaveRequestDto("Title" + i, "Content" + i, "Author" + i));
        });
	}

	// =======================================
	// View return
	// =======================================
	@GetMapping("/show/board")
	public ModelAndView showBoard() {
		ModelAndView mv = new ModelAndView();	
		InsertDummy();
		mv.addObject("list", boardService.findAll());
		mv.setViewName("board/showboard");
		return mv;
	}
	
	@GetMapping("/show/boardp")
	public ModelAndView showBoardByPage(@PageableDefault Pageable pageable) {
		ModelAndView mv = new ModelAndView();	
//		InsertDummy();
		Page<Board> boardList =  boardService.getBoardList(pageable);
		mv.addObject("list", boardList);
		log.info("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
	                boardList.getTotalElements(), boardList.getTotalPages(), boardList.getSize(),
	                boardList.getNumber(), boardList.getNumberOfElements());
		mv.setViewName("board/showboard");
		return mv;
	}

	@GetMapping("/show/map")
	public ModelAndView showMap() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/map");
		return mv;
	}

	@PostMapping("/vi/posts")
	public String save2(HttpServletRequest request) {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String author = request.getParameter("author");
		
		BoardSaveRequestDto requestDto = new BoardSaveRequestDto(title, content, author);
		boardService.save(requestDto);
		return "redirect:/show/board";
	}
	
}
