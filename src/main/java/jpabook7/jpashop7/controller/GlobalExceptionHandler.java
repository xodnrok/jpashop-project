package jpabook7.jpashop7.controller;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public String handleConflict(Exception e, Model model) {
        model.addAttribute("msg", "주문 폭주! 잠시 후 다시 시도해주세요.");
        return "error/concurrencyError"; // 뷰 템플릿 이름
    }
}
