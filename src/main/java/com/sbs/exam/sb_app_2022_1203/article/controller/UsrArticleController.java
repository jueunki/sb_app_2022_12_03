package com.sbs.exam.sb_app_2022_1203.article.controller;

import com.sbs.exam.sb_app_2022_1203.article.vo.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UsrArticleController {
  int articleLastId;

  private List<Article> articles;
  public UsrArticleController(){
    articleLastId = 0;
    articles = new ArrayList<>();
  }

  @RequestMapping("/user/article/doAdd")
  @ResponseBody
  public Article doAdd(String title, String body) {
    int id = articleLastId + 1;
    Article article = new Article(id, title, body);
    articles.add(article);
    articleLastId = id;
    return article;
  }

  @RequestMapping("/user/article/getArticles")
  @ResponseBody
  public List<Article> getArticles() {
    return articles;
  }
}