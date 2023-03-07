package com.codestates.question.service;

import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.member.entity.Member;
import com.codestates.member.repository.MemberRepository;
import com.codestates.member.service.MemberService;
import com.codestates.question.entity.Question;
import com.codestates.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {
    private MemberService memberService;

    private QuestionRepository questionRepository;
    private MemberRepository memberRepository;
    public QuestionService(MemberService memberService, QuestionRepository questionRepository, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.questionRepository = questionRepository;
        this.memberRepository = memberRepository;
    }

    public Question createQuestion(Question question) {
        memberService.findVerifiedMember(question.getMember().getMemberId());
        //Member findMember=memberService.findVerifiedMember(question.getMember().getMemberId());
        //question.setMember(findMember);
        questionRepository.save(question);
        return question;
    }
    public Question findQuestion(long questionId , long memberId){
        verifiedChecker(questionId,memberId);
        Optional<Question>optionalQuestion=questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(()->new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        return findQuestion;
    }
    public void verifiedChecker(long questionId,long memberId){
        Optional<Question>optionalQuestion=questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(()->new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        Optional<Member>optionalMember=memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(()->new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        if(findQuestion.getQuestionDisclosure().equals(Question.QuestionDisclosure.QUESTION_SECRET)){
           if(memberId!=findQuestion.getMember().getMemberId()&&!findMember.getEmail().equals("admin@gmail.com")){//관리자 검증 삭제, 찾기
               System.out.println(findMember.getEmail());
               throw new BusinessLogicException(ExceptionCode.QUESTION_MEMBER_NOT_MATCH);
           }
       }
    }

    public Question updateQuestion(Question question) {
            checkQuestionMemberId(question);

            Optional.ofNullable(question.getTitle())
                    .ifPresent(title -> question.setTitle(title));
            Optional.ofNullable(question.getContent())
                    .ifPresent(content -> question.setContent(content));
            Optional.ofNullable(question.getTitle())
                    .ifPresent(title -> question.setTitle(title));
            Optional.ofNullable(question.getQuestionDisclosure())
                    .ifPresent( questionDisclosure-> question.setQuestionDisclosure(questionDisclosure));


        return questionRepository.save(question);
    }
    public Question deleteQuestion(long questionId,long memberId){
        verifiedChecker(questionId,memberId);
        Optional<Question>optionalQuestion=questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(()->new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        Optional.ofNullable(findQuestion.getQuestionStatus())
                .ifPresent( questionStatus-> findQuestion.setQuestionStatus(Question.QuestionStatus.QUESTION_DELETED));
        return questionRepository.save(findQuestion);
    }
    public void checkQuestionMemberId(Question question){
        Optional<Question> optionalQuestion = questionRepository.findById(question.getQuestionId());
        Question findQuestion = optionalQuestion.orElseThrow(()->new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        if(question.getMember().getMemberId()!=findQuestion.getMember().getMemberId()){
            throw new BusinessLogicException(ExceptionCode.QUESTION_MEMBER_NOT_MATCH);
        }
    }

    private Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        return findQuestion;
    }
}
