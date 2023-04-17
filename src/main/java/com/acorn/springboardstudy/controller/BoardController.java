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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
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
        boards=boardService.list(loginUser);

        model.addAttribute("boards",boards);
        return "/board/list";
    }
    @GetMapping("/{tag}/tagList.do")
    public String tagList(
                        @PathVariable String tag,
                        Model model,
                        @SessionAttribute(required = false) UserDto loginUser){

        List<BoardDto> boards;
        boards=boardService.tagList(tag,loginUser);
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
                    @RequestParam (name="img",required = false) MultipartFile[] imgs,
                    @RequestParam(name="tag",required = false) List<String> tags
                                  ) throws IOException {
        String redirectPage="redirect:/board/register.do";

/*        test
        System.out.println("tags = " + tags);
        if(tags!=null) {
            return redirectPage;
        }*/

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
            register=boardService.register(board,tags);

        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(register>0){
            redirectPage="redirect:/board/list.do";
        }else{
            //등록실패시 저장했던 파일 삭제
            if(imgDtos!=null){
                for(BoardImgDto i: imgDtos){
                    File imgfIle=new File(staticPath+i.getImgPath());
                    if(imgfIle.exists()) imgfIle.delete();//DB에 이미지파일이 존재하면 이미지파일을 삭제합니다.
                }
            }
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
            @RequestParam(value="img",required = false)MultipartFile[] imgs,
            @RequestParam(value="tag",required = false) List<String>tags,
            @RequestParam(value="delTag",required = false) List<String> delTags
            ){
        //log.info(Arrays.toString(delImgIds));
        String redirectPage="redirect:/board/"+board.getBId()+"/modify.do";
        List<BoardImgDto> imgDtos=null;
        int modify=0;
        try{
            if(delImgIds!=null) imgDtos=boardService.imgList(delImgIds);//삭제하기전에 삭제예정 이미지 파일 경로를 받아옴.
            modify=boardService.modify(board,delImgIds,tags,delTags);//이미지 파일 주소를 서버에서 삭제
        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(modify>0){
            if(imgDtos!=null){
                for(BoardImgDto i: imgDtos){
                    File imgfIle=new File(staticPath+i.getImgPath());//삭제예정인 이미지 파일을 받아옴.
                    if(imgfIle.exists()) imgfIle.delete();//한번 더 물어보고 이미지파일을 DB에서 삭제
                }
            }
            redirectPage="redirect:/board/list.do";
        }
        return redirectPage;
    }
    @GetMapping("/{bId}/remove.do")
    public String removeAction(@PathVariable int bId,
                               @SessionAttribute UserDto loginUser,
                               RedirectAttributes redirectAttributes
                                ){
        String redirectPath="redirect:/board/"+bId+"/modify.do";
        String msg="삭제 실패";
        //DB의 이미지도 같이 삭제하기 위해 불러옴.
        BoardDto board=null;
        List<BoardImgDto> imgDtos=null;
        int remove=0;
        try{
            board=boardService.detail(bId);
            imgDtos=board.getImgs();//이미지 파일 조회(lazy이기 때문에~)
            remove= boardService.remove(bId);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(remove>0){
            //삭제 성공시, 이미지 파일도 삭제
            if(imgDtos!=null){
                for(BoardImgDto i:imgDtos){
                    File imgFile=new File(staticPath+i.getImgPath());
                    if(imgFile.exists()) imgFile.delete();
                }
            }
            msg="삭제성공!";
            redirectPath="redirect:/board/list.do";
        }
        redirectAttributes.addFlashAttribute("msg",msg);
        //addFlashAttribute:session에 전달
        //addAttribute : 파라미터에 전달.
        return redirectPath;

    }

}
