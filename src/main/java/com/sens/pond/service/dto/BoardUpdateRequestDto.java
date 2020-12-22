package com.sens.pond.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateRequestDto {
	private String title;
	private String content;
	@Builder
	public BoardUpdateRequestDto(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	
}
