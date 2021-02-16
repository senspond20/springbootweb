package com.sens.pond.web.board.service.dto;
import com.sens.pond.web.board.entity.Board;

import lombok.Getter;

@Getter
public class BoardResponseDto {
	
	private Long id;
	private String title;
	private String content;
	private String author;
	
	public BoardResponseDto(Board Entity) {
		super();
		this.id = Entity.getId();
		this.title = Entity.getTitle();
		this.content = Entity.getContent();
		this.author = Entity.getAuthor();
	}
	
}
