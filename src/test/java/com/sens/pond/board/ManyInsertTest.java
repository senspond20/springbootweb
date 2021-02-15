package com.sens.pond.board;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Junit5
@SpringBootTest
public class ManyInsertTest {
  
    private long before;
    private long after;
    private void printTime(){
        System.out.println("수행시간 : " + (after - before));
    } 
    @BeforeAll
	void init() {
	}

    @Test
    void test_JPA(){
        this.before = System.currentTimeMillis();

        this.after = System.currentTimeMillis();
        printTime();
    }

    @Test
    void test_MYBATIS(){
        this.before = System.currentTimeMillis();

        this.after = System.currentTimeMillis();
        printTime();
    }

    @Test
    void test_JDBC(){
        this.before = System.currentTimeMillis();

        this.after = System.currentTimeMillis();
        printTime();
    }

}
