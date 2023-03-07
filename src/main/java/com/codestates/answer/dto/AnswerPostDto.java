package com.codestates.answer.dto;

import com.codestates.question.entity.Question;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class AnswerPostDto {
    @Positive
    private long questionId;
    @NotBlank(message = "제목은 공백이 아니어야 합니다.")
    private String title;
    @NotBlank(message = "내용은 공백이 아니어야 합니다.")
    private String content;

    private long memberId;
    public Question getQuestion(){
        Question question = new Question();
        question.setQuestionId(questionId);
        return question;
    }
}
