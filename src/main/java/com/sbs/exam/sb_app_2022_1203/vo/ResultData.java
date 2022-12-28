package com.sbs.exam.sb_app_2022_1203.vo;

import lombok.Getter;
import lombok.ToString;


@ToString
public class ResultData<DT> {
  // 성공 : S-1, S-2, S-3
  // 실패 : F-1, F-2, F-3
  @Getter
  private String resultCode;
  @Getter
  private String msg;
  @Getter
  private String data1Name;
  //비고란
  @Getter
  private DT data1;
  @Getter
  private String data2Name;
  //비고란
  @Getter
  private Object data2;

//  private ResultData() {
//    // private생성자 : 외부에서 접근할 수 없게 하는것.
//
//  }


  public static ResultData from(String resultCode, String msg) {
    return from(resultCode, msg, null, null);
  }

  // 아래 ResultData 보고서 만들기. //<Dt> : 데이터 타입을 재정의한다.
  public static <DT> ResultData<DT> from(String resultCode, String msg, String data1Name, DT data1) {
    //ResultData : 데이터 타입.
    ResultData<DT> rd = new ResultData<DT>();
    rd.resultCode = resultCode;
    rd.msg = msg;
    rd.data1Name = data1Name;
    rd.data1 = data1;

    return rd;

  }


  public Boolean isSuccess() {
    return resultCode.startsWith("S-");  //무엇이든 앞에 S가 붙으면 성공이다 라는 의미.
  }

  public Boolean isFail() {
    return isSuccess() == false;  // isSuccess가 false면 실패라는 의미.
  }

  public static<DT> ResultData<DT> newData(ResultData oldRd, String data1Name, DT data1) {
    return from(oldRd.getResultCode(), oldRd.getMsg(), data1Name, data1); //데이터 이름도 넣겠다는 의미.
    //alt + shift + R 누르면 한꺼번에 바꿀수있다.
  }

  public void setData2(String dataName, Object data) {
    data2Name = dataName;
    data2 = data;
  }
}
