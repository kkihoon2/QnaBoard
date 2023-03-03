package com.codestates.question.dto;

import com.codestates.member.entity.Member;
import com.codestates.question.entity.Question;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
@Getter
public class QuestionPostDto {
    @Positive
    private long memberId;
    @NotBlank(message = "제목은 공백이 아니어야 합니다.")
    private String title;
    @NotBlank(message = "내용은 공백이 아니어야 합니다.")
    private String content;
    private Question.QuestionDisclosure questionDisclosure;
    public Member getMember(){
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }
}
