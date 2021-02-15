package com.sens.pond;

import java.util.stream.IntStream;

import com.sens.pond.board.entity.Board;
import com.sens.pond.board.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SenswebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SenswebApplication.class, args);
	}

	/**
     * 	샘플 데이터 
	 **/
    @Bean
    public CommandLineRunner initData(@Autowired BoardRepository boardRepository) {
        return args -> 
            IntStream.rangeClosed(1, 154).forEach(i -> {

                Board board = Board.builder()
                        .title("test" + i)
                        .content("내용입니다." + i)
						.author("작성자" + i)
                        .build();
                boardRepository.save(board);
            });
    }

}
