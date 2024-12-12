package com.example.omaapinions.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDto {

    private long id;
    @NotEmpty(message = "Title should not be empty")
    private String title;
    @NotEmpty(message = "Category should not be empty")
    private String category;
    @NotNull(message = "Duration should not be empty")
    private Integer duration;
    private List<QuestionDto> questions;
}
