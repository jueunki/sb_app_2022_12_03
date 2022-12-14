package com.sbs.exam.sb_app_2022_1203.controller;

import com.sbs.exam.sb_app_2022_1203.service.ArticleService;
import com.sbs.exam.sb_app_2022_1203.service.BoardService;
import com.sbs.exam.sb_app_2022_1203.service.ReactionPointService;
import com.sbs.exam.sb_app_2022_1203.service.ReplyService;
import com.sbs.exam.sb_app_2022_1203.util.Ut;
import com.sbs.exam.sb_app_2022_1203.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UsrArticleController {

  private ArticleService articleService;
  private BoardService boardService;

  private ReplyService replyService;
  private ReactionPointService reactionPointService;
  private Rq rq;

  public UsrArticleController(ArticleService articleService, BoardService boardService, ReactionPointService reactionPointService, ReplyService replyService, Rq rq) {
    this.articleService = articleService;
    this.boardService = boardService;
    this.reactionPointService = reactionPointService;
    this.replyService = replyService;
    this.rq = rq;
  }


  @RequestMapping("/usr/article/write")
  public String showWrite(HttpServletRequest req) {
    return "usr/article/write";
  }

  // 액션 메서드 시작
  @RequestMapping("/usr/article/doWrite")
  @ResponseBody
  public String doWrite(int boardId, String title, String body, String replaceUri) {

    if (rq.isLogined() == false) {
      return rq.jsHistoryBack("로그인 후 이용해주세요.");
    }

    if (Ut.empty(title)) {
      return rq.jsHistoryBack("title(을)를 입력해주세요.");
    }

    if (Ut.empty(body)) {
      return rq.jsHistoryBack("body(을)를 입력해주세요.");
    }
    // ResultData<Integer> 의 뜻 : 제너릭을 재정의하는것?
    // <Integer>를 쓴 이유 : 데이터 타입이 int인것을 return할것인데, int로 형변환을 해줄 필요가 없게된다.
    ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);
    int id = writeArticleRd.getData1();

    if (Ut.empty(replaceUri)) {
      replaceUri = Ut.f("../article/detail?id=%d", id);
    }

    // Data만 바꿔서 브라우저로 넘기는 과정
    return rq.jsReplace(Ut.f("%d번 게시물이 생성되었습니다.", id), replaceUri);
  }


  @RequestMapping("/usr/article/list")
  public String showList(Model model, @RequestParam(defaultValue = "1") int boardId, @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "title,body") String searchKeywordTypeCode, @RequestParam(defaultValue = "") String searchKeyword) {
    Board board = boardService.getBoardById(boardId);

    if (board == null) {
      return rq.historyBackJsOnView(Ut.f("%d번 게시판은 존재하지 않습니다.", boardId));
    }

    int articlesCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);

    int itemsCountInAPage = 10; //아이템 카운터를 10개 까지 한다는 뜻.
    int pagesCount = (int) Math.ceil((double) articlesCount / itemsCountInAPage); //만약에 글이 20 페이지가 있으면 2 페이지 27개의 글이 있으면 3페이지가 보이게 하는것
    List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId(), boardId, searchKeywordTypeCode, searchKeyword, itemsCountInAPage, page);

    model.addAttribute("boardId", boardId);
    model.addAttribute("pagesCount", pagesCount); // 이렇게 값을 넘겨줘야한다.
    model.addAttribute("page", page);
    model.addAttribute("articlesCount", articlesCount);
    model.addAttribute("articles", articles);

    return "/usr/article/list";

  }

  @RequestMapping("/usr/article/detail")
  public String showDetail(Model model, int id) { //상세보기는 하나만 가져오는것이기 때문에 복수와 단수를 정확하게 표현해줘야한다.

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    model.addAttribute("article", article); //->req.setAttribute();와 같은 의미이며 이는 jsp할때 이렇게 쓰는것이고 현재 쓰는것은 Spring boot 할 때 쓰는것이다.
    ResultData actorCanMakeReactionPointRd = reactionPointService.actorCanMakeReactionPoint(rq.getLoginedMemberId(), "article", id);

    model.addAttribute("actorCanMakeReaction", actorCanMakeReactionPointRd.isSuccess());

    List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMember(), "article", id);
    model.addAttribute("replies", replies);

    if (actorCanMakeReactionPointRd.getResultCode().equals("F-2")) {  // 좋아요를 할 수 있느냐에서 실패를 한다면 F-2와 같이 액션을 취했기때문에 인데
      int sumReactionPointByMemberId = (int)actorCanMakeReactionPointRd.getData1();

      if (sumReactionPointByMemberId > 0) {
        model.addAttribute("actorCanCancelGoodReaction", true);
      }
      else {
        model.addAttribute("actorCanCancelBadReaction", true);

      }
    }

    return "usr/article/detail";
    //String data type과 Int data type을 둘다 허용 하려면 둘의 상위type인 Object로 사용해준다(별로 좋은 방법은 아닙니다.)
  }

  @RequestMapping("/usr/article/doIncreaseHitCountRd") // 조회수 알아보기
  @ResponseBody
  public ResultData<Integer> doIncreaseHitCountRd(int id) {
    ResultData<Integer> increaseHitCountRd = articleService.increaseHitCount(id);

    if (increaseHitCountRd.isFail()) {
      return increaseHitCountRd;
    }

    ResultData<Integer> rd = ResultData.newData(increaseHitCountRd,"hitCount", articleService.getArticleHitCount(id));

    rd.setData2("id", id);

    return rd;
    }

  @RequestMapping("/usr/article/doDelete")
  @ResponseBody
  public String doDelete(int id) {
    if (rq.isLogined() == false) {
      return rq.jsHistoryBack("로그인 후 이용해주세요.");
    }
    // 로그인 하지않았을시 게시물 삭제가 되지않게 하는 부분.
    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    if (article == null) {
      return rq.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    //경고창을 띄어주는 부분.
    if (article.getMemberId() != rq.getLoginedMemberId()) {
      return rq.jsHistoryBack("권한이 없습니다.");
    }
    articleService.deleteArticle(id);

    return rq.jsReplace(Ut.f("%d번 게시물을 삭제하였습니다.", id), "../article/list"); //->uri를 리스트로 넘겨주는 부분.
  }

  @RequestMapping("/usr/article/modify")
  public String showModify(Model model, int id) {
    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    if (article == null) {
      return rq.historyBackJsOnView(Ut.f("%d번 게시물이 존재하지 않습니다.", id)); //여기서 @ResponseBody가 없으면 jsp를 찾는 역할을 한다.
    }
    ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article); //rq.getrq.getLoginedMemberId()()가 article을 수정할 수 있냐는 뜻

    if (actorCanModifyRd.isFail()) {  //수정 할 수 없다는 뜻.
      return rq.historyBackJsOnView(actorCanModifyRd.getMsg());
    }
    model.addAttribute("article", article);
    return "usr/article/modify";
  }

  @RequestMapping("/usr/article/doModify")
  @ResponseBody
  public String doModify(int id, String title, String body) {
    if (rq.isLogined() == false) {
      return rq.jsHistoryBack("로그인 후 이용해주세요.");
    }
    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    if (article.getMemberId() != rq.getLoginedMemberId()) {
      return rq.jsHistoryBack("권한이 없습니다."); //월권이기 때문에 서비스에게 넘기는것이 좋다.
    }

    if (article == null) {
      return rq.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article); //rq.getrq.getLoginedMemberId()()가 article을 수정할 수 있다는 뜻

    if (actorCanModifyRd.isFail()) {
      return rq.jsHistoryBack(actorCanModifyRd.getMsg());
    }
    //여기에 요청을 넣는것이다.

    articleService.modifyArticle(id, title, body); //localhost:8081/usr/article/doModify?id=1&title=제목1 수정&body=내용1 수정 이라고적으면 수정이 된다.
    return rq.jsReplace(Ut.f("%d번 게시물이 수정되었습니다.", id), Ut.f("../article/detail?id=%d", id)); //게시물 수정 후 자바 스크립트로 후 처리(수정 했을때 메시지 뜰 수 있게 만들어 주는 부분)
  }
  //여기까지 오면 수정이 가능하다는것.
}
// 액션 메서드 끝