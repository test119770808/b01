package org.zerock.b01.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Member;
import org.zerock.b01.domain.MemberRole;
import org.zerock.b01.dto.MemberSecurityDTO;
import org.zerock.b01.repository.MemberRespository;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    // 소셜에 등록된 이메일을 통해서 사용자 구분을 하나 없는 경우... 멤버 추가 설정을 위해서 DI
    private final MemberRespository memberRespository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("userRequest......");
        log.info(userRequest);

        log.info("oauth2 user..................................................");

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();
        log.info("NAME : "+clientName);  // 어떤 소셜을 사용했니?

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> paramMap = oAuth2User.getAttributes();

        String email = null;

        switch (clientName) {
            case "kakao":
                email = getKakaoEmail(paramMap);
                break;
        }
//        paramMap.forEach((k, v) -> {
//            log.info("------------------------------------------");
//            log.info(k + ":" + v);
//        });

        log.info("=================================================");
        log.info(email);
        log.info("=================================================");

        return generateDTO(email, paramMap);  //MemberSecurityDTO로 반환 처리...
    }

    private MemberSecurityDTO generateDTO(String email, Map<String, Object> params) {
        Optional<Member> result = memberRespository.findByEmail(email);

        // 데이터베이스 해당 이메일 사용자 없는 경우...
        if(result.isEmpty()) {
            // 회원 추가.... mid는 이메일 주소/ 패스워드 1111
            Member member = Member.builder()
                    .mid(email)
                    .mpw(passwordEncoder.encode("1111"))
                    .email(email)
                    .social(true)
                    .build();
            member.addRole(MemberRole.USER);
            memberRespository.save(member);
            // MemberSecurityDTO로 반환....
            MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(email, "1111", email, false,true,
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            memberSecurityDTO.setProps(params);
            return memberSecurityDTO;
        }else {
            Member member = result.get();
            MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                    member.getMid(), member.getMpw(), member.getEmail(), member.isDel(), member.isSocial(),
                    member.getRoleSet().stream()
                            .map(memberRole -> new SimpleGrantedAuthority("ROLE_"+memberRole.name()))
                            .collect(Collectors.toList())
            );
            return memberSecurityDTO;
        }
    }



    // getKakaoEmail() 만들기...  : KAKAO에서 전달된 정보를 통해서 Email 반환 처리...
    private String getKakaoEmail(Map<String, Object> paramMap) {
        log.info("KAKAO....................................");
        Object value = paramMap.get("kakao_account");
        log.info(value);

        LinkedHashMap accountMap = (LinkedHashMap) value;
        String email = (String) accountMap.get("email");
        log.info("email ... " + email);
        return email;
    }
}
