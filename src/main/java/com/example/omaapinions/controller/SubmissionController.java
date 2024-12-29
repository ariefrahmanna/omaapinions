package com.example.omaapinions.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.omaapinions.mapper.QuestionMapper.mapToQuestion;
import static com.example.omaapinions.mapper.SurveyMapper.mapToSurvey;
import com.example.omaapinions.models.Question;
import com.example.omaapinions.models.Submission;
import com.example.omaapinions.models.Survey;
import com.example.omaapinions.models.UserSurvey;
import com.example.omaapinions.security.SecurityUtil;
import com.example.omaapinions.service.QuestionService;
import com.example.omaapinions.service.SubmissionService;
import com.example.omaapinions.service.SurveyService;
import com.example.omaapinions.service.UserService;

@Controller
public class SubmissionController {

    @SuppressWarnings("FieldMayBeFinal")
    private SurveyService surveyService;
    @SuppressWarnings("FieldMayBeFinal")
    private UserService userService;
    @SuppressWarnings("FieldMayBeFinal")
    private SubmissionService submissionService;
    @SuppressWarnings("FieldMayBeFinal")
    private QuestionService questionService;

    public SubmissionController(SurveyService surveyService, UserService userService,
            SubmissionService submissionService, QuestionService questionService) {
        this.surveyService = surveyService;
        this.userService = userService;
        this.submissionService = submissionService;
        this.questionService = questionService;
    }

    @PostMapping("/surveys/{surveyId}/submit")
    public String submitSurvey(@RequestParam("survey_id") Long surveyId, @RequestParam Map<String, String> answers) {
        UserSurvey user = new UserSurvey();
        Survey survey = mapToSurvey(this.surveyService.findSurveyById(surveyId));
        String email = SecurityUtil.getSessionUser();

        if (email != null) {
            user = this.userService.findByEmail(email);
        }

        for (Map.Entry<String, String> entry : answers.entrySet()) {
            String key = entry.getKey();
            String answer = entry.getValue();

            if (key.startsWith("answers[") && key.endsWith("]")) {
                String questionIdStr = key.substring(8, key.length() - 1);
                Long questionId = Long.valueOf(questionIdStr);
                Question question = mapToQuestion(this.questionService.findQuestionById(questionId));
                Submission submission = new Submission();

                submission.setUser(user);
                submission.setSurvey(survey);
                submission.setQuestion(question);
                submission.setAnswer(answer);

                this.submissionService.save(submission);
            }

            this.submissionService.updateUserSubmissionCount(user);
        }

        return "redirect:/surveys?submit";
    }

    @GetMapping("/leaderboard")
    public String getLeaderboard(Model model) {
        List<UserSurvey> users = userService.usersBySubmissionCount();

        for (UserSurvey user : users) {
            this.submissionService.updateUserSubmissionCount(user);
        }

        model.addAttribute("users", users);

        return "leaderboard";
    }
}
