package com.example.omaapinions.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "surveys")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String category;
    private Integer duration;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToMany(mappedBy = "survey", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Question> questions = new ArrayList<>();
    @OneToMany(mappedBy = "survey", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Submission> submissions = new ArrayList<>();
}
