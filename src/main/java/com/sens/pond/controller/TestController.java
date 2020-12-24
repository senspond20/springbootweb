package com.sens.pond.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TestController {

	
	@RequestMapping(value = "/test") 
	public String test(Model m) throws Exception{ 
		log.trace("Trace Level 테스트"); 
		log.debug("DEBUG Level 테스트"); 
		log.info("INFO Level 테스트"); 
		log.warn("Warn Level 테스트"); 
		log.error("ERROR Level 테스트"); 
		return "index"; 
	}

}
