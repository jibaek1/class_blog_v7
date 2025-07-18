package com.tenco.blog.reply;

import com.tenco.blog._core.common.ApiUtil;
import com.tenco.blog.user.SessionUser;
import com.tenco.blog.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ReplyRestController {

    private final ReplyService replyService;

    // 댓글 저장 API 요청
    @PostMapping("/api/replies")
    public ResponseEntity<?> save(
            @Valid @RequestBody ReplyRequest.SaveDTO saveDTO, Errors errors,
            @RequestAttribute ("sessionUser")SessionUser sessionUser) {

        ReplyResponse.SaveDTO savedReply = replyService.save(saveDTO, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedReply));
    }

    // 댓글 삭제 API 요청
    @PostMapping("/api/replies/{id}")
    public ResponseEntity<ApiUtil<String>> delete(@PathVariable(name = "id") Long replyId,
                                                  @RequestParam(name = "boardId") Long boardId,
                                                  @RequestAttribute("sessionUser")SessionUser sessionUser) {

        replyService.deleteById(replyId, sessionUser);
        return ResponseEntity.ok( new ApiUtil<>("댓글 삭제 성공"));
    }

}
