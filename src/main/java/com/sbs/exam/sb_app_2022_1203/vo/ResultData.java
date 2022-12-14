package com.sbs.exam.sb_app_2022_1203.vo;

import lombok.Getter;

public class ResultData {
  // 성공 : S-1, S-2, S-3
  // 실패 : F-1, F-2, F-3
  @Getter
  private String resultCode;
  @Getter
  private String msg;

  //비고란
  @Getter
  private Object data1;

  private ResultData() {
    // private생성자 : 외부에서 접근할 수 없게 하는것.

  }

  // 아래 ResultData 보고서 만들기.
  private static ResultData from(String resultCode, String msg, Object data1) {
              //ResultData : 데이터 타입.
    ResultData rd = new ResultData();
    rd.resultCode = resultCode;
    rd.msg = msg;
    rd.data1 = data1;

    return rd;

  }

  public Boolean isSuccess(){
    return  resultCode.startsWith("S-");  //무엇이든 앞에 S가 붙으면 성공이다 라는 의미.
  }

  public Boolean isFail(){
    return isSuccess() == false;  // isSuccess가 false면 실패라는 의미.
  }
}
