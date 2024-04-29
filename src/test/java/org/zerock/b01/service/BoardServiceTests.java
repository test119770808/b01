package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

import java.util.NoSuchElementException;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample1 test.....")
                .content("Sample1 Content......")
                .writer("user1")
                .build();
        long bno = boardService.register(boardDTO);

        log.info("bno: "+bno);

    }

    @Test
    public void testReadOne() {
        long bno = 101L;

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);
    }

    @Test
    public void testModify() {
        //변경에 필요한 데이터만...
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(102L)
                .title("uptdate..... 102")
                .content("update Content.... 102... ")
                .build();
        boardService.modify(boardDTO);
        // 확인...
        log.info(boardService.readOne(boardDTO.getBno()));
    }

    @Test
    public void testRemove() {
        long bno = 103L;

        boardService.remove(bno);
        Assertions.assertThrows(NoSuchElementException.class,
                () -> boardService.readOne(bno));

    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);
    }

}
