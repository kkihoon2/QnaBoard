package com.codestates.question.dto;

import com.codestates.member.entity.Member;
import com.codestates.question.entity.Question;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
public class QuestionResponseDto {
    @Positive
    private long questionId;
    @Positive
    private long memberId;
    private String name;
    private String title;
    private String content;
    private Question.QuestionDisclosure questionDisclosure;

}
