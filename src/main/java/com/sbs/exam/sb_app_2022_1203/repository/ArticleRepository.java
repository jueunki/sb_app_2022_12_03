package com.sbs.exam.sb_app_2022_1203.repository;

import com.sbs.exam.sb_app_2022_1203.vo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleRepository {

  @Insert("""
      INSERT INTO article
      SET regDate = NOW(),
      updateDate = NOW(),
      memberId = #{memberId},
      title = #{title},
      `body` = #{body}
      """)
  // INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = ?, `body` = ?
  public void writeArticle(@Param("memberId") int memberId, @Param("title") String title, @Param("body") String body);

  @Select("""
      SELECT A.*,
      M.nickname AS extra__writerName 
      FROM article AS A
      LEFT JOIN member AS M
      ON A.memberId = M.id 
      WHERE 1
      AND A.id = #{id}
      """)
  // SELECE * FROM article WHERE id = ? 이라고 해야 id를 가져올 수 있다.
  public Article getForPrintArticle(@Param("id") int id);

  @Delete("""
      DELETE 
      FROM article 
      WHERE id = #{id}
      """)
  // DELETE FROM article WHERE id = ?
  public void deleteArticle(@Param("id") int id);


  @Select("""
      SELECT A.*,
      M.nickname AS extra__writerName 
      FROM article AS A
      LEFT JOIN member AS M
      ON A.memberId = M.id 
      ORDER BY A.id DESC
      """)
  // SELECT * FROM article ORDER BY id DESC;
  public List<Article> getForPrintArticles();

  @Update("""
      <script>
      UPDATE article 
      <set>
        <if test='title != null'>
          title = #{title}, 
        </if>
        <if test='body != null'>
          `body` =  #{body}, 
        </if>
        updateDate = NOW()
      </set> 
      WHERE id =  #{id}
      </script>
      """)
  // UPDATE article SET title = ?, `body` = ? updateDate = NOW() WHERE id = ?

  public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

  @Select("SELECT LAST_INSERT_ID()")
  public int getLastInsertId();

  @Select("""
      <script>
      SELECT A.*,
      M.nickname AS extra__writerName 
      FROM article AS A
      LEFT JOIN member AS M
      ON A.memberId = M.id 
      WHERE 1
      <if test="boardId != 0">
        AND A.boardId = #{boardId}
      </if>
      ORDER BY A.id DESC
      </script>
      """)
  public List<Article> getArticles(@Param("boardId") int boardId);
}

