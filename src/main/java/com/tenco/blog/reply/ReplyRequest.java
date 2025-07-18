package com.tenco.blog.reply;

import com.tenco.blog.board.Board;
import com.tenco.blog.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class ReplyRequest {

    @Data
    public static class SaveDTO {
        @NotNull(message = "게시글 정보가 필요합니다")
        @Positive(message = "올바른 게시글 ID를 입력해주세요")
        private Long boardId;

        @NotEmpty(message = "댓글 내용을 입력해주세요")
        @Size(min = 1, max = 500, message = "댓글은 1~500자 이내로 작성해주세요")
        private String comment;

        public Reply toEntity(User sessionUser, Board board) {
            return Reply.builder()
                    .comment(comment.trim())
                    .user(sessionUser)
                    .board(board)
                    .build();
        }
    }
}
