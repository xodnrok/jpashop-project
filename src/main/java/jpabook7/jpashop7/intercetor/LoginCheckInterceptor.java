package jpabook7.jpashop7.intercetor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jpabook7.jpashop7.controller.SessionConst;
import jpabook7.jpashop7.domain.Member;
import org.springframework.web.servlet.HandlerInterceptor;


public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //사용자가 요청한 URI 확인
        String requestURI = request.getRequestURI();
        //세션이 있으면 있는걸 없다면 안만들어서 가져온다.
        HttpSession session = request.getSession(false);

        //가져온 세션이 null 혹은 세션에서 값을 꺼냈는데 값이 null 일때
        //비회원 이므로 로그인 화면으로 돌려 보낸다.
        //redirect로 보내면서 어디에 접근했는지 알려준다.
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {

            response.sendRedirect("/login?redirectURL=" + requestURI);

            //더이상 진행하지 못하게 false 로 준다.
            return false;
        }

        //true 로 주면 계속 진행한다.
        return true;
    }

}
