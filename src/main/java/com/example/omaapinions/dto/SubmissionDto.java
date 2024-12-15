package com.example.omaapinions.dto;

import com.example.omaapinions.models.Survey;
import com.example.omaapinions.models.Question;
import com.example.omaapinions.models.UserSurvey;

import lombok.Data;

@Data
public class SubmissionDto {

    private UserSurvey user;
    private Survey survey;
    private Question question;
    private String answer;
}
