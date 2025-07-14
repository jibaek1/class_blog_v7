package com.tenco.blog._core.errors;

import com.tenco.blog._core.common.ApiUtil;
import com.tenco.blog._core.errors.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * REST API 전용 예외 처리 핸들러
 * 모든 예외를 JSON 형태로 응답 처리
 */
@Order(1) // 예외 처리 핸들러의 우선 순위 설정 1,2,3 ... 없음
@RestControllerAdvice // 데이터를 반환해서 내려 줄 때 사용
// @ControllerAdvice + @ResponseBody
public class MyExceptionHandler {

    // slf4j 로거 생성 - 로깅 사용시 Sysout 대신 활용하는것이 좋다.
    private static final Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler(Exception400.class) // 403오류가 터졌을때 이 메서드를 실행해라
    public ResponseEntity<?> ex400(Exception400 e, HttpServletRequest request) {
        log.warn("=== 400 Bad Request 에러 발생 ===");
        log.warn("요청 URL: {}",request.getRequestURL());
        log.warn("인증 오류: {}", e.getMessage());
        log.warn("User-Agent: {}",request.getHeader("User-Agent"));

        ApiUtil<?> apiUtil = new ApiUtil<>(400,e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> ex401(Exception401 e, HttpServletRequest request) {
        log.warn("=== 400 Bad Request 에러 발생 ===");
        log.warn("요청 URL: {}",request.getRequestURL());
        log.warn("인증 오류: {}", e.getMessage());
        log.warn("User-Agent: {}",request.getHeader("User-Agent"));

        ApiUtil<?> apiUtil = new ApiUtil<>(401,e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> ex400(Exception403 e, HttpServletRequest request) {
        log.warn("=== 400 Bad Request 에러 발생 ===");
        log.warn("요청 URL: {}",request.getRequestURL());
        log.warn("인증 오류: {}", e.getMessage());
        log.warn("User-Agent: {}",request.getHeader("User-Agent"));
        ApiUtil<?> apiUtil = new ApiUtil<>(403,e.getMessage());

        return new ResponseEntity<>(apiUtil, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> ex404(Exception400 e, HttpServletRequest request) {
        log.warn("=== 400 Bad Request 에러 발생 ===");
        log.warn("요청 URL: {}",request.getRequestURL());
        log.warn("인증 오류: {}", e.getMessage());
        log.warn("User-Agent: {}",request.getHeader("User-Agent"));

        ApiUtil<?> apiUtil = new ApiUtil<>(404,e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception500.class) // 403오류가 터졌을때 이 메서드를 실행해라
    public ResponseEntity<?> ex500(Exception500 e, HttpServletRequest request) {
        log.warn("=== 500 Internal Server Error 에러 발생 ===");
        log.warn("요청 URL: {}",request.getRequestURL());
        log.warn("인증 오류: {}", e.getMessage());
        log.warn("User-Agent: {}",request.getHeader("User-Agent"));

        ApiUtil<?> apiUtil = new ApiUtil<>(500,e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 기타 모든 RuntimeException 처리
    @ExceptionHandler(RuntimeException.class) // 403오류가 터졌을때 이 메서드를 실행해라
    public ResponseEntity<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        log.warn("=== 예상 못한 런타임 에러 발생 ===");
        log.warn("요청 URL: {}",request.getRequestURL());
        log.warn("인증 오류: {}", e.getMessage());
        log.warn("User-Agent: {}",request.getHeader("User-Agent"));
        request.setAttribute("msg","시스템 오류 발생, 관리자에게 문의 하세요");

        ApiUtil<?> apiUtil = new ApiUtil<>(500,e.getMessage());
        return new ResponseEntity<>(apiUtil, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
