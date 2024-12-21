package com.example.omaapinions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.omaapinions.models.Submission;
import com.example.omaapinions.models.UserSurvey;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    boolean existsBySurveyIdAndUser(Long surveyId, UserSurvey user);

    void deleteByQuestionId(Long questionId);

    void deleteBySurveyId(Long surveyId);
}
