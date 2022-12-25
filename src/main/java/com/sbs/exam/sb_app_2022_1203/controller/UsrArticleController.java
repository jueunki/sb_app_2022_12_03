package com.sbs.exam.sb_app_2022_1203.controller;

import com.sbs.exam.sb_app_2022_1203.service.ArticleService;
import com.sbs.exam.sb_app_2022_1203.service.BoardService;
import com.sbs.exam.sb_app_2022_1203.util.Ut;
import com.sbs.exam.sb_app_2022_1203.vo.Article;
import com.sbs.exam.sb_app_2022_1203.vo.Board;
import com.sbs.exam.sb_app_2022_1203.vo.ResultData;
import com.sbs.exam.sb_app_2022_1203.vo.Rq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UsrArticleController {

  private ArticleService articleService;
  private BoardService boardService;

  public UsrArticleController(ArticleService articleService, BoardService boardService) {
    this.articleService = articleService;
    this.boardService = boardService;
  }




  @RequestMapping("/user/article/write")
  public String showWrite(HttpServletRequest req) {
    return "user/article/write";
  }

  // 액션 메서드 시작
  @RequestMapping("/user/article/doWrite")
  @ResponseBody
  public String doWrite(HttpServletRequest req, String title, String body, String replaceUri) {
   Rq rq = (Rq) req.getAttribute("rq");

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
    ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);
    int id = writeArticleRd.getData1();

    if(Ut.empty(replaceUri)) {
      replaceUri = Ut.f("../article/detail?id=%d", id);
    }

    // Data만 바꿔서 브라우저로 넘기는 과정
    return rq.jsReplace(Ut.f("%d번 게시물이 생성되었습니다.", id), replaceUri);
  }


  @RequestMapping("/user/article/list")
  public String showList(HttpServletRequest req, Model model, int boardId) {
    Rq rq = (Rq) req.getAttribute("rq");

    Board board = boardService.getBoardById(boardId);

    if(board == null) {
      return rq.historyBackJsOnView(Ut.f("%d번 게시판은 존재하지 않습니다.", boardId));
    }

    int articlesCount = articleService.getArticlesCount(boardId);

    List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId(), boardId);

    model.addAttribute("board", board);
    model.addAttribute("articlesCount", articlesCount);
    model.addAttribute("articles", articles);

    return "/user/article/list";

  }

  @RequestMapping("/user/article/detail")
  public String showDetail(HttpServletRequest req, Model model, int id) { //상세보기는 하나만 가져오는것이기 때문에 복수와 단수를 정확하게 표현해줘야한다.
    Rq rq = (Rq) req.getAttribute("rq");

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

   model.addAttribute("article", article); //->req.setAttribute();와 같은 의미이며 이는 jsp할때 이렇게 쓰는것이고 현재 쓰는것은 Spring boot 할 때 쓰는것이다.

    return "user/article/detail";
    //String data type과 Int data type을 둘다 허용 하려면 둘의 상위type인 Object로 사용해준다(별로 좋은 방법은 아닙니다.)
  }

  @RequestMapping("/user/article/doDelete")
  @ResponseBody
  public String doDelete(HttpServletRequest req, int id) {
    Rq rq = (Rq) req.getAttribute("rq");
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

  @RequestMapping("/user/article/modify")
  public String showModify(HttpServletRequest req, Model model, int id) {
    Rq rq = (Rq) req.getAttribute("rq");

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    if (article == null) {
      return rq.historyBackJsOnView(Ut.f("%d번 게시물이 존재하지 않습니다.", id)); //여기서 @ResponseBody가 없으면 jsp를 찾는 역할을 한다.
    }
    ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article); //rq.getrq.getLoginedMemberId()()가 article을 수정할 수 있냐는 뜻

    if (actorCanModifyRd.isFail()) {  //수정 할 수 없다는 뜻.
      return rq.historyBackJsOnView(actorCanModifyRd.getMsg());
    }
    model.addAttribute("article", article);
    return "user/article/modify";
  }

    @RequestMapping("/user/article/doModify")
  @ResponseBody
  public String doModify(HttpServletRequest req, int id, String title, String body) {
      Rq rq = (Rq) req.getAttribute("rq");
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

      articleService.modifyArticle(id, title, body); //localhost:8081/user/article/doModify?id=1&title=제목1 수정&body=내용1 수정 이라고적으면 수정이 된다.
      return rq.jsReplace(Ut.f("%d번 게시물이 수정되었습니다.", id), Ut.f("../article/detail?id=%d", id)); //게시물 수정 후 자바 스크립트로 후 처리(수정 했을때 메시지 뜰 수 있게 만들어 주는 부분)
    }
      //여기까지 오면 수정이 가능하다는것.
  }
  // 액션 메서드 끝