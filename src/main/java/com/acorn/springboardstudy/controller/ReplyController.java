package com.acorn.springboardstudy.controller;

import com.acorn.springboardstudy.dto.BoardReplyDto;
import com.acorn.springboardstudy.dto.UserDto;
import com.acorn.springboardstudy.service.BoardReplyService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/reply")
@Log4j2
public class ReplyController {
   private BoardReplyService boardReplyService;
    public ReplyController(BoardReplyService boardReplyService) {
        //주입 @AllArgsConstructor 어노테이션을 하면 밑의 imgUploadPath도 생성자를 만들어 에러가 발생.
        this.boardReplyService = boardReplyService;
    }

    @Value("${img.upload.path}") //application.yml 설정값 가져오기.

   private String imgUploadPath;
    @Value("${img.static.path}") //application.yml 설정값 가져오기.
    private String staticPath;


    @GetMapping("/{brId}/detail.do")
    public @ResponseBody BoardReplyDto detail(@PathVariable int brId){

        return boardReplyService.detail(brId);
    }


    @GetMapping("/{bId}/list.do")
    public String list (@PathVariable int bId,
                        Model model){
        List<BoardReplyDto> replies=boardReplyService.list(bId);
        model.addAttribute("replies",replies);
        return "/reply/list";
    }
     @Data
     class HandlerDto{
        private int register=0;
        private int modify;//기본값 =0
        private int remove;
     }
    @PostMapping("/handler.do")
    public @ResponseBody HandlerDto registerHandler(
            @ModelAttribute BoardReplyDto reply,
            @SessionAttribute UserDto loginUser,
            MultipartFile img) throws IOException {
        //@ResponseBody : 뷰를 응답하지 않고 해당 객체를 제이슨으로 만들어서 파싱해서 배포
//        log.info(reply);
//        log.info(img.getOriginalFilename()) ;
        HandlerDto handlerDto=new HandlerDto();
        if(!img.isEmpty()){
            String[] contentTypes=img.getContentType().split("/");
            if(contentTypes[0].equals("image")){
                String fileName=System.currentTimeMillis()+"_"+(int)(Math.random()*10000)+"."+contentTypes[1];
                Path path=Paths.get(imgUploadPath+"/reply/"+fileName);
                img.transferTo(path);
                //fetch에서 resp.status 200일때만 처리 하기 때문에 그냥 오류가 발생하면 500
                reply.setImgPath("/public/img/reply/"+fileName);
            }
        }
        int register=boardReplyService.register(reply);
        handlerDto.setRegister(register);
        return handlerDto;
    }

    @PutMapping("/handler.do")
    public @ResponseBody HandlerDto modify(
            @ModelAttribute BoardReplyDto reply,
            MultipartFile img,
            @SessionAttribute UserDto loginUser) throws IOException {
        HandlerDto handlerDto=new HandlerDto();
//       log.info(reply);
//       log.info(img.getOriginalFilename());
        if(!img.isEmpty()){

            String[] contentTypes=img.getContentType().split("/");
            if(contentTypes[0].equals("image")){
                String fileName=System.currentTimeMillis()+"_"+(int)(Math.random()*10000)+"."+contentTypes[1];
                Path path=Paths.get(imgUploadPath+"/reply/"+fileName);
                img.transferTo(path);
                //만약, 수정 전이미지 파일이 있으면 삭제
                if(reply.getImgPath()!=null){
                    File imgFile=new File(staticPath+reply.getImgPath());
                    if(imgFile.exists()) imgFile.delete();
                }
                reply.setImgPath("/public/img/reply/"+fileName);

            }
        }
        int modify=boardReplyService.modify(reply);
        handlerDto.setModify(modify);
        return handlerDto;
    }
    @DeleteMapping("/handler.do")
    public @ResponseBody HandlerDto remove(
            BoardReplyDto reply,
            @SessionAttribute UserDto loginUser
    ){
        HandlerDto handlerDto=new HandlerDto();
        int remove= boardReplyService.remove(reply.getBrId());
        handlerDto.setRemove(remove);
        if(remove>0&&reply.getImgPath()!=null){
            File imgFile=new File(staticPath+reply.getImgPath());
            if(imgFile.exists()) imgFile.delete();

        }
        return handlerDto;
    }

    @PostMapping("/insert.do")
    public String insertAction(
            @ModelAttribute BoardReplyDto reply,
            RedirectAttributes redirectAttributes,
            @SessionAttribute UserDto loginUser,
            MultipartFile img
            ) throws IOException {
        //    @SessionAttribute UserDto loginUser, //로그인한 유저만 접근
        //            MultipartFile img // img파일전송
        String msg="등록 실패";
        log.info(imgUploadPath);
        int register=0;
        try{
            if(img!=null && !img.isEmpty()){//파일을 선택하지 않아도 null이 아님.
                String contentType=img.getContentType(); //입력한 확장자 그대로 넘어옴.//image/png or image/jpeg or text/xml or application/json
                String [] contentTypes=contentType.split("/");
                if(contentTypes[0].equals("image")){
                    String fileName=System.currentTimeMillis()+"_"+(int)(Math.random()*10000)+"."+contentTypes[1];
                    String imgPath=imgUploadPath+"/reply/"+fileName ;  //서버컴퓨터의 물리적 위치
                    Path path= Paths.get(imgPath);
                    img.transferTo(path);  //이미지 저장
                    reply.setImgPath("/public/img/reply/"+fileName); //서버가 이미지를 배포하는 위치
                }
            }
            register=boardReplyService.register(reply);
        }catch (Exception e){
            log.error(e.getMessage());
            msg+="에러:"+e.getMessage();
        }
        //log.info(reply);
        if(register>0){
            msg="댓글 등록 성공!";
        }
        redirectAttributes.addFlashAttribute("msg",msg);

        return "redirect:/board/"+reply.getBId()+"/detail.do";
    }
}
