package org.zerock.b01.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;
import org.zerock.b01.service.ReplyService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;  //의존성 주입

    @Operation(summary = "Replies Post - Post방식으로 댓글 등록")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(
            @Valid @RequestBody ReplyDTO replyDTO,
            BindingResult bindingResult) throws BindException {
        log.info(replyDTO);

        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();
        Long rno = replyService.register(replyDTO);

        resultMap.put("rno", rno);

        return resultMap;
    }


    // 특정  게시물의 댓글 목록 보기...
    // URI -> '/replies/list/{bno}' , 방식 -> GET
    // @PathVariable 어노테이션 사용!
    @Operation(summary = "Replies of Board로 GET방식으로 특정 게시물 댓글 목록 처리...")
    @GetMapping(value = "/list/{bno}")
    public PageResponseDTO<ReplyDTO> getList(
            @PathVariable("bno") Long bno,
            PageRequestDTO pageRequestDTO) {

        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfBoard(bno,pageRequestDTO);

        return responseDTO;
    }

    @Operation(summary = "Read Reply - GET방식으로 댓글 조회")
    @GetMapping("/{rno}")
    public ReplyDTO getReplyDTO(@PathVariable("rno") Long rno) {
        ReplyDTO replyDTO = replyService.read(rno);
        return replyDTO;
    }

    @Operation(summary = "Delete Reply - DELETE 메서드를 이용한 댓글 삭제")
    @DeleteMapping("/{rno}")
    public Map<String, Long> remove(@PathVariable("rno") Long rno) {
        replyService.remove(rno);

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", rno);

        return resultMap;
    }

    @Operation(summary = "Modify Reply - PUT 방식으로 댓글 수정")
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modify(
            @PathVariable("rno") Long rno,
            @RequestBody ReplyDTO replyDTO){
        replyDTO.setBno(rno);   // 번호 일치
        replyService.modify(replyDTO);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", rno);
        return resultMap;
    }
}
