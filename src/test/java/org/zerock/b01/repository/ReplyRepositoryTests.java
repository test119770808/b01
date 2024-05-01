package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    // 테스트 : 있는게시글 중에 댓글 추가... 208번 에 댓글 추가... (insert....)
    @Test
    public void testInsert() {
        // 실제 DB에 있는 BNO를 선택
        Long bno = 208L;

        Board board = Board.builder().bno(bno).build();
        IntStream.rangeClosed(1, 100).forEach( i -> {
            Reply reply = Reply.builder()
                    .board(board)
                    .replyText("댓글 테스트1......"+i)
                    .replyer("replyer1")
                    .build();

            replyRepository.save(reply);
        });
//        Reply reply = Reply.builder()
//                .board(board)
//                .replyText("댓글 테스트1......")
//                .replyer("replyer1")
//                .build();
//
//        replyRepository.save(reply);
    }

    @Test
    public void testBoardReplies() {
        // 실제 게시물 번호
        Long bno = 208L;

        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());


    }

}
