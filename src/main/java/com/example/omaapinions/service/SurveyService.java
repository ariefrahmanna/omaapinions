package com.example.omaapinions.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.omaapinions.dto.SurveyDto;
import static com.example.omaapinions.mapper.SurveyMapper.mapToSurvey;
import static com.example.omaapinions.mapper.SurveyMapper.mapToSurveyDto;
import com.example.omaapinions.models.Survey;
import com.example.omaapinions.repository.SurveyRepository;

@Service
public class SurveyService {

    @SuppressWarnings("FieldMayBeFinal")
    private SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public List<SurveyDto> findAllSurveys() {
        List<Survey> surveys = this.surveyRepository.findAll();

        return surveys.stream().map((survey) -> mapToSurveyDto(survey)).collect(Collectors.toList());
    }

    public void saveSurvey(SurveyDto surveyDto) {
        Survey survey = mapToSurvey(surveyDto);

        this.surveyRepository.save(survey);
    }

    public SurveyDto findSurveyById(long surveyId) {
        Survey survey = this.surveyRepository.findById(surveyId).get();
        SurveyDto surveyDto = mapToSurveyDto(survey);

        return surveyDto;
    }

    public void updateSurvey(SurveyDto surveyDto) {
        Survey survey = mapToSurvey(surveyDto);

        this.surveyRepository.save(survey);
    }

    public void delete(long surveyId) {
        this.surveyRepository.deleteById(surveyId);
    }

    public List<SurveyDto> searchSurveys(String query) {
        List<Survey> surveys = this.surveyRepository.searchSurveys(query);

        return surveys.stream().map((survey) -> mapToSurveyDto(survey)).collect(Collectors.toList());
    }
}