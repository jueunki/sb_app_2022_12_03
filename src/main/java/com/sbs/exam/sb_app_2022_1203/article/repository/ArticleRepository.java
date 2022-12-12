package com.sbs.exam.sb_app_2022_1203.article.repository;

import com.sbs.exam.sb_app_2022_1203.article.vo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleRepository {

  @Insert("INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = #{title}, `body` = #{body}")
  // INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = ?, `body` = ?
  public void writeArticle(String title, String body);

  @Select("SELECT * FROM article WHERE id = #{id}")
  // SELECE * FROM article WHERE id = ? 이라고 해야 id를 가져올 수 있다.
  public Article getArticle(int id);
  @Delete("DELETE FROM article WHERE id = #{id}")
  // DELETE FROM article WHERE id = ?
  public void deleteArticle(int id);


  @Select("SELECT * FROM article ORDER BY id DESC;")
  // SELECT * FROM article ORDER BY id DESC;
  public List<Article> getArticles();

  @Update("UPDATE article SET title = #{title}, `body` =  #{body}, updateDate = NOW() WHERE id =  #{id}")
  // UPDATE article SET title = ?, `body` = ? updateDate = NOW() WHERE id = ?

  public void ModifyArticle(int id, String title, String body);

  @Select("SELECT LAST_INSERT_ID()")
  public int getLastInsertId();
}

