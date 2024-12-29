package com.example.omaapinions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.omaapinions.models.Submission;
import com.example.omaapinions.models.UserSurvey;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    boolean existsBySurveyIdAndUser(Long surveyId, UserSurvey user);

    void deleteByQuestionId(Long questionId);

    void deleteBySurveyId(Long surveyId);

    @Query("SELECT COUNT(DISTINCT s.user) FROM Submission s WHERE s.survey.id = :surveyId")
    long countSubmissionBySurveyId(@Param("surveyId") Long surveyId);

    @Query("SELECT COUNT(DISTINCT s.survey) FROM Submission s WHERE s.user.id = :userId")
    long countSubmissionByUserId(@Param("userId") Long userId);
}
