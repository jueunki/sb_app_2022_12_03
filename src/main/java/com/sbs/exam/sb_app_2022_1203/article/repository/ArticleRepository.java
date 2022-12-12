package com.sbs.exam.sb_app_2022_1203.article.repository;

import com.sbs.exam.sb_app_2022_1203.article.vo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleRepository {

  // INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = ?, `body` = ?
  public Article writeArticle(String title, String body);

  @Select("SELECT * FROM article WHERE id = #{id}")
  // SELECE * FROM article WHERE id = ? 이라고 해야 id를 가져올 수 있다.
  public Article getArticle(int id);
  // DELETE FROM article WHERE id = ?
  public void deleteArticle(int id);

  // SELECT * FROM article ORDER BY id DESC;
  public List<Article> getArticles();

  // UPDATE article SET title = ?, `body` = ? updateDate = NOW() WHERE id = ?

  public void ModifyArticle(int id, String title, String body);
}

