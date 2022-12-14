package com.sbs.exam.sb_app_2022_1203.vo;

import lombok.Getter;

public class ResultData<DT> {
  // 성공 : S-1, S-2, S-3
  // 실패 : F-1, F-2, F-3
  @Getter
  private String resultCode;
  @Getter
  private String msg;
  //비고란
  @Getter
  private DT data1;

  private ResultData() {
    // private생성자 : 외부에서 접근할 수 없게 하는것.

  }


  public static ResultData from(String resultCode, String msg) {
    return from(resultCode, msg, null);
  }

  // 아래 ResultData 보고서 만들기. //<Dt> : 데이터 타입을 재정의한다.
  public static <DT> ResultData<DT> from(String resultCode, String msg, DT data1) {
    //ResultData : 데이터 타입.
    ResultData<DT> rd = new ResultData<DT>();
    rd.resultCode = resultCode;
    rd.msg = msg;
    rd.data1 = data1;

    return rd;

  }


  public Boolean isSuccess() {
    return resultCode.startsWith("S-");  //무엇이든 앞에 S가 붙으면 성공이다 라는 의미.
  }

  public Boolean isFail() {
    return isSuccess() == false;  // isSuccess가 false면 실패라는 의미.
  }

  public static ResultData newData(ResultData joinRd, Object newData) {
    return from(joinRd.getResultCode(), joinRd.getMsg(), newData);

  }
}
