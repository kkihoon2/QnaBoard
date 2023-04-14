package com.codestates.question.controller;

import com.codestates.dto.MultiResponseDto;
import com.codestates.dto.SingleResponseDto;
import com.codestates.member.service.MemberService;
import com.codestates.question.dto.QuestionPatchDto;
import com.codestates.question.dto.QuestionPostDto;
import com.codestates.question.entity.Question;
import com.codestates.question.mapper.QuestionMapper;
import com.codestates.question.service.QuestionService;
import com.codestates.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v11/questions")
@Validated
@Slf4j
public class QuestionController {
    private final static String QUESTION_DEFAULT_URL = "/v11/questions";
    QuestionService questionService;
    QuestionMapper mapper;
    MemberService memberService;

    public QuestionController(QuestionService questionService, QuestionMapper mapper, MemberService memberService) {
        this.questionService = questionService;
        this.mapper = mapper;
        this.memberService = memberService;
    }
    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionPostDto questionPostDto){
        Question question = questionService.createQuestion(mapper.questionPostDtoToQuestion(questionPostDto));
        URI location = UriCreator.createUri(QUESTION_DEFAULT_URL, question.getQuestionId());

        return ResponseEntity.created(location).build();
    }
    @PatchMapping
    public ResponseEntity patchQuestion(@Valid @RequestBody QuestionPatchDto questionPatchDto){
        Question question = questionService.updateQuestion(mapper.questionPatchDtoToQuestion(questionPatchDto));
        URI location = UriCreator.createUri(QUESTION_DEFAULT_URL, question.getQuestionId());

        return ResponseEntity.created(location).build();
    }
    @GetMapping
    public ResponseEntity getQuestions(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size,
                                     @Positive @RequestParam int option){
        Page<Question> pageQuestions = questionService.findQuestions(page-1,size, option);
        List<Question> questions= pageQuestions.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.questionsToQuestionResponseDto(questions),
                        pageQuestions),
                HttpStatus.OK);
    }
    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") @Positive long questionId,
                                   @Positive @RequestParam int memberId){
        Question findQuestion = questionService.findQuestion(questionId,memberId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.questionToQuestionResponseDto(findQuestion)), HttpStatus.OK
        );
    }
    @DeleteMapping({"/{question-id}"})
    public ResponseEntity getDelete(@PathVariable("question-id") @Positive long questionId,
                                    @Positive @RequestParam int memberId){
        Question deleteQuestion = questionService.deleteQuestion(questionId,memberId);
        URI location = UriCreator.createUri(QUESTION_DEFAULT_URL, deleteQuestion.getQuestionId());

        return ResponseEntity.created(location).build();
    };
}
