package com.example.omaapinions.service;

import org.springframework.stereotype.Service;

import com.example.omaapinions.dto.QuestionDto;
import static com.example.omaapinions.mapper.QuestionMapper.mapToQuestion;
import static com.example.omaapinions.mapper.QuestionMapper.mapToQuestionDto;
import com.example.omaapinions.models.Survey;
import com.example.omaapinions.models.Question;
import com.example.omaapinions.repository.QuestionRepository;
import com.example.omaapinions.repository.SurveyRepository;

@Service
public class QuestionService {

    @SuppressWarnings("FieldMayBeFinal")
    private QuestionRepository questionRepository;
    @SuppressWarnings("FieldMayBeFinal")
    private SurveyRepository surveyRepository;

    public QuestionService(QuestionRepository questionRepository, SurveyRepository surveyRepository) {
        this.questionRepository = questionRepository;
        this.surveyRepository = surveyRepository;
    }

    public void createQuestion(Long surveyId, QuestionDto questionDto) {
        Survey survey = this.surveyRepository.findById(surveyId).get();
        Question question = mapToQuestion(questionDto);

        question.setSurvey(survey);
        this.questionRepository.save(question);
    }

    public QuestionDto findQuestionById(long questionId) {
        Question question = this.questionRepository.findById(questionId).get();
        QuestionDto questionDto = mapToQuestionDto(question);

        return questionDto;
    }

    public void updateQuestion(QuestionDto questionDto) {
        Question question = mapToQuestion(questionDto);

        this.questionRepository.save(question);
    }

    public void deleteQuestion(long questionId) {
        this.questionRepository.deleteById(questionId);
    }
}
