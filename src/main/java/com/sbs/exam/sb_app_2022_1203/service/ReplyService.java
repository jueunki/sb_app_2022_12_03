package com.sbs.exam.sb_app_2022_1203.service;

import com.sbs.exam.sb_app_2022_1203.repository.ReplyRepository;
import com.sbs.exam.sb_app_2022_1203.util.Ut;
import com.sbs.exam.sb_app_2022_1203.vo.Reply;
import com.sbs.exam.sb_app_2022_1203.vo.ResultData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
  private ReplyRepository replyRepository;

  public ReplyService(ReplyRepository replyRepository) {
    this.replyRepository = replyRepository;
  }

  public ResultData<Integer> writeArticle(int actorId, String relTypeCode, int relId, String body) {
    replyRepository.writeArticle(actorId, relTypeCode, relId, body);
    int id = replyRepository.getLastInsertId();

    // Service(여기)에서 Controller에게 넘기는 과정
    return ResultData.from("S-1", Ut.f("%d번 댓글이 생성되었습니다.", id), "id", id);
  }

  public List<Reply> getForPrintReplies(Member actor, String relTypeCode, int relId) {
    return replyRepository.getForPrintReplies(relTypeCode, relId);
  }
}
