package org.hanghae99.tddframeworkstudy.reply;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hanghae99.tddframeworkstudy.common.response.ApiRes;
import org.hanghae99.tddframeworkstudy.common.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/reply")
@RestController
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/write")
    public ResponseEntity<ApiRes<ReplyDto>> write(@RequestBody ReplyDto replyDto, HttpServletRequest request){
        String token = JwtTokenProvider.extractToken(request);
        return ApiRes.success("", replyService.write(replyDto, token));
    }

}
