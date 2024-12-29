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

    public boolean surveyTaken(Long surveyId, String email) {
        UserSurvey user = this.userRepository.findByEmail(email);

        return this.submissionRepository.existsBySurveyIdAndUser(surveyId, user);
    }

    public long countSubmissionBySurveyId(long surveyId) {
        return this.submissionRepository.countSubmissionBySurveyId(surveyId);
    }

    public void updateUserSubmissionCount(UserSurvey user) {
        long submissionCount = this.submissionRepository.countSubmissionByUserId(user.getId());

        user.setSubmissionCount(submissionCount);
        this.userRepository.save(user);
    }
}