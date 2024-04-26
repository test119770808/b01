package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.b01.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
