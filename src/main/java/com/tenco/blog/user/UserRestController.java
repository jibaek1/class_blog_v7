package com.tenco.blog.user;

import com.tenco.blog._core.common.ApiUtil;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController // @Controller + @ResponseBody
public class UserRestController {

    // @Slf4j 사용시 자동 선언 됨
    // private static final Logger log = LoggerFactory.getLogger(UserRestController.class)


    // 생성자 의존 주입 - DI
    private final UserService userService;


    // http://localhost:8080/join 확장자가 포함 되지 않은 API를 URI 라고 한다
    //회원 가입 API 설계
    @PostMapping("/join")
    // public ResponseEntity<ApiUtil<UserResponse.JoinDTO>> join() {
    // JSON 형식에 데이터를 추출 할 때 @RequestBody 선언
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO reqDTO) {
        log.info("회원가입 API 호출 - 사용자명:{}, 이메일:{}",
                reqDTO.getUsername(), reqDTO.getEmail());
        reqDTO.validate();
        // 서비스 위임 처리
        UserResponse.JoinDTO joinUser = userService.join(reqDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiUtil<>(joinUser));
    }

    // 방식은 자원의 요청 하지만 로그인에서는 보안상의 문제로 POST 방식을 사용한다.
    // 로그인 API GET
    // http://localhost:8080/login
    @PostMapping("/login")
    public ResponseEntity<ApiUtil<UserResponse.LoginDTO>> login(
            @RequestBody UserRequest.LoginDTO reqDTO, HttpSession session) {
        log.info("로그인 API 호출 - 사용자명: {}",reqDTO.getUsername());
        reqDTO.validate();

        UserResponse.LoginDTO loginUser = userService.login(reqDTO);
        // 세션에 정보 저장
        // session.setAttribute("sessionUser",loginUser);

        return ResponseEntity.ok(new ApiUtil<>(loginUser));
    }


    // 로그아웃 API
    @GetMapping("/logout")
    public ResponseEntity<ApiUtil<String>> logout(HttpSession session) {
        log.info("로그아웃 API 호출");
        session.invalidate();
        return ResponseEntity.ok(new ApiUtil<>("로그아웃 성공"));
    }


    // http://localhost:8080/api/users/{id}
    // 회원 정보 API
    @GetMapping("/api/users/{id}")
    public ResponseEntity<ApiUtil<UserResponse.DetailDTO>> getUserInfo(
            @PathVariable(name = "id") Long id, HttpSession session) {

        log.info("회원 정보 API 호출 - ID: {}", id);

        User sessionUser = (User)session.getAttribute("sessionUser");

        // 로그인한 사용자가 - 10
        // 로그인한 사용자가 - 30 (정보요청)
        UserResponse.DetailDTO userDetail = userService.findUserById(id,sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(userDetail));
    }

    // 회원 정보 수정
    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") Long id,
                                        @RequestBody UserRequest.UpdateDTO updateDTO) {
        // 인증검사는 인터셉터에 처리 됨
        // 유효성 검사
        updateDTO.validate();
        UserResponse.UpdateDTO updateUser = userService.updateById(id,updateDTO);

        return ResponseEntity.ok(new ApiUtil<>(updateUser));
    }
}
