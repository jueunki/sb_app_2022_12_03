package com.sbs.exam.sb_app_2022_1203.article.service;

import com.sbs.exam.sb_app_2022_1203.article.repository.ArticleRepository;
import com.sbs.exam.sb_app_2022_1203.article.vo.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

  private ArticleRepository articleRepository;

  public ArticleService(ArticleRepository articleRepository){
    this.articleRepository = articleRepository; //@Autowired를 지워주고 생성자 주입해준다.

  }


  public Article writeArticle(String title, String body) {
    return articleRepository.writeArticle(title, body);
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
