package com.sens.pond.web.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//일반 자바빈 규약대로 @Getter/@Setter를 만들기 쉽지만
//@Entity 가 붙으면 @Setter를 만들어서는 안된다.
//-> 해당 인스턴스의 값들이 언제 변경되는지 구분하기가 어려워서 향후 굉장히 복잡해진다.
//대신 필드를 변경하고자 한다면 목적과 이유가 명확해야 한다.-> update 제공

@Entity
@Getter
@NoArgsConstructor
@Table(name ="board")
@ToString
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(length = 500, nullable = false)
	private String title;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;
	
	private String author;

	@Builder // 빌더패턴 적용
	public Board(String title, String content, String author) {
		super();
		this.title = title;
		this.content = content;
		this.author = author;
	}
	// update 수정 setter대신
	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
}

