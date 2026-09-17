package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    @Autowired
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String index(){
        return "redirect:/board/list";
    }

    @GetMapping("/board/write") // localhost:8080/board/write
    public String boardWriteForm(){
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file)throws Exception{

        if(!StringUtils.hasText(board.getTitle())){
            model.addAttribute("message", "제목을 입력해주세요.");
            model.addAttribute("searchUrl", "/board/write");

            return "message";
        }

        if(!StringUtils.hasText(board.getContent())){
            model.addAttribute("message", "내용을 입력해주세요.");
            model.addAttribute("searchUrl", "/board/write");

            return "message";
        }
        try {
            boardService.boardWrite(board, file);
        }
        catch (IllegalArgumentException e){
            model.addAttribute("message", e.getMessage());
            model.addAttribute("searchUrl", "/board/list");
            return "message";
        }

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("board/list")
    public String boardlist(Model model,
                            @PageableDefault(page=0, size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchOption,
                            String searchKeyword){

        Page<Board> list = null;

        if(StringUtils.hasText(searchKeyword)){
            if("Total".equals(searchOption)) {
                list = boardService.boardSearchTotalList(searchKeyword, pageable);
            }
            else if("Title".equals(searchOption)) {
                list = boardService.boardSearchTitleList(searchKeyword, pageable);
            }

            else if("Content".equals(searchOption)) {
                list = boardService.boardSearchContentList(searchKeyword, pageable);
            }

            else {
                list = boardService.boardList(pageable);
            }
        }

        else {
            list = boardService.boardList(pageable);
        }


        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.min(Math.max(nowPage -2, 1), (list.getTotalPages() - 5) + 1);
        int endPage = Math.max(Math.min(nowPage + 2,  list.getTotalPages()), 5);

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("searchOption", searchOption);
        model.addAttribute("searchKeyword", searchKeyword);


        return "boardlist";
    }

    @GetMapping("/board/view") //localhost:8080/board/view?id=1
    public String boardView(Model model, Integer id){
        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id, Model model) throws Exception{
        boardService.boardDelete(id);

        model.addAttribute("message", "글이 삭제되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return"message";
    }

    @GetMapping("board/modify/{id}")
    public  String boardUpdate(@PathVariable("id") Integer id, Model model){
        model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id,
                              Board board,
                              Model model,
                              MultipartFile file) throws Exception{
        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        try {
            boardService.boardWrite(boardTemp, file);
        }
        catch (IllegalArgumentException e){
            model.addAttribute("message", e.getMessage());
            model.addAttribute("searchUrl", "/board/list");
            return "message";
        }

        model.addAttribute("message", "글이 수정되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }



}
