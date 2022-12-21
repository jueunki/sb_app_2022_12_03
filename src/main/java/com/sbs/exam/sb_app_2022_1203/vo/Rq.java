package com.sbs.exam.sb_app_2022_1203.vo;

import com.sbs.exam.sb_app_2022_1203.util.Ut;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Rq {

  @Getter
  private boolean isLogined;
  @Getter
  private int loginedMemberId;

  private HttpServletRequest req;
  private HttpServletResponse resp;
  private HttpSession session;
  public Rq(HttpServletRequest req, HttpServletResponse resp) {
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
    }
    this.isLogined = isLogined;
    this.loginedMemberId = loginedMemberId;
  }

  public void printHistoryBackJs(String msg) {
    resp.setContentType("text/html; charset=UTF-8"); //한글 깨짐 방지

    println("<script>");

    if(!Ut.empty(msg)) {
      println("alert('" + msg + "');");
    }
    println("history.back();");

    println("</script>");
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
}
