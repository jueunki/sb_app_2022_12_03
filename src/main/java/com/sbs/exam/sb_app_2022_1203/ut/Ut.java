package com.sbs.exam.sb_app_2022_1203.ut;


// Util 함수 클래스 : 로그인 아이디가 있는지 체크해주는 함수
public class Ut {
  public static boolean empty(Object obj) { //범용적으로 데이터를 받으려고 Object 타입으로 한다.
    if(obj == null) {   //String이 아니면 비어있다.
      return  true;
    }

    if( obj instanceof String == false) {   //넘겨받은것을 String으로 바꿀것인데 String이 아닌것은 return true해준다.
      return true;
    }   // instanceof : obj객체가 String형 이라는것을 확인시켜주기 위한것.

    String str = (String) obj;
    return str.trim().length() == 0;  // 좌우 공백을 제거하고 길이를 쟀을때 길이가 0이면 비어있다고 여긴다.
  }
}
