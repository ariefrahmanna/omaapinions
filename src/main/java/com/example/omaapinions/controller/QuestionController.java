package com.example.omaapinions.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.omaapinions.dto.QuestionDto;
import com.example.omaapinions.dto.SurveyDto;
import com.example.omaapinions.models.Question;
import com.example.omaapinions.service.QuestionService;
import com.example.omaapinions.service.SurveyService;

@Controller
public class QuestionController {

    @SuppressWarnings("FieldMayBeFinal")
    private QuestionService questionService;
    @SuppressWarnings("FieldMayBeFinal")
    private SurveyService surveyService;

    public QuestionController(QuestionService questionService, SurveyService surveyService) {
        this.questionService = questionService;
        this.surveyService = surveyService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/surveys/{surveyId}/questions")
    public String createQuestionForm(@PathVariable long surveyId, Model model) {
        Question question = new Question();
        SurveyDto surveyDto = this.surveyService.findSurveyById(surveyId);

        model.addAttribute("question", question);
        model.addAttribute("survey", surveyDto);

        return "questions-create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/questions/{questionId}/edit")
    public String editQuestion(@PathVariable long questionId, Model model) {
        QuestionDto questionDto = this.questionService.findQuestionById(questionId);

        model.addAttribute("question", questionDto);

        return "questions-edit";
    }

    @PostMapping("/surveys/{surveyId}/questions")
    public String createQuestionForm(@PathVariable Long surveyId,
            @ModelAttribute("question") QuestionDto questionDto, Model model) {
        String route = String.format("/surveys/%d/questions", surveyId);

        this.questionService.createQuestion(surveyId, questionDto);

        return "redirect:" + route;
    }

    @PostMapping("/questions/{questionId}/edit")
    public String updateQuestion(@PathVariable long questionId,
            @ModelAttribute("question") QuestionDto questionDto, Model model) {
        QuestionDto questionDtoById = this.questionService.findQuestionById(questionId);
        long surveyId = questionDtoById.getSurvey().getId();
        SurveyDto surveyDto = this.surveyService.findSurveyById(surveyId);
        String route = String.format("/surveys/%d/questions", surveyId);

        questionDto.setId(questionId);
        questionDto.setSurvey(questionDtoById.getSurvey());

        this.questionService.updateQuestion(questionDto);
        model.addAttribute("survey", surveyDto);

        return "redirect:" + route;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/questions/delete/{questionId}")
    public String deleteQuestion(@PathVariable long questionId) {
        long surveyId = this.questionService.findQuestionById(questionId).getSurvey().getId();
        String route = String.format("/surveys/%d/questions", surveyId);

        this.questionService.deleteQuestion(questionId);

        return "redirect:" + route;
    }
}
