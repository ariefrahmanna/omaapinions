package com.example.omaapinions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.omaapinions.models.UserSurvey;

public interface UserRepository extends JpaRepository<UserSurvey, Long> {

    UserSurvey findByEmail(String email);

    UserSurvey findByUsername(String userName);

    UserSurvey findFirstByUsername(String username);

    List<UserSurvey> findAllByOrderBySubmissionCountDesc();
}
