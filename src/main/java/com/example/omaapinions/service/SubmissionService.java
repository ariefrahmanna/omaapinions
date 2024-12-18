package com.example.omaapinions.service;

import org.springframework.stereotype.Service;

import com.example.omaapinions.models.Submission;
import com.example.omaapinions.models.UserSurvey;
import com.example.omaapinions.repository.SubmissionRepository;
import com.example.omaapinions.repository.UserRepository;

@Service
public class SubmissionService {

    @SuppressWarnings("FieldMayBeFinal")
    private SubmissionRepository submissionRepository;
    @SuppressWarnings("FieldMayBeFinal")
    private UserRepository userRepository;

    public SubmissionService(SubmissionRepository submissionRepository, UserRepository userRepository) {
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
    }

    public void save(Submission submission) {
        this.submissionRepository.save(submission);
    }

    public boolean surveyTaken(Long surveyId, String username) {
        UserSurvey user = this.userRepository.findByEmail(username);

        return this.submissionRepository.existsBySurveyIdAndUser(surveyId, user);
    }
}
