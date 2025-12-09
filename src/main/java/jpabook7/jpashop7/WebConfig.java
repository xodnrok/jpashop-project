package jpabook7.jpashop7;

import jpabook7.jpashop7.intercetor.LogInterceptor;
import jpabook7.jpashop7.intercetor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //사용자 로그 표시기
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error"); //css,아이콘,에러는 제외



//        //로그인한 사람만 사이트 접근 체크
//        registry.addInterceptor(new LoginCheckInterceptor())
//                .order(2)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/login", "/members/new","/logout","/css/**","/*.ico","/error"); //css,아이콘,에러는 제외

    }


}
