package com.example.omaapinions.dto;

import com.example.omaapinions.models.Survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {

    private long id;
    private String prompt;
    private Survey survey;
}
