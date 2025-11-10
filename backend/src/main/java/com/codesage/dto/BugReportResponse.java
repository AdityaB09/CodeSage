package com.codesage.dto;

import java.util.List;

public class BugReportResponse {
    private List<BugDto> bugs;
    private double overallConfidence;

    public BugReportResponse() {
    }

    public BugReportResponse(List<BugDto> bugs, double overallConfidence) {
        this.bugs = bugs;
        this.overallConfidence = overallConfidence;
    }

    public List<BugDto> getBugs() {
        return bugs;
    }

    public void setBugs(List<BugDto> bugs) {
        this.bugs = bugs;
    }

    public double getOverallConfidence() {
        return overallConfidence;
    }

    public void setOverallConfidence(double overallConfidence) {
        this.overallConfidence = overallConfidence;
    }
}
