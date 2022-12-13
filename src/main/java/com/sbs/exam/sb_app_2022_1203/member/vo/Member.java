package com.sbs.exam.sb_app_2022_1203.member.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  Member {
  private int id;
  private String regDate;
  private String updateDate;
  private String loginId;
  private String loginPw;
  private int authLevel;
  private String name;
  private String nickname;
  private String cellphoneNo;
  private String email;
  private boolean delStatus;  // int로 잡으면 안된다. 1이면true 0이면 false로 잡는다.
  private String delDate;

}