package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    // 게시글 작성
    public void boardWrite(Board board, MultipartFile file) throws Exception{

        if(!file.isEmpty()){
                                  // 프로젝트의 root 디렉토리
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
            UUID uuid = UUID.randomUUID();
            String originalFileName = file.getOriginalFilename();
            String fileName = uuid + "_" + file.getOriginalFilename();
            File saveFile = new File(projectPath,fileName);
            file.transferTo(saveFile);

            board.setOriginal_filename(originalFileName);
            board.setFilename(fileName);
            board.setFilepath("/files/" + fileName);
        }


        boardRepository.save(board);
    }

    // 게시글 리스트 불러오기
    public Page<Board> boardList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Page<Board> boardSearchTitleList(String searchKeywordTitle, Pageable pageable){
        return boardRepository.findByTitleContaining(searchKeywordTitle, pageable);
    }

    public Page<Board> boardSearchContentList(String searchKeywordContent, Pageable pageable){
        return boardRepository.findByContentContaining(searchKeywordContent, pageable);
    }

    public Page<Board> boardSearchTotalList(String searchKeywordTotal, Pageable pageable){
        return boardRepository.findByTitleContainingOrContentContaining(searchKeywordTotal, searchKeywordTotal, pageable);
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id){

        return boardRepository.findById(id).get();
    }

    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }
}
