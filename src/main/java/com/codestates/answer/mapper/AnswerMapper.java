package com.codestates.answer.mapper;

import com.codestates.answer.dto.AnswerPostDto;
import com.codestates.answer.entity.Answer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    Answer answerPostDtoToAnswer(AnswerPostDto answerPostDto);
}
