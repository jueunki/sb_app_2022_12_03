package com.sbs.exam.sb_app_2022_1203.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  //@Data : getter and setter를 다 만들어준다.
@NoArgsConstructor
@AllArgsConstructor //@AllArgsConstructor : 모든인스턴스의 변수를 받는 생성자를 만들수있다.
public class Board {
  private int id;
  private String regDate;
  private String updateDate;

  private String code;
  private String name;
  private boolean delStatus;
  private String delDate;

}