package com.codesage.dto;

public class ScoreResponse {
    private int loc;
    private int complexity;
    private int nestingDepth;
    private double readabilityScore;
    private double explainConfidence;
    private double bugConfidence;

    public ScoreResponse() {
    }

    public ScoreResponse(int loc, int complexity, int nestingDepth,
                         double readabilityScore, double explainConfidence,
                         double bugConfidence) {
        this.loc = loc;
        this.complexity = complexity;
        this.nestingDepth = nestingDepth;
        this.readabilityScore = readabilityScore;
        this.explainConfidence = explainConfidence;
        this.bugConfidence = bugConfidence;
    }

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public int getNestingDepth() {
        return nestingDepth;
    }

    public void setNestingDepth(int nestingDepth) {
        this.nestingDepth = nestingDepth;
    }

    public double getReadabilityScore() {
        return readabilityScore;
    }

    public void setReadabilityScore(double readabilityScore) {
        this.readabilityScore = readabilityScore;
    }

    public double getExplainConfidence() {
        return explainConfidence;
    }

    public void setExplainConfidence(double explainConfidence) {
        this.explainConfidence = explainConfidence;
    }

    public double getBugConfidence() {
        return bugConfidence;
    }

    public void setBugConfidence(double bugConfidence) {
        this.bugConfidence = bugConfidence;
    }
}
