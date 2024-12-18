package com.example.omaapinions.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.omaapinions.mapper.SurveyMapper.mapToSurvey;
import com.example.omaapinions.models.Submission;
import com.example.omaapinions.models.Survey;
import com.example.omaapinions.models.Question;
import com.example.omaapinions.models.UserSurvey;
import com.example.omaapinions.security.SecurityUtil;
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

    public SubmissionController(SurveyService surveyService, UserService userService,
            SubmissionService submissionService) {
        this.surveyService = surveyService;
        this.userService = userService;
        this.submissionService = submissionService;
    }

    @PostMapping("/submitSurvey")
    public String submitSurvey(@RequestParam("survey_id") Long surveyId, @RequestParam Map<String, String> answers) {
        UserSurvey user = new UserSurvey();
        Survey survey = mapToSurvey(this.surveyService.findSurveyById(surveyId));
        String username = SecurityUtil.getSessionUser();

        if (username != null) {
            user = this.userService.findByEmail(username);
        }

        for (Map.Entry<String, String> entry : answers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.startsWith("answers[") && key.endsWith("]")) {
                String questionIdStr = key.substring(8, key.length() - 1);
                Long questionId = Long.valueOf(questionIdStr);
                Submission submission = new Submission();
                Question question = new Question();

                question.setId(questionId);
                submission.setUser(user);
                submission.setSurvey(survey);
                submission.setQuestion(question);
                submission.setAnswer(value);

                this.submissionService.save(submission);
            }
        }

        return "redirect:/surveys?submit";
    }
}
