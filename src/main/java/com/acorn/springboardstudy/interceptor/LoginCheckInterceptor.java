package com.acorn.springboardstudy.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Log4j2
public class LoginCheckInterceptor implements HandlerInterceptor {
    //필터보다 기능이 훨씬 많이 추가된 미들웨어


    @Override    //일반적으로 필터가 가능한 곳 (컨트롤러(spring),서블릿(tomcat)(동적페이지) 요청 전)
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //return true; // 원래 요청하면 페이지로 이동 (==doChain)
        //return false; // 안함.

        HttpSession session= request.getSession();
        Object loginUser=session.getAttribute("loginUser");
        String uri=request.getRequestURI();//로그인이 성공하면 원래 가려던 페이지
        if(loginUser!=null){
            return true;
        }else{//로그인되어있는 경우
            session.setAttribute("redirectPage",uri);
            session.setAttribute("msg","로그인시 이용가능한 페이지입니다.");
            response.sendRedirect("/user/login.do");
            return  false;
        }
    }
    //@Web 대신 interceptor에서는 config 파일을 만들어줘야함 -> InterceptorConfig.java


    @Override //요청이 끝나고 뷰를 렌더하기 직전
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override //view 랜더 가 끝나면 실행 -> 보통 html을 추가하고 싶을 때
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
