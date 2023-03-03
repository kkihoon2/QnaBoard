package com.codestates.question.service;

import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.member.service.MemberService;
import com.codestates.question.entity.Question;
import com.codestates.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {
    private MemberService memberService;
    private QuestionRepository questionRepository;

    public QuestionService(MemberService memberService, QuestionRepository questionRepository) {
        this.memberService = memberService;
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(Question question) {
        memberService.findVerifiedMember(question.getMember().getMemberId());
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
       if(findQuestion.getQuestionDisclosure().equals(Question.QuestionDisclosure.QUESTION_SECRET)){
           if(memberId!=findQuestion.getMember().getMemberId()){
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
