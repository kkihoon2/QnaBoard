package com.codestates.question.dto;

import com.codestates.member.entity.Member;
import com.codestates.question.entity.Question;
import lombok.Getter;

import javax.validation.constraints.Positive;
@Getter
public class QuestionPatchDto {
    @Positive
    private long questionId;
    @Positive
    private long memberId;
    private String title;
    private String content;
    private Question.QuestionDisclosure questionDisclosure;
    public void setQuestionId(long questionId){this.questionId=questionId;}
    public Member getMember(){
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }
}
