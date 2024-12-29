package com.example.omaapinions.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.omaapinions.dto.SurveyDto;
import com.example.omaapinions.models.Survey;
import com.example.omaapinions.models.UserSurvey;
import com.example.omaapinions.security.SecurityUtil;
import com.example.omaapinions.service.SubmissionService;
import com.example.omaapinions.service.SurveyService;
import com.example.omaapinions.service.UserService;

import jakarta.validation.Valid;

@Controller
public class SurveyController {

    @SuppressWarnings("FieldMayBeFinal")
    private SurveyService surveyService;
    @SuppressWarnings("FieldMayBeFinal")
    private UserService userService;
    @SuppressWarnings("FieldMayBeFinal")
    private SubmissionService submissionService;

    public SurveyController(SurveyService surveyService, UserService userService, SubmissionService submissionService) {
        this.surveyService = surveyService;
        this.userService = userService;
        this.submissionService = submissionService;
    }

    @GetMapping("/")
    public String getOverview() {
        return "overview";
    }

    @GetMapping("/surveys")
    public String listSurveys(Model model) {
        String username = SecurityUtil.getSessionUser();
        UserSurvey user = new UserSurvey();
        List<SurveyDto> surveys = this.surveyService.findAllSurveys();
        Map<String, List<SurveyDto>> surveysByCategory = surveys.stream()
                .collect(Collectors.groupingBy(SurveyDto::getCategory));
        List<String> categories = new ArrayList<>(surveysByCategory.keySet());

        Collections.sort(categories);

        if (username != null) {
            user = this.userService.findByUsername(username);
        }

        model.addAttribute("user", user);
        model.addAttribute("categories", categories);
        model.addAttribute("surveysByCategory", surveysByCategory);

        return "survey-list";
    }

    @GetMapping("/surveys/search")
    public String searchSurvey(@RequestParam String query, Model model) {
        List<SurveyDto> surveys = this.surveyService.searchSurveys(query);
        Map<String, List<SurveyDto>> surveysByCategory = surveys.stream()
                .collect(Collectors.groupingBy(SurveyDto::getCategory));
        List<String> categories = new ArrayList<>(surveysByCategory.keySet());

        Collections.sort(categories);
        model.addAttribute("categories", categories);
        model.addAttribute("surveysByCategory", surveysByCategory);

        return "survey-list";
    }

    @GetMapping("/surveys/{surveyId}")
    public String surveyDetails(@PathVariable long surveyId, Model model) {
        SurveyDto surveyDto = this.surveyService.findSurveyById(surveyId);
        String username = SecurityUtil.getSessionUser();
        boolean hasTakenSurvey = this.submissionService.surveyTaken(surveyId, username);
        boolean questionsEmpty = surveyDto.getQuestions().isEmpty();
        boolean canTakeSurvey = !hasTakenSurvey && !questionsEmpty;
        long submissionCount = this.submissionService.countSubmissionBySurveyId(surveyId);
        String hoverMessage = hasTakenSurvey
                ? "Survey already taken"
                : questionsEmpty
                        ? "Questions not available"
                        : "";

        model.addAttribute("survey", surveyDto);
        model.addAttribute("canTakeSurvey", canTakeSurvey);
        model.addAttribute("hoverMessage", hoverMessage);
        model.addAttribute("submissionCount", submissionCount);

        return "survey-details";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/surveys/new")
    public String addSurvey(Model model) {
        model.addAttribute("survey", new Survey());

        return "survey-create";
    }

    @PostMapping("/surveys/new")
    public String saveSurvey(@Valid @ModelAttribute("survey") SurveyDto surveyDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("survey", surveyDto);

            return "survey-create";
        }

        this.surveyService.saveSurvey(surveyDto);

        return "redirect:/surveys?add";
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/surveys/{surveyId}/submit")
    public String fillSurvey(@PathVariable long surveyId, Model model) {
        String email = SecurityUtil.getSessionUser();

        if (this.submissionService.surveyTaken(surveyId, email)) {
            return "redirect:/surveys?surveyTaken";
        }

        SurveyDto surveyDto = this.surveyService.findSurveyById(surveyId);

        model.addAttribute("survey", surveyDto);

        return "questions-list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/surveys/{surveyId}/edit")
    public String editSurvey(@PathVariable long surveyId, Model model) {
        SurveyDto surveyDto = this.surveyService.findSurveyById(surveyId);

        model.addAttribute("survey", surveyDto);

        return "surveys-edit";
    }

    @PostMapping("/surveys/{surveyId}/edit")
    public String updateSurvey(@PathVariable long surveyId,
            @Valid @ModelAttribute("survey") SurveyDto surveyDto, BindingResult result) {
        String route = String.format("/surveys/%d?edit", surveyId);

        if (result.hasErrors()) {
            return "surveys-edit";
        }

        surveyDto.setId(surveyId);
        this.surveyService.updateSurvey(surveyDto);

        return "redirect:" + route;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/surveys/{surveyId}/delete")
    public String deleteSurvey(@PathVariable long surveyId) {
        this.surveyService.delete(surveyId);

        return "redirect:/surveys?del";
    }
}
