package org.zerock.b01.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.b01.domain.Board;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long rno;
    @NotNull
    private Long bno;
    @NotEmpty
    private String replyText;
    @NotEmpty
    private String replyer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //날짜 형식 설정...
    private LocalDateTime regDate;

    @JsonIgnore                                   // 날짜 변환 무시...
    private LocalDateTime modDate;


}
