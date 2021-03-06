package com.sens.pond;

import com.sens.pond.config.MyCLRunner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SenswebApplication {
	// @Autowired 
	// private BoardRepository boardRepository;

	public static void main(String[] args) {
		SpringApplication.run(SenswebApplication.class, args);
	}

	//애플리케이션 실행 시점에 수행되는 초기화 코드
	@Bean
	public CommandLineRunner myCLineRunner(){
		return new MyCLRunner();
	}

	// https://jeong-pro.tistory.com/206
	// component scanning에 의한 방식으로 빈을 등록하는 방법
	/*@Component 
	class MyCLRunner implements CommandLineRunner{ 
		@Override public void run(String... args) throws Exception { 
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
			System.out.println("@@ Insert Board" + count + "건 수행 시간 : " + (afterTime - beforeTime) + "ms");
		} 
	}*/

	/**
     * 	샘플 데이터 
	 **/
 /*   @Bean
    public CommandLineRunner initData(@Autowired BoardRepository boardRepository) {
		long beforeTime = System.currentTimeMillis(); 
		IntStream.rangeClosed(1, 154).forEach(i -> {
			Board board = Board.builder()
					.title("test" + i)
					.content("내용입니다." + i)
					.author("작성자" + i)
					.build();
			boardRepository.save(board);
		});
		long afterTime = System.currentTimeMillis(); 
		System.out.println("@@ 수행 시간 : " + (afterTime - beforeTime));
		
        return args -> 	
            IntStream.rangeClosed(1, 154).forEach(i -> {

                Board board = Board.builder()
                        .title("test" + i)
                        .content("내용입니다." + i)
						.author("작성자" + i)
                        .build();
                boardRepository.save(board);
            });
    }*/

}
