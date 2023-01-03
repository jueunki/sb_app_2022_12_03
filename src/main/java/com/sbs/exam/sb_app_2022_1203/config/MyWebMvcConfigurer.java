package com.sbs.exam.sb_app_2022_1203.config;

import com.sbs.exam.sb_app_2022_1203.interceptor.BeforeActionInterceptor;
import com.sbs.exam.sb_app_2022_1203.interceptor.NeedLoginInterceptor;
import com.sbs.exam.sb_app_2022_1203.interceptor.NeedLogoutInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
  // beforeActionInterceptor 인터셉터 불러오기
  @Autowired
  BeforeActionInterceptor beforeActionInterceptor;

  // needLoginInterceptor 인터셉터 불러오기
  @Autowired
  NeedLoginInterceptor needLoginInterceptor;

  @Autowired
  NeedLogoutInterceptor needLogoutInterceptor;

  // 이 함수는 인터셉터를 적용하는 역할을 합니다.
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(beforeActionInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/css/**")
        .excludePathPatterns("/js/**")
        .excludePathPatterns("/error");

    registry.addInterceptor(needLoginInterceptor)
        .addPathPatterns("/usr/article/myPage")
        .addPathPatterns("/usr/article/checkPassword")
        .addPathPatterns("/usr/article/doCheckPassword")
        .addPathPatterns("/usr/article/write")
        .addPathPatterns("/usr/article/doWrite")
        .addPathPatterns("/usr/article/modify")
        .addPathPatterns("/usr/article/doModify")
        .addPathPatterns("/usr/article/doDelete")
        .addPathPatterns("/usr/reactionPoint/doGoodReaction")
        .addPathPatterns("/usr/reactionPoint/doBadReaction")
        .addPathPatterns("/usr/reactionPoint/doCancelGoodReaction")
        .addPathPatterns("/usr/reactionPoint/doCancelBadReaction");

    registry.addInterceptor(needLogoutInterceptor)
        .addPathPatterns("/usr/member/join")
        .addPathPatterns("/usr/member/doJoin")
        .addPathPatterns("/usr/member/login")
        .addPathPatterns("/usr/member/doLogin")
        .addPathPatterns("/usr/member/findLoginId")
        .addPathPatterns("/usr/member/doFindLoginId")
        .addPathPatterns("/usr/member/findLoginPw")
        .addPathPatterns("/usr/member/doFindLoginPw");
  }
}