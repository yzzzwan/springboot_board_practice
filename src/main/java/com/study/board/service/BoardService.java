package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.entity.BoardFile;
import com.study.board.entity.User;
import com.study.board.repository.BoardFileRepository;
import com.study.board.repository.BoardRepository;
import com.study.board.repository.UserRepository;
import jakarta.transaction.Transactional;
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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardFileRepository boardFileRepository;
    @Autowired
    private UserRepository userRepository;

    private String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

    // 게시글 작성

    public void boardWrite(Board board, MultipartFile file) throws Exception{
        User user = new User();
        user = getUserById("admin");


        board.setUser(user);
        Board savedBoard = boardRepository.save(board);

       saveBoardFile(file, savedBoard);
    }

    @Transactional
    public void boardModify(Board board, MultipartFile file) throws Exception{
        int boardId = board.getBoardId();

        User user = new User();
        user = getUserById("admin");
        board.setUser(user);
        Board savedBoard = boardRepository.save(board);

        boardFileDelete(boardId);
        saveBoardFile(file, savedBoard);
    }

    public void saveBoardFile(MultipartFile file, Board board) throws Exception{
        if(!file.isEmpty()) {
            // 프로젝트의 root 디렉토리
            UUID uuid = UUID.randomUUID();
            String originalFileName = file.getOriginalFilename();
            String fileName = uuid + "_" + file.getOriginalFilename();

            // 확장자 검사
            if(!checkFileExtension(fileName)){
                throw new IllegalArgumentException("허용되지 않는 파일 확장자입니다.");
            }

            File saveFile = new File(projectPath,fileName);
            file.transferTo(saveFile); // 파일을 로컬에 저장

            // 파일 정보를 db에 저장
            BoardFile boardFile = new BoardFile();
            boardFile.setBoard(board);
            boardFile.setOriginalFilename(originalFileName);
            boardFile.setFilename(fileName);
            boardFile.setFilepath("/files/" + fileName);
            boardFileRepository.save(boardFile);
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

    // 특정 게시글의 파일 불러오기
    public BoardFile boardFileGet(Integer board_id){
        return boardFileRepository.findByBoard_BoardId(board_id).orElse(null);
    }

    @Transactional
    public void boardDelete(Integer boardId) throws Exception{
        boardFileDelete(boardId);
        boardRepository.deleteById(boardId);
    }

    @Transactional
    public void boardFileDelete(Integer boardId){
        if(boardFileRepository.existsByBoard_BoardId(boardId)){
            boardFileRepository.deleteByBoard_BoardId(boardId);
        }
    }

    public boolean checkFileExtension(String filename){
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

        if(Set.of("txt", "jpg", "png", "jfif").contains(extension))return true;
        else return false;

    }

    public User getUserById(String id){
        return userRepository.findByLoginId(id);
    }

}
