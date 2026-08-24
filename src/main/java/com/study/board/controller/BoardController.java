package com.study.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/board/write") // localhost:8080/board/write
    public String boardWriteForm(){
        return "boardwrite";
        // return 쌍따옴표 안에 들어가는 것은어떤 html 파일(view 파일)로 보내줄 것인지
    }
}
