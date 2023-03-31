package com.acorn.springboardstudy.controller;

import com.acorn.springboardstudy.service.UserService;
import com.acorn.springboardstudy.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@AllArgsConstructor //lombok에서  컴파일 할때 모든 필드를  POJO 형식의 생성자로 만들어줌
@Controller//< @Component 의 일종, 요청과 응답을 처리 가능
@RequestMapping("/user")
@Log4j2 //log 필드로 로그를 남길 수 있다. (로그를 할 수 있는 필드가 따로 분리가 되어있고 컨트롤러에 주입할수있다.)
        //System.out.print() -> 휘발성 로그( 콘솔에 출력만 가능!)
        //Log4j2 는 파일로 저장가능[유지기간설정,성질]
public class UserController {
    //@GetMapping 으로 정의한 함수 하나하나 가 동적페이지이다.

    private UserService userService;

    //"/user/login.do" 동적페이지 정의
    @GetMapping("/login.do")
    public String loginForm(){

        return "/user/loginForm";//render할 view 를 정의
    }
    @PostMapping("/login.do")
    public String loginAction(
            UserDto user,//get/set으로 정의된 userDto를 받으면 파라미터를받을 수 있다.
            Integer autoLogin,
            HttpSession session,
            RedirectAttributes redirectAttributes){
        //🍒redirect로 페이지에 메세지를 보내는 방법
        //1. 파라미터로 ?msg= 로그인 성공(권장X)
                //redirectAttributes.addAttribute("msg","로그인 성공!");
        //2. Session에 추가한 후에 사용하고 삭제 (권장O, 보완이됨)
                //redirectAttributes.addFlashAttribute("msg","로그인성공");
                //->세션에 저장되었다가 사용하면 바로 삭제 (눈에보이지않음)
        //3. RedirectAttributes 사용, 2번과정을 자동으로 해줌.

        log.info(user);
        log.info(autoLogin);

        UserDto loginUser = null;
         try{
            loginUser=userService.login(user);
         }catch (Exception e){
             log.error(e.getMessage());
         }
        if(loginUser!=null){
            redirectAttributes.addFlashAttribute("msg","로그인 성공!");
            session.setAttribute("loginUser",loginUser);
            return "redirect:/";
            //get을 제외한 다른 메소드는 양식을 제출하거나 ajax 로 페이지를 호출할때만 가능
        }else {
            redirectAttributes.addFlashAttribute("msg","아이디나 패스워드를 확인하세요.");
            return "redirect:/user/login.do";
        }
    }
}
