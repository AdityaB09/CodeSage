package com.codesage.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "analysis_records")
public class AnalysisRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language;

    @Column(length = 4000)
    private String code;

    @Column(length = 1000)
    private String summary;

    private double explainConfidence;
    private double bugConfidence;

    private Instant createdAt;

    public AnalysisRecord() {
    }

    public AnalysisRecord(String language, String code, String summary,
                          double explainConfidence, double bugConfidence) {
        this.language = language;
        this.code = code;
        this.summary = summary;
        this.explainConfidence = explainConfidence;
        this.bugConfidence = bugConfidence;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }

    public String getSummary() {
        return summary;
    }

    public double getExplainConfidence() {
        return explainConfidence;
    }

    public double getBugConfidence() {
        return bugConfidence;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setExplainConfidence(double explainConfidence) {
        this.explainConfidence = explainConfidence;
    }

    public void setBugConfidence(double bugConfidence) {
        this.bugConfidence = bugConfidence;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
