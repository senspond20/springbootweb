package com.sens.pond.board;

import com.sens.pond.board.controller.BoardTestController;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// Junit5
// @SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest(BoardTestController.class)
public class ManyInsertTest {
  
    private long before;
    private long after;

    @Autowired
	protected MockMvc mockMvc;

    // @BeforeAll
	// void init() {
	// }

    // @BeforeEach
    // void setBefor(){
    //     this.before = System.currentTimeMillis();
    // }

    // @AfterEach
    // void setAfterAndPrint(){
    //     this.after = System.currentTimeMillis();
    //     System.out.println("수행시간 : " + (after - before));
    // }

    @Test
    void test_mvc() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/junit5/test")
        .accept(MediaType.APPLICATION_JSON));


    }
    @Test
    void test_JPA(){
        

    }

    @Test
    void test_MYBATIS(){
     
    }

    @Test
    void test_JDBC(){
   
    }

}
