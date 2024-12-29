package com.example.omaapinions.service;

import org.springframework.stereotype.Service;

import com.example.omaapinions.dto.QuestionDto;
import static com.example.omaapinions.mapper.QuestionMapper.mapToQuestion;
import static com.example.omaapinions.mapper.QuestionMapper.mapToQuestionDto;
import com.example.omaapinions.models.Question;
import com.example.omaapinions.models.Survey;
import com.example.omaapinions.repository.QuestionRepository;
import com.example.omaapinions.repository.SubmissionRepository;
import com.example.omaapinions.repository.SurveyRepository;

import jakarta.transaction.Transactional;

@Service
public class QuestionService {

    @SuppressWarnings("FieldMayBeFinal")
    private QuestionRepository questionRepository;
    @SuppressWarnings("FieldMayBeFinal")
    private SurveyRepository surveyRepository;
    @SuppressWarnings("FieldMayBeFinal")
    private SubmissionRepository submissionRepository;

    public QuestionService(QuestionRepository questionRepository, SurveyRepository surveyRepository,
            SubmissionRepository submissionRepository) {
        this.questionRepository = questionRepository;
        this.surveyRepository = surveyRepository;
        this.submissionRepository = submissionRepository;
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

    @Transactional
    public void deleteQuestion(long questionId) {
        this.submissionRepository.deleteByQuestionId(questionId);
        this.questionRepository.deleteById(questionId);
    }
}
