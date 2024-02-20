package main.sulsul.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main.sulsul.global.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(name = "idx__username", columnList = "username", unique = true)
})
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;
}
