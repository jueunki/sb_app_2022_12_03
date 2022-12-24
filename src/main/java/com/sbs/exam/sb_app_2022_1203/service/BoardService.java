package com.sbs.exam.sb_app_2022_1203.service;

import com.sbs.exam.sb_app_2022_1203.repository.BoardRepository;
import com.sbs.exam.sb_app_2022_1203.vo.Board;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  private BoardRepository boardRepository;

  public BoardService(BoardRepository boardRepository) {
    this.boardRepository = boardRepository; //@Autowired를 지워주고 생성자 주입해준다.

  }


  public Board getBoardById(int id) {
    return boardRepository.getBoardById(id);
  }
}