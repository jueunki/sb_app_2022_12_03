package com.sbs.exam.sb_app_2022_1203.controller;

import com.sbs.exam.sb_app_2022_1203.service.ReplyService;
import com.sbs.exam.sb_app_2022_1203.util.Ut;
import com.sbs.exam.sb_app_2022_1203.vo.ResultData;
import com.sbs.exam.sb_app_2022_1203.vo.Rq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrReplyController {
  private ReplyService replyService;
  private Rq rq;

  public UsrReplyController(ReplyService replyService, Rq rq) {
    this.replyService = replyService;
    this.rq = rq;
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
     ResultData<Integer> writeArticleRd = replyService.writeArticle(rq.getLoginedMemberId(), relTypeCode, relId, body);
    int id = writeArticleRd.getData1();

    switch (relTypeCode) {
      case "article":
        replaceUri = Ut.f("../article/detail?id=%d", relId);
        break;
    }

    return rq.jsReplace(writeArticleRd.getMsg(), replaceUri);
  }
}
