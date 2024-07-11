package main.sulsul.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.sulsul.member.domain.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;

    private String nickname;

    private String username;

    private Role role;

    private String refreshToken;

    private String message;
}
