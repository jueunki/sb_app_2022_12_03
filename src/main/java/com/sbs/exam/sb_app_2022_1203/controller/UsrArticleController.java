package com.sbs.exam.sb_app_2022_1203.controller;

import com.sbs.exam.sb_app_2022_1203.service.ArticleService;
import com.sbs.exam.sb_app_2022_1203.util.Ut;
import com.sbs.exam.sb_app_2022_1203.vo.Article;
import com.sbs.exam.sb_app_2022_1203.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UsrArticleController {
  @Autowired
  private ArticleService articleService;


  // 액션 메서드 시작
  @RequestMapping("/user/article/doAdd")
  @ResponseBody
  public ResultData<Article> doAdd(String title, String body) {
    if(Ut.empty(title)) {
      return ResultData.from("F-1","title(을)를 입력해주세요.");
    }

    if(Ut.empty(body)) {
      return ResultData.from("F-2","body(을)를 입력해주세요.");
    }
    // ResultData<Integer> 의 뜻 : 제너릭을 재정의하는것?
    // <Integer>를 쓴 이유 : 데이터 타입이 int인것을 return할것인데, int로 형변환을 해줄 필요가 없게된다.
    ResultData<Integer> writerArticleRd = articleService.writeArticle(title, body);

    int id = writerArticleRd.getData1();
    Article article = articleService.getArticle(id);

    // Data만 바꿔서 브라우저로 넘기는 과정
    return ResultData.newData(writerArticleRd, article);
  }


  @RequestMapping("/user/article/getArticles")
  @ResponseBody   //자바에서는 형태를 자세히 써주는것이 좋아서 이러한 타입을 리턴한다라는 뜻으로 적어준다.
  public ResultData<List<Article>> getArticles() {
    List<Article> articles = articleService.getArticles();

    return ResultData.from("S-1", "게시물 리스트 입니다.", articles);
  }

  @RequestMapping("/user/article/getArticle")
  @ResponseBody
  public ResultData<Article> getArticle(int id) { //상세보기는 하나만 가져오는것이기 때문에 복수와 단수를 정확하게 표현해줘야한다.
    Article article = articleService.getArticle(id);

    if (article == null) {

      return ResultData.from( "F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    return ResultData.from( "S-1", Ut.f("%d번 게시물 입니다.", id), article);
    //String data type과 Int data type을 둘다 허용 하려면 둘의 상위type인 Object로 사용해준다(별로 좋은 방법은 아닙니다.)
  }

  @RequestMapping("/user/article/doDelete")
  @ResponseBody
  public ResultData<Integer> doDelete(int id) {
    Article article = articleService.getArticle(id);
    if (article == null) {
      return ResultData.from( "F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));

    }

    articleService.deleteArticle(id);

    return ResultData.from( "S-1", Ut.f("%d번 게시물을 삭제하였습니다.", id), id);
  }

  @RequestMapping("/user/article/doModify")
  @ResponseBody
  public ResultData<Integer> doModify(int id, String title, String body) {
    Article article = articleService.getArticle(id);

    if (article == null) {
      return ResultData.from( "F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    articleService.ModifyArticle(id, title, body);  //여기에 요청을 넣는것이다.

    return ResultData.from( "S-1", Ut.f("%d번 게시물을 수정하였습니다.", id), id); //localhost:8081/user/article/doModify?id=1&title=제목1 수정&body=내용1 수정 이라고적으면 수정이 된다.
  }


  // 액션 메서드 끝

}