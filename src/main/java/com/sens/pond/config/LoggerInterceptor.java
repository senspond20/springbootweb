package com.sens.pond.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
// import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*
    spring boot 2.4 이상부터 HandlerInterceptorAdapter를 상속 받으려고 하면
    HandlerInterceptorAdapter is deprecated 라는 에러문구가 뜬다.
    -> HandlerInterceptor를 상속받는다.
*/
import org.springframework.web.servlet.ModelAndView;
public class LoggerInterceptor implements HandlerInterceptor{
    private Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    private long beforeTime; 
    private long afterTime;

    /* controller로 요청을 보내기 전에 처리하는 수행된다.
	   반환이 false라면 controller로 요청을 안함
	   handler 핸들러 정보를 담고있다.. ( RequestMapping , DefaultServletHandler ) */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("==================== Start =======================");
        logger.info("[ preHandle ]       RequestURI : {}",request.getRequestURI());
        logger.info("                    Method : {}",request.getMethod());
        beforeTime = System.currentTimeMillis();
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    /* controller의 요청 처리가 완료되면 수행됨(VIEW 랜더링 전)*/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        afterTime = System.currentTimeMillis(); 
        logger.info("[ postHandle ]      요청 수행시간 {}ms", (afterTime-beforeTime));
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    // view 까지 처리가 끝난 후에 처리됨
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
               
        logger.info("[ afterCompletion ] 최종 수행시간 {}ms", (afterTime-beforeTime));
        logger.info("===================== End ======================");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
