package com.example.omaapinions.mapper;

import com.example.omaapinions.dto.QuestionDto;
import com.example.omaapinions.models.Question;

public class QuestionMapper {

    public static Question mapToQuestion(QuestionDto questionDto) {
        return Question.builder()
                .id(questionDto.getId())
                .prompt(questionDto.getPrompt())
                .survey(questionDto.getSurvey())
                .build();
    }

    public static QuestionDto mapToQuestionDto(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .prompt(question.getPrompt())
                .survey(question.getSurvey())
                .build();
    }
}
