package com.sbs.exam.sb_app_2022_1203.controller;

import com.sbs.exam.sb_app_2022_1203.service.ReactionPointService;
import com.sbs.exam.sb_app_2022_1203.vo.ResultData;
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
    ResultData actorCanMakeReactionPointRd = reactionPointService.actorCanMakeReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

    if(actorCanMakeReactionPointRd.isFail()) {
      return  rq.jsHistoryBack(actorCanMakeReactionPointRd.getMsg());
    }
   ResultData addGoodReactionPointRd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

   return rq.jsReplace(addGoodReactionPointRd.getMsg(), replaceUri);
  }
  @RequestMapping("/user/reactionPoint/doBadReaction")
  @ResponseBody
  String doBadReaction(String relTypeCode, int relId,String replaceUri) {
    ResultData actorCanMakeReactionPointRd = reactionPointService.actorCanMakeReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

    if(actorCanMakeReactionPointRd.isFail()) {
      return  rq.jsHistoryBack(actorCanMakeReactionPointRd.getMsg());
    }

    ResultData addBadReactionPointRd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

    return rq.jsReplace(addBadReactionPointRd.getMsg(), replaceUri);
  }

  @RequestMapping("/user/reactionPoint/doCancelGoodReaction")
  @ResponseBody
  String doCancelGoodReaction(String relTypeCode, int relId, String replaceUri) {
    ResultData actorCanMakeReactionPointRd = reactionPointService.actorCanMakeReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

    if(actorCanMakeReactionPointRd.isSuccess()) { // 내가 좋아요 싫어요 버튼을 누를수 있는단계
      return  rq.jsHistoryBack("이미 취소되었습니다.");
    }
    ResultData deleteGoodReactionPointRd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

    return rq.jsReplace(deleteGoodReactionPointRd.getMsg(), replaceUri);
  }

  @RequestMapping("/user/reactionPoint/doCancelBadReaction")
  @ResponseBody
  String doCancelBadReaction(String relTypeCode, int relId, String replaceUri) {
    ResultData actorCanMakeReactionPointRd = reactionPointService.actorCanMakeReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

    if(actorCanMakeReactionPointRd.isSuccess()) { // 내가 좋아요 싫어요 버튼을 누를수 있는단계
      return  rq.jsHistoryBack("이미 취소되었습니다.");
    }
    ResultData deleteBadReactionPointRd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

    return rq.jsReplace(deleteBadReactionPointRd.getMsg(), replaceUri);
  }
}
