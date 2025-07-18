package com.tenco.blog.reply;

import com.tenco.blog._core.utils.MyDateUtil;
import com.tenco.blog.board.Board;
import com.tenco.blog.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Table(name = "reply_tb")
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // mysql,po,MS Server
    private Long id;

    @Column(nullable = false, length = 500) // 기본 값 255
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY) // 성능상의 문제로 프로젝트에선 LAZY 전략만 사용
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Reply(Long id, String comment, User user, Board board, Timestamp createdAt) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.board = board;
        this.createdAt = createdAt;
    }

    @Transient
    private boolean isReplyOwner;

    public boolean isOwner(Long sessionId) {
        return this.user.getId().equals(sessionId);
    }

    public String getTime() {
        return MyDateUtil.timestampFormat(createdAt);
    }

}
