package com.sbs.exam.sb_app_2022_1203.service;

import com.sbs.exam.sb_app_2022_1203.repository.ArticleRepository;
import com.sbs.exam.sb_app_2022_1203.util.Ut;
import com.sbs.exam.sb_app_2022_1203.vo.Article;
import com.sbs.exam.sb_app_2022_1203.vo.ResultData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

  private ArticleRepository articleRepository;

  public ArticleService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository; //@Autowired를 지워주고 생성자 주입해준다.

  }


  public ResultData writeArticle(int memberId, String title, String body) {

    articleRepository.writeArticle(memberId, title, body);
    int id = articleRepository.getLastInsertId();

    // Service(여기)에서 Controller에게 넘기는 과정
    return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), id);
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
    articleRepository.ModifyArticle(id, title, body);
  }
}
