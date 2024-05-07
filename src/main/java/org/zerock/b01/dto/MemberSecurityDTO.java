package org.zerock.b01.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/*
  스프링 시큐리티에서는 UserDetails 라는 인터페이스를 사용합니다.
  때문에 스프링 시큐리티에서 UserDetails의 구현체인 User를 상속받아서 구현....
 */
@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {

    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;

    public MemberSecurityDTO(String username, String password, String email, boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.del = del;
        this.social = social;
    }

}
