package com.acorn.springboardstudy.controller;

import com.acorn.springboardstudy.dto.BoardDto;
import com.acorn.springboardstudy.dto.BoardImgDto;
import com.acorn.springboardstudy.dto.UserDto;
import com.acorn.springboardstudy.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/board")
@Log4j2
public class BoardController {

    private BoardService boardService; //DIP

    @Value("${img.upload.path}")
    private String uploadPath;//등록
    @Value("${static.path}")
    private String staticPath; //삭제
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list.do")
    public String list(Model model,
                       @SessionAttribute(required = false) UserDto loginUser){
        // @SessionAttribute(required = false) UserDto loginUser 로그인 안해도 들어올수있음.

        List<BoardDto> boards;
        if(loginUser==null) boards=boardService.list();
        else boards=boardService.list(loginUser.getUId());

        model.addAttribute("boards",boards);
        return "/board/list";
    }
    //?bId=1 //bId 동적페이지에 꼭 필요(400)을 명시적으로 나타내는것
    @GetMapping("/{bId}/detail.do")
    public String detail(Model model,
                         @PathVariable int bId){
        BoardDto board=boardService.detail(bId);
        model.addAttribute("b",board);
        return "/board/detail";//html
    }

    @GetMapping("/register.do")
    public void registerForm(@SessionAttribute UserDto loginUser){

    }
    @PostMapping("/register.do")
    public String registerAction(
                    @SessionAttribute UserDto loginUser,
                    @ModelAttribute BoardDto board,
                    @RequestParam (name="img") MultipartFile[] imgs
                                  ) throws IOException {
        String redirectPage="redirect:/board/register.do";
        if(!loginUser.getUId().equals(board.getUId())) return redirectPage;
        /*폼의 글쓴이와 로그인아이디가 다른경우.*/
        /*@RequestParam (name="img") MultipartFile[] imgs
           파일을 복수로 보낼때 사용 원래 파라미터가 img로 오고 우리는 imgs에 담아야 하기 때문에
           이름 설정을 해줍니다.*/
        List<BoardImgDto> imgDtos=null;
        if(imgs!=null){
            imgDtos=new ArrayList<>();
            for(MultipartFile img: imgs){
                if(!img.isEmpty()){
                    String[] contentTypes=img.getContentType().split("/");
                    if(contentTypes[0].equals("image")){
                        String fileName=System.currentTimeMillis()+"_"+(int)(Math.random()*10000)+"."+contentTypes[1];
                        Path path = Paths.get(uploadPath+"/board/"+fileName);//컴퓨터의 실제 저장위치.
                        img.transferTo(path);
                        BoardImgDto imgDto=new BoardImgDto();
                        imgDto.setImgPath("/public/img/board/"+fileName);//서버배포경로
                        imgDtos.add(imgDto);
                    }
                }
            }
        }
        board.setImgs(imgDtos);
        int register=0;
        try{
            register=boardService.register(board);

        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(register>0){
            redirectPage="redirect:/board/list.do";
        }else{
            //등록실패시 저장했던 파일 삭제

        }
        return redirectPage;

    }

    @GetMapping("/{bId}/modify.do")
    public String modify(Model model,
                         @PathVariable int bId,
                         @SessionAttribute UserDto loginUser){
        BoardDto board=boardService.detail(bId);
        model.addAttribute("b",board);
        return "/board/modify";//html
    }

    @PostMapping("/modify.do")
    public String modifyAction(
            @ModelAttribute BoardDto board,
            @RequestParam(value ="delImgId",required = false) int[] delImgIds,
            @RequestParam(value ="delImgPath",required = false) String[] delImgpaths,
            @RequestParam(value="img",required = false)MultipartFile[] imgs){
        //log.info(Arrays.toString(delImgIds));
        String redirectPage="redirect:/board/"+board.getBId()+"/modify.do";

        int modify=0;
        try{
            modify=boardService.modify(board,delImgIds);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(modify>0){
            redirectPage="redirect:/board/list.do";
        }
        return redirectPage;
    }

}
