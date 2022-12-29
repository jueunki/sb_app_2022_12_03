package com.sbs.exam.sb_app_2022_1203.controller;

import com.sbs.exam.sb_app_2022_1203.service.ReactionPointService;
import com.sbs.exam.sb_app_2022_1203.vo.Rq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrReactionPointController {
  private ReactionPointService reactionPointService;
  private Rq rq;

  public UsrReactionPointController(ReactionPointService reactionPointService, Rq rq) {
    this.reactionPointService = reactionPointService;
    this.rq = rq;
  }

  @RequestMapping("/user/reactionPoint/doGoodReaction")
  @ResponseBody
  String doGoodReaction(String relTypeCode, int relId, String replaceUri) {
   boolean actorCanMakeReactionPoint = reactionPointService.actorCanMakeReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId).isSuccess();

   if(actorCanMakeReactionPoint == false) {
     return  rq.jsHistoryBack("이미 처리되었습니다.");
   }
   reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

   return rq.jsReplace("좋아요를 하셨습니다.", replaceUri);
  }
  @RequestMapping("/user/reactionPoint/doBadReaction")
  @ResponseBody
  String doBadReaction(String relTypeCode, int relId,String replaceUri) {
    boolean actorCanMakeReactionPoint = reactionPointService.actorCanMakeReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId).isSuccess();

    if(actorCanMakeReactionPoint == false) {
      return  rq.jsHistoryBack("이미 처리되었습니다.");
    }

    reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

    return rq.jsReplace("싫어요를 하셨습니다.", replaceUri);
  }
}
