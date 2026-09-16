package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    private String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

    // 게시글 작성
    public void boardWrite(Board board, MultipartFile file) throws Exception{
        String prevFileName =  board.getFilename(); // 덮어쓰기 전에 기존 파일명 미리 저장
        if(!file.isEmpty()){
                                  // 프로젝트의 root 디렉토리
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

        // 새 파일과 새 파일 정보 DB 저장 후 이전 파일 삭제
        if(!file.isEmpty() && StringUtils.hasText(prevFileName)) {
            Files.deleteIfExists(Path.of(projectPath, prevFileName));
        }

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

    public void boardDelete(Integer id) throws Exception{
        Board board = boardView(id);

        boardRepository.deleteById(id);

        if(StringUtils.hasText(board.getFilename())) {
            Files.deleteIfExists(Path.of(projectPath, board.getFilename()));
        }


    }
}
