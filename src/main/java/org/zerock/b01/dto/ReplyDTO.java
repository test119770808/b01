package org.zerock.b01.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long rno;
    private Long bno;
    private String replyText;
    private String replyer;

    private LocalDateTime regDate, modDate;


}
