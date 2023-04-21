package com.acorn.springboardstudy;

import com.acorn.springboardstudy.interceptor.AutoLoginInterceptor;
import com.acorn.springboardstudy.interceptor.LoginCheckInterceptor;
import com.acorn.springboardstudy.interceptor.MsgRemoveInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//> @Component 스프링 실행 시 설정 파일
// WebMvcConfigurer : 요청|응답과 관련된 설정
@AllArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    //interface 에 default 속성을 부여하면 강제구현을 하지않는다.

    private AutoLoginInterceptor autoLoginInterceptor;
    private LoginCheckInterceptor loginCheckInterceptor;
    private MsgRemoveInterceptor   msgRemoveInterceptor;
    @Override
    //addInterceptors : Interceptor 설정
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(autoLoginInterceptor).order(1)
               .addPathPatterns("/**");
       registry.addInterceptor(loginCheckInterceptor).order(2)
                .addPathPatterns("/user/**")//   .addPathPatterns(url) url 페이지는 오지않도록 해라.
                    .excludePathPatterns("/user/login.do")//login.do는 제외
                    .excludePathPatterns("/user/signup.do")//login.do는 제외
                    .excludePathPatterns("/user/emailCheck.do")//는 제외
                .addPathPatterns("/board/**")
                    .excludePathPatterns("/board/list.do")
                    .excludePathPatterns("/board/*/ajaxTagList.do")
                    .excludePathPatterns("/board/*/detail.do");
        /*  order를 이용해 순서 설정 가능
        registry.addInterceptor(loginCheckInterceptor).order(1)
        registry.addInterceptor().order(2);
        */
       registry.addInterceptor(msgRemoveInterceptor).order(3)
                .addPathPatterns("/**");
    }
}
