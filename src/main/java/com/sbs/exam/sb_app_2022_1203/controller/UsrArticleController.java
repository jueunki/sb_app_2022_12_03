package com.sbs.exam.sb_app_2022_1203.controller;

import com.sbs.exam.sb_app_2022_1203.service.ArticleService;
import com.sbs.exam.sb_app_2022_1203.util.Ut;
import com.sbs.exam.sb_app_2022_1203.vo.Article;
import com.sbs.exam.sb_app_2022_1203.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsrArticleController {
  @Autowired
  private ArticleService articleService;


  // 액션 메서드 시작
  @RequestMapping("/user/article/doAdd")
  @ResponseBody
  public ResultData<Article> doAdd(HttpSession httpSession, String title, String body) {
    boolean isLogined = false;
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null) {
      // 로그인 되어있는 상태
      isLogined = true;
      loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
    }
    if (isLogined == false) {
      return ResultData.from("F-A", "로그인 후 이용해주세요.");
    }

    if (Ut.empty(title)) {
      return ResultData.from("F-1", "title(을)를 입력해주세요.");
    }

    if (Ut.empty(body)) {
      return ResultData.from("F-2", "body(을)를 입력해주세요.");
    }
    // ResultData<Integer> 의 뜻 : 제너릭을 재정의하는것?
    // <Integer>를 쓴 이유 : 데이터 타입이 int인것을 return할것인데, int로 형변환을 해줄 필요가 없게된다.
    ResultData<Integer> writeArticleRd = articleService.writeArticle(loginedMemberId, title, body);
    int id = writeArticleRd.getData1();

    Article article = articleService.getArticle(id);

    // Data만 바꿔서 브라우저로 넘기는 과정
    return ResultData.newData(writeArticleRd, "article", article); //데이터만 보여주는것이 아니라 데이터 타입도 보여주는것.
  }


  @RequestMapping("/user/article/list")
  public String showList(Model model) {
    List<Article> articles = articleService.getArticles();

    model.addAttribute("articles", articles);

    return "/user/article/list";

  }

  @RequestMapping("/user/article/getArticle")
  @ResponseBody
  public ResultData<Article> getArticle(int id) { //상세보기는 하나만 가져오는것이기 때문에 복수와 단수를 정확하게 표현해줘야한다.
    Article article = articleService.getArticle(id);

    if (article == null) {

      return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    return ResultData.from("S-1", Ut.f("%d번 게시물 입니다.", id),"article", article);
    //String data type과 Int data type을 둘다 허용 하려면 둘의 상위type인 Object로 사용해준다(별로 좋은 방법은 아닙니다.)
  }

  @RequestMapping("/user/article/doDelete")
  @ResponseBody
  public ResultData<Integer> doDelete(HttpSession httpSession, int id) {
    boolean isLogined = false;
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null) {
      // 로그인 되어있는 상태
      isLogined = true;
      loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
    }
    if (isLogined == false) {
      return ResultData.from("F-A", "로그인 후 이용해주세요.");
    }
    Article article = articleService.getArticle(id);

    if (article.getMemberId() != loginedMemberId) {
      return ResultData.from("F-2", "권한이 없습니다.");
    }

    if (article == null) {
      return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));

    }

    articleService.deleteArticle(id);

    return ResultData.from("S-1", Ut.f("%d번 게시물을 삭제하였습니다.", id),"id", id);
  }

  @RequestMapping("/user/article/doModify")
  @ResponseBody
  public ResultData<Article> doModify(HttpSession httpSession, int id, String title, String body) {
    boolean isLogined = false; //변수가 isLogined라는게 체킹되려면 이러한 변수를 셋팅해줘야한다.
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null) {
      // 로그인 되어있는 상태
      isLogined = true;
      loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
    }
    if (isLogined == false) {
      return ResultData.from("F-A", "로그인 후 이용해주세요.");
    }
    Article article = articleService.getArticle(id);

    if (article.getMemberId() != loginedMemberId) {
      return ResultData.from("F-2", "권한이 없습니다."); //월권이기 때문에 서비스에게 넘기는것이 좋다.
    }

    if (article == null) {
      return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    ResultData actorCanModifyRd = articleService.actorCanModify(loginedMemberId, article); //loginedMemberId가 article을 수정할 수 있다는 뜻

    if (actorCanModifyRd.isFail()) {  //
      return actorCanModifyRd;
    }
    //여기에 요청을 넣는것이다.

    return articleService.modifyArticle(id, title, body); //localhost:8081/user/article/doModify?id=1&title=제목1 수정&body=내용1 수정 이라고적으면 수정이 된다.
    //여기까지 오면 수정이 가능하다는것.
  }


  // 액션 메서드 끝

}