package jpabook7.jpashop7.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> processValidationError(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        // getFieldErrors() : 에러가 난 필드 목록을 '모두' 가져옵니다.
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            // key: 필드명(ex: name), value: 에러메시지(ex: 공백일 수 없습니다)
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return errors;
    }



    @Data
    @AllArgsConstructor
    static class ErrorResult {
        private String code;    // 예: "BAD_REQUEST", "USER-EX-001" 등
        private String message; // 예: "이미 존재하는 로그인아이디입니다."
    }
}
