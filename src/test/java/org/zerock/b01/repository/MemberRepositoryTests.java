package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.b01.domain.Member;
import org.zerock.b01.domain.MemberRole;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRespository memberRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMembers() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .mid("member"+i)
                    .mpw(passwordEncoder.encode("1111"))
                    .email("email"+i+"@test.com")
                    .build();
            member.addRole(MemberRole.USER);  //권한 설정

            if(i >= 90) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRespository.save(member);
        });
    }

    @Test
    public void testRead() {
        Optional<Member> result = memberRespository.getWithRoles("member100");
        Member member = result.orElseThrow();

        log.info(member);
        log.info(member.getRoleSet());
        member.getRoleSet().forEach(memberRole -> log.info(memberRole.name()));
    }

    @Test
    public void testFindByEmail() {
        Optional<Member> result = memberRespository.findByEmail("email100@test.com");
        Member member = result.orElseThrow();

        log.info(member);
    }

}
