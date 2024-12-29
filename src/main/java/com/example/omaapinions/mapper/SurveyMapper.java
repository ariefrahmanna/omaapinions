package com.example.omaapinions.mapper;

import java.util.stream.Collectors;

import com.example.omaapinions.dto.SurveyDto;
import static com.example.omaapinions.mapper.QuestionMapper.mapToQuestionDto;
import com.example.omaapinions.models.Survey;

public class SurveyMapper {

    public static SurveyDto mapToSurveyDto(Survey survey) {
        return SurveyDto.builder()
                .id(survey.getId())
                .title(survey.getTitle())
                .category(survey.getCategory())
                .duration(survey.getDuration())
                .description(survey.getDescription())
                .questions(survey.getQuestions().stream().map((question) -> mapToQuestionDto(question))
                        .collect(Collectors.toList()))
                .build();
    }

    public static Survey mapToSurvey(SurveyDto survey) {
        return Survey.builder()
                .id(survey.getId())
                .title(survey.getTitle())
                .category(survey.getCategory())
                .duration(survey.getDuration())
                .description(survey.getDescription())
                .build();
    }
}
