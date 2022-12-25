package com.sbs.exam.sb_app_2022_1203.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Rq 매번 적기 번거로워서 여기 interceptor를 만들어놓은것이다.
@Component
public class BeforeActionInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handle) throws Exception {
    // 이제는 Rq 객체가 자동으로 만들어지기 때문에 필요가없어서 지워줬다.

    return HandlerInterceptor.super.preHandle(req, resp, handle);
  }
}
