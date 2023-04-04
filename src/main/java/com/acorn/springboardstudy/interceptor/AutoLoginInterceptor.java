package com.acorn.springboardstudy.interceptor;

import com.acorn.springboardstudy.dto.UserDto;
import com.acorn.springboardstudy.lib.AESEncryption;
import com.acorn.springboardstudy.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component //Config에서 Bean으로 주입받기 위해 작성
@AllArgsConstructor //생성자주입
@Log4j2
public class AutoLoginInterceptor implements HandlerInterceptor {
    //로그인이 되어있는 사람은 필요 없는 페이지 이다.
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession();
        Object loginUserObj=session.getAttribute("loginUser");
        if(loginUserObj!=null) return  true;//로그인한 유저인 경우 그냥 패스해~

        Cookie[] cookies=request.getCookies();
        if(cookies==null) return true; //쿠키가 없으면 자동로그인 안할꺼니 그냥 패스해~
        Cookie loginId =null;
        Cookie loginPw = null;
        for(Cookie c: cookies){
            if(c.getName().equals("SPRING_LOGIN_ID")){
                loginId=c;
            }else if(c.getName().equals("SPRING_LOGIN_PW")){
                loginPw=c;
            }
        }
        if(loginId!=null && loginPw!=null){
            UserDto loginUser=null;
            String msg=null;
            try{
                UserDto user = new UserDto();
                user.setUId(AESEncryption.decryptValue(loginId.getValue()));//복호화
                user.setPw(AESEncryption.decryptValue(loginPw.getValue()));
                loginUser=userService.login(user);
            }catch(Exception e){
                log.error(e.getMessage());
            }
            if(loginUser!=null){
                msg="자동 로그인 성공!";
                session.setAttribute("loginUser",loginUser);//로그인 성공
                session.setAttribute("msg",msg);
                return true;
            }else{
                msg="자동로그인 실패!(쿠키정보를 삭제합니다. 다시 로그인하세요!)";
                loginId.setMaxAge(0);
                loginPw.setMaxAge(0);
                loginId.setPath("/");
                loginPw.setPath("/");
                response.addCookie(loginId);
                response.addCookie(loginPw);//만들어놓은 쿠키들 삭제~
                session.setAttribute("msg",msg);
                response.sendRedirect("/user/login.do");
                return false;
            }
        }
        return true;//아이디랑 패스워드 둘중에 하나라도 없으면 그냥 패스해~
    }
}
