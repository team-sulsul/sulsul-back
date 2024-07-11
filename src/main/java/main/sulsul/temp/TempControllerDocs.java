package main.sulsul.temp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import main.sulsul.global.dto.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Tag(name = "테스트용 컨트롤러")
public interface TempControllerDocs {
    
    @Operation(summary = "테스트용 accessToken 발급 받기", description = "테스트용 accessToken 발급 받기")
    @GetMapping("/accessToken/{memberId}")
    CommonResponse<String> getAccessTokenByMemberId(@PathVariable("memberId") Long memberId);
}
