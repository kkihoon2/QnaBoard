package com.codestates.question.mapper;

import com.codestates.order.dto.OrderPostDto;
import com.codestates.order.entity.Order;
import com.codestates.question.dto.QuestionPatchDto;
import com.codestates.question.dto.QuestionPostDto;
import com.codestates.question.dto.QuestionResponseDto;
import com.codestates.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question questionPostDtoToQuestion(QuestionPostDto questionPostDto);
    Question questionPatchDtoToQuestion(QuestionPatchDto questionPatchDto);
    @Mapping(source = "member.memberId",target = "memberId")
    @Mapping(source = "member.name",target = "name")
    QuestionResponseDto questionToQuestionResponseDto(Question question);

    List<QuestionResponseDto>questionsToQuestionResponseDto(List<Question> questions);
}
