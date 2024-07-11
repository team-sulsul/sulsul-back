package main.sulsul.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MypageInfoResponse {

    private String nickname;

    private Integer totalBottle;

    private Integer totalDrink;
}
