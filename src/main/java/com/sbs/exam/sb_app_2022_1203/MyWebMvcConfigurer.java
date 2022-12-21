package com.sbs.exam.sb_app_2022_1203;

import com.sbs.exam.sb_app_2022_1203.interceptor.BeforeActionInterceptor;
import com.sbs.exam.sb_app_2022_1203.interceptor.NeedLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 프로그램이 처음 실행될때 처음 한번 읽는다.
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

  // beforeActionInterceptor 인터셉터 불러오기
  @Autowired
  BeforeActionInterceptor beforeActionInterceptor;

  // NeedLoginInterceptor 인터셉터 불러오기
  @Autowired
  NeedLoginInterceptor needLoginInterceptor;

  // 이 함수는 인터셉터를 적용하는 역할을 합니다.
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(beforeActionInterceptor)
        .addPathPatterns("/**")           //-> 모든 요청
        .excludePathPatterns("/css/**") //-> css와
        .excludePathPatterns("/js/**")  //->js는 interceptor가 작동 할 필요가 없는 작업이다.
                                        //localhost:8081/css/common.css라고 하면 출력된다.
        .excludePathPatterns("/error");

    registry.addInterceptor(needLoginInterceptor)
        .addPathPatterns("/user/article/write")   //글 작성폼
        .addPathPatterns("/user/article/doWrite") //글 전송폼
        .addPathPatterns("/user/article/modify")
        .addPathPatterns("/user/article/doModify")
        .addPathPatterns("/user/article/doDelete");
  }
}
