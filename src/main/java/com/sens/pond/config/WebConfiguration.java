package com.sens.pond.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		HandlebarsViewResolver resolver = new HandlebarsViewResolver();
		// resolver.setCharset("UTF-8");
		resolver.setOrder(1);
		resolver.setContentType("text/html;charset=UTF-8");
		resolver.setPrefix("classpath:/templates");
		// 개발시에는 false, 배포시에는 true
		resolver.setCache(false);
		resolver.setSuffix(".html");
		registry.viewResolver(resolver);
	}

	// @Override
	// public void addInterceptors(InterceptorRegistry registry) {

	// 	registry.addInterceptor((HandlerInterceptor) new LoggerInterceptor());
	// 	WebMvcConfigurer.super.addInterceptors(registry);
	// }

}