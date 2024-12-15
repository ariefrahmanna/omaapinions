package com.example.omaapinions.mapper;

import com.example.omaapinions.dto.SubmissionDto;
import com.example.omaapinions.models.Submission;

public class SubmissionMapper {

    public static Submission mapToSubmission(SubmissionDto submissionDto) {
        return Submission.builder()
                .user(submissionDto.getUser())
                .survey(submissionDto.getSurvey())
                .question(submissionDto.getQuestion())
                .answer(submissionDto.getAnswer())
                .build();
    }
}
