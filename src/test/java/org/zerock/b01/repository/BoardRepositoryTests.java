package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    //insert
    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("title..."+ i )
                    .content("content..............."+i)
                    .writer("user"+(i%10))
                    .build();
            Board result = boardRepository.save(board);
            log.info("BNO : "+result.getBno());
        });
    }

    @Test
    public void testSelect() {
        Long bno = 100L;

        Optional<Board> result =  boardRepository.findById(bno);
        Board board = result.orElseThrow();

        log.info(board);

    }

    // update
    // Entity는 생성시 불변이면 좋으나, 변경이 일어날 경우 최소한으로 설계...
    @Test
    public void testUpdate() {
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        board.change("update...Title1000", "update content....... 100");

        boardRepository.save(board);
    }

    // 삭제하기
    @Test
    public void testDelete() {
        Long bno = 1L;

        boardRepository.deleteById(bno);
    }
}
