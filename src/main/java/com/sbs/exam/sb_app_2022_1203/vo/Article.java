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
  private String body;
  // 여기 까지는 기본 쿼리를 쓰면 받을 수 있고

  private  String extra__writerName; // 작성자다.
  // 여기는 leftjoin을 사용하면 받을 수 있는데,
  private boolean extra__actorCanDelete;
  // 여기는 쿼리로 받아올 수 없다.(무조건 false인 정보이다.)
}

