package com.codestates.answer.service;

import com.codestates.answer.entity.Answer;
import com.codestates.answer.repository.AnswerRepository;
import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.member.entity.Member;
import com.codestates.member.repository.MemberRepository;
import com.codestates.member.service.MemberService;
import com.codestates.question.entity.Question;
import com.codestates.question.repository.QuestionRepository;
import com.codestates.question.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {
    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;
    private MemberService memberService;
    private MemberRepository memberRepository;
    private QuestionService questionService;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, MemberService memberService, MemberRepository memberRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.questionService = questionService;
    }

    public Answer createAnswer(Answer answer){
         verified(answer);//question이 secret 모드면 작성자 본인과 관리자만 답변을 달 수 있다.
         answerRepository.save(answer);
         changeQuestionStatus(answer);

         return answer;
    }
    public Answer updateAnswer(Answer answer,long answerId){//답변자 본인만 답변 수정가능
        Answer findAnswer = checkPatchVerified(answer,answerId);

        Optional.ofNullable(answer.getTitle()).ifPresent(title->findAnswer.setTitle(title));
        Optional.ofNullable(answer.getContent()).ifPresent(content->findAnswer.getContent());
        return answerRepository.save(findAnswer);
    }
    public void deleteMember(long memberId,long answerId){
        Answer findAnswer = checkDeleteVerified(memberId,answerId);
        answerRepository.delete(findAnswer);
    }
    public Answer checkDeleteVerified(long memberId,long answerId){
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        Answer findAnswer = optionalAnswer.orElseThrow(()->new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(()->new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        if(findAnswer.getMember().getMemberId()!=memberId&&!findMember.getEmail().equals("admin@gmail.com")){
            throw new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND);
        }
        return findAnswer;
    }
    public Answer checkPatchVerified(Answer answer,long answerId)//답변자 본인만 답변 수정가능
    {
        Optional<Answer>optionalAnswer = answerRepository.findById(answerId);
        Answer findAnswer = optionalAnswer.orElseThrow(()->new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        Optional<Member>optionalMember = memberRepository.findById(answer.getMember().getMemberId());

        if(answer.getMember().getMemberId()!= findAnswer.getAnswerId()){
            throw new BusinessLogicException(ExceptionCode.ANSWER_MEMBER_NOT_MATCH);
        }
        return findAnswer;
    }
    public void changeQuestionStatus(Answer answer)
    {
        Optional<Question>optionalQuestion=questionRepository.findById(answer.getQuestion().getQuestionId());
        Question question = optionalQuestion.orElseThrow(()->new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        if(question.getQuestionStatus().equals(Question.QuestionStatus.QUESTION_REGISTRATION)){
            question.setQuestionStatus(Question.QuestionStatus.QUESTION_ANSWERED);
            questionRepository.save(question);
        }
    }
    public void verified(Answer answer){ //question이 secret 모드면 작성자 본인과 관리자만 답변을 달 수 있다.
        Optional<Question>optionalQuestion = questionRepository.findById(answer.getQuestion().getQuestionId());
        Question question = optionalQuestion.orElseThrow(()->new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        Optional<Member>optionalMember = memberRepository.findById(answer.getMember().getMemberId());
        Member member = optionalMember.orElseThrow(()->new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        if(question.getQuestionDisclosure().equals(Question.QuestionDisclosure.QUESTION_SECRET)){
            if(question.getMember().getMemberId()!=answer.getMember().getMemberId()&&!member.getEmail().equals("admin@gmail.com")){
                throw new BusinessLogicException(ExceptionCode.SECRET_QUESTION_QUESTIONID_NOT_MATCH);
            }
        }
    }

}
