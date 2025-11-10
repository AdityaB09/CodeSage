package com.codesage.dto;

public class BugDto {
    private String id;
    private int line;
    private String severity;
    private String type;
    private String message;
    private String suggestion;
    private String fixedSnippet;

    public BugDto() {
    }

    public BugDto(String id, int line, String severity, String type, String message,
                  String suggestion, String fixedSnippet) {
        this.id = id;
        this.line = line;
        this.severity = severity;
        this.type = type;
        this.message = message;
        this.suggestion = suggestion;
        this.fixedSnippet = fixedSnippet;
    }

    public String getId() {
        return id;
    }

    public int getLine() {
        return line;
    }

    public String getSeverity() {
        return severity;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public String getFixedSnippet() {
        return fixedSnippet;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public void setFixedSnippet(String fixedSnippet) {
        this.fixedSnippet = fixedSnippet;
    }
}
