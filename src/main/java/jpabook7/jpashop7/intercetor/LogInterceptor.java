package jpabook7.jpashop7.intercetor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    //사용자가 컨트롤러 요청하기 직전에 동작
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //사용자 요청 URL 체크
        String requestURI = request.getRequestURI();
        //사용자 구분 UUID
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID, uuid);

        //uuid , 요청 url , 요청에 대한 컨트롤러 확인
        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);

        return true;
    }

    //컨트롤러 view 를 동작시키기 직전에 동작
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    //사용자 요청에 대한 응답을 하기 바로 직전에 동작
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String logId = (String) request.getAttribute(LOG_ID);
        String requestURI = request.getRequestURI();

        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);

        if (ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}
