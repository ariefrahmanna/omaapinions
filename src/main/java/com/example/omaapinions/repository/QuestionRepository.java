package com.example.omaapinions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.omaapinions.models.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
