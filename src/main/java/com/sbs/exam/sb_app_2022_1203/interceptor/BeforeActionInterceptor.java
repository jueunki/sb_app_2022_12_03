package com.sbs.exam.sb_app_2022_1203.interceptor;

import com.sbs.exam.sb_app_2022_1203.vo.Rq;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Rq 매번 적기 번거로워서 여기 interceptor를 만들어놓은것이다.
@Component
public class BeforeActionInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handle) throws Exception {
    // 리스트 버튼 누르면 실행되는부분.
    Rq rq = new Rq(req, resp);
    req.setAttribute("rq", rq);

    return HandlerInterceptor.super.preHandle(req, resp, handle);
  }
}
