package org.zerock.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardListReplyCountDTO;

/*
    1. Querydsl과 기존의 JPARepository와 연동 작업 설정을 위한 인터페이스 생성
    2. 이 인터페이스를 구현하는 구현체 생성. 주의사항 구현체의 이름은 인터페이스 + Impl 로 작성함.
      이름이 다른 경우 제대로 동작하지 않을 수 있어요~~~~
    3. 마지막으로 BoardRepository의 선언부에서 BoardSearch 인터페이스를 추가 지정
 */
public interface BoardSearch {

    Page<Board> search1(Pageable pageable);

    // title과 content 의 내용을 검색...
    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types,
                                                      String keyword,
                                                      Pageable pageable);

}
