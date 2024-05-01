package org.zerock.b01.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDTO {
    // 게시글에 댓글 숫자 값을 처리하는 DTO
    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;

    private Long replyCount;

}
