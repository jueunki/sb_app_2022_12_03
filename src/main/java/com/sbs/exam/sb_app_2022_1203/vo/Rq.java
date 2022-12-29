package com.sbs.exam.sb_app_2022_1203.vo;

import com.sbs.exam.sb_app_2022_1203.service.MemberService;
import com.sbs.exam.sb_app_2022_1203.util.Ut;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {

  @Getter
  private boolean isLogined;
  @Getter
  private int loginedMemberId;
  @Getter
  private Member loginedMember;
  private HttpServletRequest req;
  private HttpServletResponse resp;
  private HttpSession session;
  public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
    this.req = req;
    this.resp = resp;
    this.session = req.getSession();

    HttpSession httpSession = req.getSession();
    boolean isLogined = false;
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null) {
      // 로그인 되어있는 상태
      isLogined = true;
      loginedMemberId = (int) session.getAttribute("loginedMemberId");
      loginedMember = memberService.getMemberById(loginedMemberId); // 위에는 Id만 가져오는것이고 여기는 작성자까지 가져오려는 부분이다.
      // loginedMemberId가 실제 작성자가 되는것이기 때문에 로그인한 멤버 아이디를 가져오는것이다.
    }
    this.isLogined = isLogined;
    this.loginedMemberId = loginedMemberId;
    this.loginedMember = loginedMember;
    this.req.setAttribute("rq", this); // this는 자기자신이다.!!
  }

  public boolean isnotLogined() {
    return !isLogined;
  }

  public void printHistoryBackJs(String msg) {
    resp.setContentType("text/html; charset=UTF-8"); //한글 깨짐 방지
    print(Ut.jsHistoryBack(msg));
  }

  public void print(String str) {
    try {
      resp.getWriter().append(str);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void println(String str) {
    print(str + "\n");
  }

  public void login(Member member) {
    session.setAttribute("loginedMemberId", member.getId());
  }

  public void logout() {
    session.removeAttribute("loginedMemberId");
  }

  public String historyBackJsOnView(String msg) {
    req.setAttribute("msg", msg);
    req.setAttribute("historyBack", true);
    return "common/js";
  }

  public String jsHistoryBack(String msg) {
    return Ut.jsHistoryBack(msg);
  }

  public String jsReplace(String msg, String uri) {
    return Ut.jsReplace(msg, uri);
  }


  //---- 로그인상태에서 메인홈화면으로 가면 로그아웃 되어있어야하는데 로그인이라고 잘못 설정이 되어있는 부분을 해소하고자 만든 부분----
  //  하위의 메서드는 Rq 객체가 자연스럽게 생성되도록 유도하는 역할을 한다.
  // 지우면 안되고 편의를 위해서 initOnBeforeActionInterceptor 에서 꼭 호촐을 해줘야 한다.
  public void initOnBeforeActionInterceptor() {
  }

  public String getCurrentUri() {
    String currentUri = (String)req.getAttribute("javax.servlet.forward.request_uri");  //
    String queryString = req.getQueryString();

    if (queryString != null && queryString.length() > 0) {
      currentUri += "?" + queryString;
    }

    return currentUri;
  }

  public String getEncodedCurrentUri() {
    return Ut.getUriEncoded(getCurrentUri());
  }
}
