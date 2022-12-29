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
    List<Reply> replies = replyRepository.getForPrintReplies(relTypeCode, relId);

    for (Reply reply : replies) {
      updateForPrintData(actor, reply);
    }
    return replies;
  }

  private void updateForPrintData(Member actor, Reply reply) {
    if (reply == null) {
      return;
    }

    ResultData actorCanDeleteRd = actorCanDelete(actor, reply);
    reply.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());

    ResultData actorCanModifyRd = actorCanModify(actor, reply);
    reply.setExtra__actorCanModify(actorCanModifyRd.isSuccess());
  }

  private ResultData actorCanModify(Member actor, Reply reply) {
    if (reply == null) {
      return ResultData.from("F-1", "댓글이 존재하지 않습니다.");
    }
    if (reply.getMemberId() != actor.getId()) {
      return ResultData.from("F-2", "권한이 없습니다.");
    }
    return ResultData.from("S-1", "댓글 수정이 가능합니다.");
  }

  private ResultData actorCanDelete(Member actor, Reply reply) {
    if (reply == null) {
      return ResultData.from("F-1", "댓글이 존재하지 않습니다.");
    }
    if (reply.getMemberId() != actor.getId()) {
      return ResultData.from("F-2", "권한이 없습니다.");
    }
    return ResultData.from("S-1", "댓글 삭제가 가능합니다.");
  }
}

