package main.sulsul.oauth.application;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NicknameGenerator {

    private static final String apiUrl = "https://nickname.hwanmoo.kr/?format=text&max_length=5";

    /**
     * 닉네임을 제공해준다.
     * @return
     */
    public String getNickname() {

        // WebClient 객체 생성
        WebClient webClient = WebClient.create();

        // API 호출 및 응답 처리 (동기적)

        return webClient.get()
            .uri(apiUrl)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
