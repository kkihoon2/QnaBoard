package com.codestates.answer.controller;

import com.codestates.answer.dto.AnswerPostDto;
import com.codestates.answer.entity.Answer;
import com.codestates.answer.mapper.AnswerMapper;
import com.codestates.answer.service.AnswerService;
import com.codestates.question.dto.QuestionPostDto;
import com.codestates.utils.UriCreator;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/v11/answers")
@Validated
public class AnswerController {
    private final static String ANSWER_DEFAULT_URL = "/v11/answers";
    private AnswerService answerService;
    private AnswerMapper mapper;

    public AnswerController(AnswerService answerService, AnswerMapper mapper) {
        this.answerService = answerService;
        this.mapper = mapper;
    }
    @PostMapping("/{member-id}")//관리자 or 작성자 확인용
    public ResponseEntity postAnswer(@Valid @RequestBody AnswerPostDto answerPostDto
                                     , @PathVariable("member-id") @Positive long memberId){
        Answer answer = answerService.createAnswer(mapper.answerPostDtoToAnswer(answerPostDto),memberId);
        URI location = UriCreator.createUri(ANSWER_DEFAULT_URL, answer.getAnswerId());

        return ResponseEntity.created(location).build();
    }
}
