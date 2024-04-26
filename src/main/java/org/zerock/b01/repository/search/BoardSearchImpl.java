package org.zerock.b01.repository.search;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;

import java.util.List;

/*
    BoardSearch를 상속받는 구현체
 */
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        // Q도메인을 이용한 쿼리 작성 및 테스트
        // Querydsl의 목적은 "타입" 기반으로 "코드"를 이용해서 JPQL 쿼리를 생성하고 실행한다...
        // Q도메인은 이 때에 코드를 만든는 대신 클래스가 Q도메인 클래스...

        // 1. Q도메인 객체 생성
        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);   // select .. from board

        query.where(board.title.contains("1")); // where title like ...

        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> title = query.fetch();      // JPQLQuery에 대한 실행

        long count = query.fetchCount();        // 쿼리 실행....

        return null;
    }
}
