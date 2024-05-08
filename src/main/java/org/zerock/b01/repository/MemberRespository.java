package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Member;

import java.util.Optional;

public interface MemberRespository extends JpaRepository<Member, String> {

    // 소셜 사용하지 않는 사용자의 권한 로딩...
    @EntityGraph(attributePaths = "roleSet")   // fetch 조인을 위해서 사용한 어노테이션...
    @Query("select m from Member m where m.mid = :mid and m.social = false")
    Optional<Member> getWithRoles(String mid);

    @EntityGraph(attributePaths = "roleSet")
    Optional<Member> findByEmail(String email);

}
