package com.sbs.exam.sb_app_2022_1203.interceptor;

import com.sbs.exam.sb_app_2022_1203.vo.Rq;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Rq 매번 적기 번거로워서 여기 interceptor를 만들어놓은것이다.
@Component
public class NeedLoginInterceptor implements HandlerInterceptor {
  private Rq rq;

  public NeedLoginInterceptor(Rq rq) {
    this.rq = rq;
  }

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handle) throws Exception {
    if(!rq.isLogined()) { //로그인 했냐고 물어보는 부분
      rq.printHistoryBackJs("로그인 후 이용해주세요.");
      return false;
    }
    return HandlerInterceptor.super.preHandle(req, resp, handle);
  }
}
