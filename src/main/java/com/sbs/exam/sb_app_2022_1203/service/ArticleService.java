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


  public ResultData<Integer> writeArticle(int memberId, int boardId, String title, String body) {
    articleRepository.writeArticle(memberId, boardId, title, body);
    int id = articleRepository.getLastInsertId();

    // Service(여기)에서 Controller에게 넘기는 과정
    return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), "id", id);
  }

  //출력용
  public List<Article> getForPrintArticles(int actorId, int boardId, String searchKeywordTypeCode, String searchKeyword, int itemsCountInAPage, int page) {
    /* 예시 설명
    SELECT *
    FROM article
    WHERE boardId = 1  여기서 1은 공지사항 게시판으로 설정이 되어있어서
     ORDER BY id DESC   공지사항 게시판이면 0부터 10까지 보여줘라 라는 의미.
     LIMIT 0, 10
    */
    int limitStart = (page - 1) * itemsCountInAPage; //페이지가 1이면 0부터 10까지 보여주는것이고 페이지가 1 이면 10에서 10 까지 보여주는것이고 페이지가 3 이면 20에서 10까지 보여주는것이다.
    int limitTake = itemsCountInAPage;

    List<Article> articles = articleRepository.getFroPrintArticles(boardId, searchKeywordTypeCode, searchKeyword,
        limitStart, limitTake);

    for (Article article : articles) {
      updateForPrintData(actorId, article);
    }
    return articles;
  }

  public Article getForPrintArticle(int actorId, int id) {
    Article article = articleRepository.getForPrintArticle(id);

    updateForPrintData(actorId, article);

    return article;
  }
  private void updateForPrintData(int actorId, Article article) { //(int actorId, Article article) : 로그인 한 사람이 누구인지 얻어오고, 해당 게시물을 얻어오는데
    //데이터를 담아서 넘겨준 상태.
    if (article == null) {
      return;
    }

    ResultData actorCanDeleteRd = actorCanDelete(actorId, article);
    article.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());

    ResultData actorCanModifyRd = actorCanModify(actorId, article);
    article.setExtra__actorCanModify(actorCanModifyRd.isSuccess());
  }



  public void deleteArticle(int id) {
    articleRepository.deleteArticle(id);
  }

  public ResultData<Article> modifyArticle(int id, String title, String body) {
    articleRepository.modifyArticle(id, title, body);

    Article article = getForPrintArticle(0, id);
    return ResultData.from("S-1", Ut.f("%d번 게시물을 수정하였습니다.", id), "article", article);
  }

  public ResultData actorCanModify(int actorId, Article article) {
    if (article == null) {
      return ResultData.from("F-1", "게시물이 존재하지 않습니다.");
    }
    if (article.getMemberId() != actorId) {
      return ResultData.from("F-2", "권한이 없습니다.");
    }
    return ResultData.from("S-1", "게시물 수정이 가능합니다.");
  }

  public ResultData actorCanDelete(int actorId, Article article) {

    if (article == null) {
      return ResultData.from("F-1", "게시물이 존재하지 않습니다.");
    }
    if (article.getMemberId() != actorId) {
      return ResultData.from("F-2", "권한이 없습니다.");
    }
    return ResultData.from("S-1", "게시물 삭제가 가능합니다.");
  }

  public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
    return articleRepository.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
  }

  public ResultData<Integer> increaseHitCount(int id) {
    int affectedRowsCount = articleRepository.increaseHitCount(id);

    if(affectedRowsCount == 0) {
      return ResultData.from("F-1","해당 게시물이 존재하지않습니다.",
          "affectedRowsCount", affectedRowsCount);
    }

    return ResultData.from("S-1","조회수가 증가되었습니다.",
    "affectedRowsCount", affectedRowsCount);
  }

  public int getArticleHitCount(int id) {
    return articleRepository.getArticleHitCount(id);
  }


  public ResultData increaseGoodReactionPoint(int relId) {
    int affectedRowsCount = articleRepository.increaseGoodReactionPoint(relId);

    if(affectedRowsCount == 0) {
      return ResultData.from("F-1","해당 게시물이 존재하지않습니다.",
          "affectedRowsCount", affectedRowsCount);
    }

    return ResultData.from("S-1","좋아요 수가 증가되었습니다.",
        "affectedRowsCount", affectedRowsCount);


  }

  public ResultData increaseBadReactionPoint(int relId) {
    int affectedRowsCount = articleRepository.increaseBadReactionPoint(relId);

    if(affectedRowsCount == 0) {
      return ResultData.from("F-1","해당 게시물이 존재하지않습니다.",
          "affectedRowsCount", affectedRowsCount);
    }

    return ResultData.from("S-1","싫어요 수가 증가되었습니다.",
        "affectedRowsCount", affectedRowsCount);

  }

  public ResultData decreaseGoodReactionPoint(int relId) {
    int affectedRowsCount = articleRepository.decreaseGoodReactionPoint(relId);

    if(affectedRowsCount == 0) {
      return ResultData.from("F-1","해당 게시물이 존재하지않습니다.",
          "affectedRowsCount", affectedRowsCount);
    }

    return ResultData.from("S-1","좋아요 수가 감소되었습니다.",
        "affectedRowsCount", affectedRowsCount);


  }


  public ResultData decreaseBadReactionPoint(int relId) {
      int affectedRowsCount = articleRepository.decreaseBadReactionPoint(relId);

      if(affectedRowsCount == 0) {
        return ResultData.from("F-1","해당 게시물이 존재하지않습니다.",
            "affectedRowsCount", affectedRowsCount);
      }

      return ResultData.from("S-1","싫어요 수가 감소되었습니다.",
          "affectedRowsCount", affectedRowsCount);

    }


  public Article getArticle(int id) {
    return articleRepository.getArticle(id);
  }
}

