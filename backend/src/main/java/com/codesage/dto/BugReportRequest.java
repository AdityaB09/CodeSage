package com.codesage.dto;

public class BugReportRequest {
    private String code;
    private String language;

    public BugReportRequest() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
