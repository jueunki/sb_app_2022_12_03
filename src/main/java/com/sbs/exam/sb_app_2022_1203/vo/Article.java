package com.sbs.exam.sb_app_2022_1203.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data  //@Data : getter and setter를 다 만들어준다.
@NoArgsConstructor
@AllArgsConstructor //@AllArgsConstructor : 모든인스턴스의 변수를 받는 생성자를 만들수있다.
public class Article {
  private int id;
  private String regDate;
  private String updateDate;
  private int memberId;
  private String title;
  private String body;    // 여기까지 작성한것은 db 정보가 있으니 정보를 담을 수 있는 틀을 만들어준것이다.(안전하게 저장이 된다.)
  // 여기 까지는 기본 쿼리를 쓰면 받을 수 있고
  // --여기는
  private String hitCount;
  private int extra__sumReactionPoint;
  private int extra__goodReactionPoint;
  private int extra__badReactionPoint;
  // 제외--
  private  String extra__writerName; // 작성자다.
  // 여기는 leftjoin을 사용하면 받을 수 있는데,
  private boolean extra__actorCanModify;
  private boolean extra__actorCanDelete; //


  public String getRegDateForPrint() {
    return regDate.substring(2, 16);
  }

  public String getUpdateDateForPrint() {
    return updateDate.substring(2, 16);
  }

  // 여기는 쿼리로 받아올 수 없다.(무조건 false인 정보이다.)
}

