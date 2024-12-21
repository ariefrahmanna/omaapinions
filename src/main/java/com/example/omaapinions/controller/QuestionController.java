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
    @GetMapping("/questions/new/{surveyId}")
    public String createQuestionForm(@PathVariable("surveyId") long surveyId, Model model) {
        Question question = new Question();
        SurveyDto surveyDto = this.surveyService.findSurveyById(surveyId);

        model.addAttribute("question", question);
        model.addAttribute("survey", surveyDto);

        return "questions-create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/questions/edit/{questionId}")
    public String editQuestion(@PathVariable("questionId") long questionId, Model model) {
        QuestionDto questionDto = this.questionService.findQuestionById(questionId);

        model.addAttribute("question", questionDto);

        return "questions-edit";
    }

    @PostMapping("/questions/{surveyId}")
    public String createQuestionForm(@PathVariable("surveyId") Long surveyId,
            @ModelAttribute("question") QuestionDto questionDto, Model model) {
        this.questionService.createQuestion(surveyId, questionDto);

        return "redirect:/questions/new/" + surveyId;
    }

    @PostMapping("/questions/edit/{questionId}")
    public String updateQuestion(@PathVariable("questionId") long questionId,
            @ModelAttribute("question") QuestionDto questionDto, Model model) {
        QuestionDto questionDtoById = this.questionService.findQuestionById(questionId);

        questionDto.setId(questionId);
        questionDto.setSurvey(questionDtoById.getSurvey());
        this.questionService.updateQuestion(questionDto);

        return ("redirect:/questions/new/" + questionDtoById.getSurvey().getId());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/questions/delete/{questionId}")
    public String deleteQuestion(@PathVariable("questionId") long questionId) {
        long surveyId = this.questionService.findQuestionById(questionId).getSurvey().getId();

        this.questionService.deleteQuestion(questionId);

        return "redirect:/questions/new/" + surveyId;
    }
}
