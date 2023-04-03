package com.acorn.springboardstudy.controller;

import com.acorn.springboardstudy.dto.UserDto;
import com.acorn.springboardstudy.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
    @GetMapping("/dropout.do")
    public String dropoutForm(
            @SessionAttribute UserDto loginUser){
        return "/user/dropoutForm";
    }
    @PostMapping("/dropout.do")
    public String dropoutAction(
            @ModelAttribute UserDto user,
            @SessionAttribute UserDto loginUser,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ){
        String msg="회원 탈퇴 실패";
        String redirectPage="redirect:/user/dropout.do";
        int dropout=0;
        try{
            dropout=userService.dropout(user);
        }catch (Exception e){
            log.error(e.getMessage());
            msg+="에러: "+e.getMessage();
        }
        if(dropout>0){
            msg="이용해주셔서 감사합니다.(회원탈퇴 성공)";
            redirectPage="redirect:/";
            session.removeAttribute("loginUser");

        }
        redirectAttributes.addFlashAttribute("msg",msg);
        return redirectPage;
    }

    @GetMapping("/{uId}/modify.do")
    public String modifyForm(
            @PathVariable String uId,
            @SessionAttribute UserDto loginUser,//로그인한 사람만 수정페이지 접근가능
            Model model ){ //model : 렌더할 뷰에 바로 객체 전달
        UserDto user=userService.detail(uId);
        model.addAttribute("user",user);
        return "/user/modify";
    }
    @PostMapping("/modify.do")
    public String modifyAction(
            @SessionAttribute UserDto loginUser,
            @ModelAttribute UserDto user,
            RedirectAttributes redirectAttributes
        ){
        int modify=0;
        String msg="수정 실패";
        String redirectPage="redirect:/user/"+user.getUId()+"/modify.do";
        try{
            modify=userService.modify(user);
        }catch (Exception e){
            log.error(e.getMessage());
            msg+= "에러"+e.getMessage();
        }
        if(modify>0) {
            redirectPage = "redirect:/user/"+ user.getUId()+"/detail.do";
            msg = "수정 성공!";
        }
        redirectAttributes.addFlashAttribute("msg",msg);
        return redirectPage;
    }


    //로그인한유저만 detail을 보게 하는 법
    //1.filter(interceptor) : 해당 페이지를 요청하기 전에 로그인 했는지 검사
    //2.Controller : 해당 페이지에서 로그인 했는지 검사
    @GetMapping("/{uId}/detail.do")
    public ModelAndView detail(
            @SessionAttribute(required = false) UserDto loginUser,
            //🔼UserDto loginUser=(UserDto)session.getAttribute("loginUser"); 과 같다.
            //단 위의 방법은 세션객체를 파라미터 취급(required=true)하여 오류가 발생시 400에러가 발생
            @PathVariable String uId,
            ModelAndView modelAndView,
            RedirectAttributes redirectAttributes
            ){
        //ModelAndView : 렌더하는 뷰 설정 및 전달할 객체설정.
        if(loginUser==null){
            redirectAttributes.addFlashAttribute("msg","로그인하셔야 이용할 수 있는 페이지 입니다.");
            modelAndView.setViewName("redirect:/user/login.do");
            return modelAndView;
        }
        UserDto user = userService.detail(uId);
        modelAndView.setViewName("/user/detail");
        modelAndView.addObject("user",user);
        return modelAndView;
    }
    @GetMapping("/signup.do")
    public void signupForm(){}

    @PostMapping("/signup.do")
    public String signupAction(
            @ModelAttribute UserDto user,
            RedirectAttributes redirectAttributes
    ){
        //  @ModelAttribute UserDto user, //유저를 쫙 가져온다!
        //log.info(user.toString()); //파라미터를 잘 받는지 체크
        int signup=0;
        String errMsg=null;
        try{
            signup=userService.signup(user);
        }catch (Exception e){
            log.error(e);
            errMsg=e.getMessage();
        }
        if(signup>0) {
            redirectAttributes.addFlashAttribute("msg","회원가입을 축하합니다!! 로그인하세요!");
            return "redirect:/";
        }else{
            redirectAttributes.addFlashAttribute("msg","회원가입 실패 에러 :"+errMsg);
            //상세한 에러정보이기 때문에 개발이 끝나면 삭제...
            return "redirect:/user/signup.do";
        }
    }
    @GetMapping("/logout.do")
    public String logoutAction(
            HttpSession session,
            RedirectAttributes redirectAttributes
            ){
     //session.invalidate();
        session.removeAttribute("loginUser");
        redirectAttributes.addFlashAttribute("msg","로그아웃되었습니다.");
        return "redirect:/";
    }

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
            RedirectAttributes redirectAttributes,
            @SessionAttribute(required=false) String redirectPage){
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
            if(redirectPage!=null){
                session.removeAttribute("redirectPage");
                return "redirect:" +redirectPage;
            }
            return "redirect:/";
            //get을 제외한 다른 메소드는 양식을 제출하거나 ajax 로 페이지를 호출할때만 가능
        }else {
            redirectAttributes.addFlashAttribute("msg","아이디나 패스워드를 확인하세요.");
            return "redirect:/user/login.do";
        }
    }
}
