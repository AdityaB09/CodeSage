package com.codesage.dto;

import java.util.List;

public class ExplainResponse {
    private String summary;
    private List<String> trace;
    private double confidence;

    public ExplainResponse() {
    }

    public ExplainResponse(String summary, List<String> trace, double confidence) {
        this.summary = summary;
        this.trace = trace;
        this.confidence = confidence;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getTrace() {
        return trace;
    }

    public void setTrace(List<String> trace) {
        this.trace = trace;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
