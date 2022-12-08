package com.sbs.exam.sb_app_2022_1203.article.controller;

import com.sbs.exam.sb_app_2022_1203.article.service.ArticleService;
import com.sbs.exam.sb_app_2022_1203.article.vo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UsrArticleController {
  // 인스턴스 변수 시작
  @Autowired  //컴포넌트로 등록되는 것들에게 붙여주면 된다.
  private ArticleService articleService;
  int articleLastId;

  // 인스턴스 변수 끝

  // 생성자

  private List<Article> articles;

  public UsrArticleController() {
    articles = new ArrayList<>();
    articleLastId = 0;

    makeTestDate();
  }

  // 서비스 메서드 시작
  private void makeTestDate() {

    for (int i = 1; i <= 10; i++) {
      String title = "제목" + i;
      String body = "내용" + i;

      writeArticle(title, body);
    }
  }

  private Article getArticle(int id) {

    for (Article article : articles) {
      if (article.getId() == id) {
        return article;
      }
    }
    return null;
  }

  private Article writeArticle(String title, String body) {
    int id = articleLastId + 1;
    Article article = new Article(id, title, body);

    articles.add(article);
    articleLastId = id;
    return article;
  }

  private void deleteArticle(int id) {
    Article article = getArticle(id);
    articles.remove(article);
  }

  private void ModifyArticle(int id, String title, String body) {
    Article article = getArticle(id);

    article.setTitle(title);
    article.setBody(body);

  }
  // 서비스 메서드 끝


  @RequestMapping("/user/article/doAdd")
  @ResponseBody
  public Article doAdd(String title, String body) {
    Article article = writeArticle(title, body);
    return article;
  }


  @RequestMapping("/user/article/getArticles")
  @ResponseBody
  public List<Article> getArticles() {
    return articles;
  }

  @RequestMapping("/user/article/getArticle")
  @ResponseBody
  public Object getArticleAction(int id) { //상세보기는 하나만 가져오는것이기 때문에 복수와 단수를 정확하게 표현해줘야한다.
    Article article = getArticle(id);

    if (article == null) {
      return id + "번 게시물이 존재하지 않습니다.";
    }

    return article;
    //String data type과 Int data type을 둘다 허용 하려면 둘의 상위type인 Object로 사용해준다(별로 좋은 방법은 아닙니다.)
  }

  @RequestMapping("/user/article/doDelete")
  @ResponseBody
  public String doDelete(int id) {
    Article article = getArticle(id);
    if (article == null) {
      return id + "번 게시물이 존재하지 않습니다.";
    }

    deleteArticle(id);

    return id + "번 게시물을 삭제하였습니다.";
  }

  @RequestMapping("/user/article/doModify")
  @ResponseBody
  public String doModify(int id, String title, String body) {
    Article article = getArticle(id);

    if (article == null) {
      return id + "번 게시물이 존재하지 않습니다.";
    }

    ModifyArticle(id, title, body);

    return id + "번 게시물을 수정하였습니다."; //localhost:8081/user/article/doModify?id=1&title=제목1 수정&body=내용1 수정 이라고적으면 수정이 된다.
  }



  // 액션 메서드 끝

}