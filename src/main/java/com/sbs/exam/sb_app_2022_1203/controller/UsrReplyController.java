package com.sbs.exam.sb_app_2022_1203.controller;

import com.sbs.exam.sb_app_2022_1203.service.ArticleService;
import com.sbs.exam.sb_app_2022_1203.service.ReplyService;
import com.sbs.exam.sb_app_2022_1203.util.Ut;
import com.sbs.exam.sb_app_2022_1203.vo.Article;
import com.sbs.exam.sb_app_2022_1203.vo.Reply;
import com.sbs.exam.sb_app_2022_1203.vo.ResultData;
import com.sbs.exam.sb_app_2022_1203.vo.Rq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrReplyController {
  private ReplyService replyService;
  private ArticleService articleService;
  private Rq rq;


  public UsrReplyController(ReplyService replyService, ArticleService articleService, Rq rq) {
    this.replyService = replyService;
    this.articleService = articleService;
    this.rq = rq;
  }

  @RequestMapping("/user/reply/doDelete")
  @ResponseBody
  public String doDelete(int id, String replaceUri) {
    if (Ut.empty(id)) {
      return rq.jsHistoryBack("id(을)를 입력해주세요.");
    }

    Reply reply = replyService.getForPrintReply(rq.getLoginedMember(), id);
    if (reply == null) {
      return rq.jsHistoryBack(Ut.f("%d 번 댓글은 존재하지 않습니다.", id));
    }
    if (reply.isExtra__actorCanDelete() == false) {
      return rq.jsHistoryBack(Ut.f("%d번 댓글을 삭제 할 권한이 없습니다.", id));
    }

    ResultData deleteReplyRd = replyService.deleteReply(id);

    if (Ut.empty(replaceUri)) {
      switch (reply.getRelTypeCode()) {
        case "article":
          replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
          break;
      }


    }

    return rq.jsReplace(deleteReplyRd.getMsg(), replaceUri);
  }

  @RequestMapping("/user/reply/modify")
  public String doDelete(int id, String replaceUri, Model model) {
    if (Ut.empty(id)) {
      return rq.jsHistoryBack("id(을)를 입력해주세요.");
    }

    Reply reply = replyService.getForPrintReply(rq.getLoginedMember(), id);

    if (reply == null) {
      return rq.historyBackJsOnView(Ut.f("%d 번 댓글은 존재하지 않습니다.", id));
    }
    if (reply.isExtra__actorCanModify() == false) {
      return rq.historyBackJsOnView(Ut.f("%d번 댓글을 수정 할 권한이 없습니다.", id));
    }

    String relDataTitle = null;

    switch (reply.getRelTypeCode()) {
      case "article":
        Article article = articleService.getArticle(reply.getRelId());
        relDataTitle = article.getTitle();
    }

    model.addAttribute("relDataTitle", relDataTitle);
    model.addAttribute("reply", reply);

    return "user/reply/modify";
  }


  @RequestMapping("/user/reply/doWrite")
  @ResponseBody
  public String doWrite(String relTypeCode, int relId, String body, String replaceUri) {
    if (Ut.empty(relTypeCode)) {
      return rq.jsHistoryBack("relTypeCode(을)를 입력해주세요.");
    }

    if (Ut.empty(relId)) {
      return rq.jsHistoryBack("relId(을)를 입력해주세요.");
    }
     ResultData<Integer> writeArticleRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);
    int id = writeArticleRd.getData1();

    switch (relTypeCode) {
      case "article":
        replaceUri = Ut.f("../article/detail?id=%d", relId);
        break;
    }

    return rq.jsReplace(writeArticleRd.getMsg(), replaceUri);
  }
}
