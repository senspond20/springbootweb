package com.sens.pond.config;

import java.util.stream.IntStream;
import com.sens.pond.board.entity.Board;
import com.sens.pond.board.repository.BoardRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

// @Component -> 컴포넌트 scan으로 인한 방식 사용시
public class MyCLRunner implements CommandLineRunner{ 

    @Autowired 
	private BoardRepository boardRepository;

    private Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override 
    public void run(String... args) throws Exception { 
        int count = 154;
        long beforeTime = System.currentTimeMillis(); 
        IntStream.rangeClosed(1, count).forEach(i -> {
            Board board = Board.builder()
                    .title("test" + i)
                    .content("내용입니다." + i)
                    .author("작성자" + i)
                    .build();
            boardRepository.save(board);
        });
        long afterTime = System.currentTimeMillis(); 
        logger.info("@@ Insert Board {}건 수행 시간 : {}ms", count,(afterTime - beforeTime));
    } 
}
