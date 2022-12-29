package com.sbs.exam.sb_app_2022_1203.util;


import java.net.URLEncoder;

// Util 함수 클래스 : 로그인 아이디가 있는지 체크해주는 함수
public class Ut {
  public static boolean empty(Object obj) { //범용적으로 데이터를 받으려고 Object 타입으로 한다.
    if (obj == null) {   //String이 아니면 비어있다.
      return true;
    }

    if (obj instanceof Integer) {
      return ((int)obj) == 0;
    }

    if (obj instanceof Long) {
      return ((Long)obj) == 0;
    }


    if (obj instanceof String == false) {   //넘겨받은것을 String으로 바꿀것인데 String이 아닌것은 return true해준다.
      return true;
    }   // instanceof : obj객체가 String형 이라는것을 확인시켜주기 위한것.

    String str = (String) obj;
    return str.trim().length() == 0;  // 좌우 공백을 제거하고 길이를 쟀을때 길이가 0이면 비어있다고 여긴다.
  }

  public static String f(String format, Object... args) { // Object... args: 이렇게 쓰면 들어가는 값이 두개이던 N개이던 배열 형식으로 받아서 들어간다.
    return String.format(format, args);
  }

  public static String jsHistoryBack(String msg) {
    if (msg == null) {
      msg = "";
    }

    // 메시지를 띄워주고 뒤로가기. history.back();*자바스크립트 문법.
    return Ut.f("""
        <script>
        const msg = '%s'.trim();
        if( msg.length > 0 ) {
         alert(msg);
        }
        history.back();
        </script>
        """, msg);
  }

  public static String jsReplace(String msg, String uri) {
    if (msg == null) {
      msg = "";
    }

    if (uri == null) {
      uri = "";
    }
    // 이부분이 있어야지만 실제로 게시물을 삭제 했을때 리스트 페이지로 이동 할 수 있는 부분.
    return Ut.f("""
        <script>
        const msg = '%s'.trim();
        if( msg.length > 0 ) {
         alert(msg);
        }
        location.replace('%s');
        </script>
        """, msg, uri);
  }

  public static String getUriEncoded(String str) {
    try {
      return URLEncoder.encode(str, "UTF-8");
    } catch (Exception e) {
      return str;
    }
  }
}

