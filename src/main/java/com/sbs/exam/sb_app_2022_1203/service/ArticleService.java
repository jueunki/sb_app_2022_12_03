package com.sbs.exam.sb_app_2022_1203.service;

import com.sbs.exam.sb_app_2022_1203.repository.ArticleRepository;
import com.sbs.exam.sb_app_2022_1203.vo.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

  private ArticleRepository articleRepository;

  public ArticleService(ArticleRepository articleRepository){
    this.articleRepository = articleRepository; //@Autowired를 지워주고 생성자 주입해준다.

  }


  public int writeArticle(String title, String body) {

    articleRepository.writeArticle(title, body);
    return articleRepository.getLastInsertId();
  }

  public List<Article> getArticles() {
    return articleRepository.getArticles();
  }

  public Article getArticle(int id) {
    return articleRepository.getArticle(id);
  }

  public void deleteArticle(int id) {
    articleRepository.deleteArticle(id);
  }

  public void ModifyArticle(int id, String title, String body) {
    articleRepository.ModifyArticle(id, title,body);
  }
}
