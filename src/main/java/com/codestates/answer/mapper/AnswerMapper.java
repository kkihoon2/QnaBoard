package com.codestates.answer.mapper;

import com.codestates.answer.dto.AnswerPostDto;
import com.codestates.answer.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    @Mapping(source = "memberId",target = "member.memberId")
    @Mapping(source = "questionId",target = "question.questionId")
    Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto);
}
