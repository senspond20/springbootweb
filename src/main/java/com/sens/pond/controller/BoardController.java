package com.sens.pond.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sens.pond.board.entity.Board;
import com.sens.pond.board.service.BoardService;
import com.sens.pond.board.service.dto.BoardResponseDto;
import com.sens.pond.board.service.dto.BoardSaveRequestDto;
import com.sens.pond.board.service.dto.BoardUpdateRequestDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

	@GetMapping("/api/boardp")
	public ResponseEntity<?> showBoardByPageApi(@PageableDefault Pageable pageable) {
		ModelAndView mv = new ModelAndView();	
//		InsertDummy();
	
		Page<Board> boardList =  boardService.getBoardList(pageable);
		mv.addObject("list", boardList);
		log.info("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
	                boardList.getTotalElements(), boardList.getTotalPages(), boardList.getSize(),
	                boardList.getNumber(), boardList.getNumberOfElements());
		mv.setViewName("board/showboard");
		return ResponseEntity.ok(boardList);
	}
	
	@GetMapping("/api/boardp/{page}")
	public ResponseEntity<?> showBoardByPageApi(@PathVariable int page) {
		ModelAndView mv = new ModelAndView();	
		int size = 5;
		Sort sort = Sort.by("id").descending();
		Page<Board> boardList =  boardService.getBoardListFromPageRequest(page, size, sort);
		mv.addObject("list", boardList);
		log.info("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
	                boardList.getTotalElements(), boardList.getTotalPages(), boardList.getSize(),
	                boardList.getNumber(), boardList.getNumberOfElements());
		mv.setViewName("board/showboard");
		return ResponseEntity.ok(boardList);
	}
	
	// http://localhost:8080/api/boardr?page=5&size=5
	@GetMapping("/api/boardr")
	public ResponseEntity<?> showBoardByPageApi(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView();	
		int page, size = 0;
		try {
			page = (int) Integer.parseInt(request.getParameter("page"));
			size = (int) Integer.parseInt(request.getParameter("size"));
		}catch(Exception e) {
			Map<String,String> responseMessage = new HashMap<String,String>();
			responseMessage.put("msg", HttpStatus.BAD_REQUEST.getReasonPhrase());
			responseMessage.put("code", HttpStatus.BAD_REQUEST.toString());
			return ResponseEntity.badRequest().body(responseMessage);
		}
		Sort sort = Sort.by("id").descending();
		Page<Board> boardList =  boardService.getBoardListFromPageRequest(page, size, sort);
		mv.addObject("list", boardList);
		log.info("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
	                boardList.getTotalElements(), boardList.getTotalPages(), boardList.getSize(),
	                boardList.getNumber(), boardList.getNumberOfElements());
		mv.setViewName("board/showboard");
		return ResponseEntity.ok(boardList);
	}
	
	// http://localhost:8080/api/boardrp?page=5&size=15dfdf
	// http://localhost:8080/api/boardrp?page=5&size=15
	@GetMapping("/api/boardrp")
	public ResponseEntity<?> showBoardByPageApi(@RequestParam("page") String page, 
												@RequestParam("size") String size) {
		ModelAndView mv = new ModelAndView();	
		int ipage, isize = 0;
		try {
			ipage = (int) Integer.parseInt(page);
			isize = (int) Integer.parseInt(size);
		}catch(Exception e) {
			Map<String,String> responseMessage = new HashMap<String,String>();
			responseMessage.put("msg", HttpStatus.BAD_REQUEST.getReasonPhrase());
			responseMessage.put("code", HttpStatus.BAD_REQUEST.toString());
			return ResponseEntity.badRequest().body(responseMessage);
		}
		
		Sort sort = Sort.by("id").descending();
		Page<Board> boardList =  boardService.getBoardListFromPageRequest(ipage, isize, sort);
		mv.addObject("list", boardList);
		log.info("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
	                boardList.getTotalElements(), boardList.getTotalPages(), boardList.getSize(),
	                boardList.getNumber(), boardList.getNumberOfElements());
		mv.setViewName("board/showboard");
		return ResponseEntity.ok(boardList);
	}
	
	// Spring boot 에서는 위에서처럼 귀찮게 BadRequest 응답예외처리를 하지 않아도 
	// RequestParam 자료형이 맞지 않으면 자동으로 BadRequest 응답을 던진다.
	// http://localhost:8080/api/boardrp?page=5&size=15dfdf
	// http://localhost:8080/api/boardrp?page=5&size=15
	// http://localhost:8080/api/boardrp?page=5
		
	@GetMapping("/api/boards")
	public ResponseEntity<?> showBoardByPageApi(@RequestParam("page") int page, 
												@RequestParam(name = "size", required = false, defaultValue = "10") int size) {
		ModelAndView mv = new ModelAndView();	
		Sort sort = Sort.by("id").descending();
		Page<Board> boardList =  boardService.getBoardListFromPageRequest(page, size, sort);
		mv.addObject("list", boardList);
		log.info("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
	                boardList.getTotalElements(), boardList.getTotalPages(), boardList.getSize(),
	                boardList.getNumber(), boardList.getNumberOfElements());
		mv.setViewName("board/showboard");
		return ResponseEntity.ok(boardList);
	}
/*
{
    "content": [
        {
            "id": 9973,
            "title": "Title4974",
            "content": "Content4974",
            "author": "Author4974"
        },
        {
            "id": 9972,
            "title": "Title4973",
            "content": "Content4973",
            "author": "Author4973"
        },
        {
            "id": 9971,
            "title": "Title4972",
            "content": "Content4972",
            "author": "Author4972"
        },
        {
            "id": 9970,
            "title": "Title4971",
            "content": "Content4971",
            "author": "Author4971"
        },
        {
            "id": 9969,
            "title": "Title4970",
            "content": "Content4970",
            "author": "Author4970"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "offset": 25,
        "pageSize": 5,
        "pageNumber": 5,
        "unpaged": false,
        "paged": true
    },
    "last": false,
    "totalPages": 2000,
    "totalElements": 9998,
    "size": 5,
    "number": 5,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "first": false,
    "numberOfElements": 5,
    "empty": false
}
 */
	
	// =======================================
	// View return
	// =======================================
	@GetMapping("/show/board")
	public ModelAndView showBoard() {
		ModelAndView mv = new ModelAndView();	
//		InsertDummy();
//		mv.addObject("list", boardService.findAll());
		
		// id 로 내림차순 정렬
//		mv.addObject("list", boardService.findAll(Sort.by("id").descending()));
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
