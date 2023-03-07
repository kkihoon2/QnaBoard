package com.codestates.answer.service;

import com.codestates.answer.entity.Answer;
import com.codestates.answer.repository.AnswerRepository;
import com.codestates.member.repository.MemberRepository;
import com.codestates.member.service.MemberService;
import com.codestates.question.service.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private AnswerRepository answerRepository;
    private MemberService memberService;
    private MemberRepository memberRepository;
    private QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, MemberService memberService, MemberRepository memberRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.questionService = questionService;
    }

    public Answer createAnswer(Answer answer, long memberId){
//           questionService.verifiedChecker(answer.getQuestion().getMember().getMemberId(),memberId);
           answerRepository.save(answer);
           return answer;
    }

}
