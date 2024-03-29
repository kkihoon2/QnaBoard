package com.codestates.answer.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
@Getter
public class AnswerPatchDto {
    @Positive
    private long questionId;
    @Positive
    private long memberId;
    @NotBlank(message = "제목은 공백이 아니어야 합니다.")
    private String title;
    @NotBlank(message = "내용은 공백이 아니어야 합니다.")
    private String content;
}
