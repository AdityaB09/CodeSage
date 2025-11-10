package com.codesage.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Placeholder for DJL + CodeBERT / CodeT5 based analysis.
@Service
public class MlAnalysisService {

    public String summarize(String code, String language, String mode) {
        if (code == null || code.isBlank()) {
            return "No code provided.";
        }

        String lang = language == null ? "code" : language.toLowerCase();
        if ("student".equalsIgnoreCase(mode)) {
            return "This " + lang + " snippet defines some logic. The tool sees variables, control structures, and method calls, and explains them for a learner.";
        } else {
            return "This " + lang + " snippet implements some business logic using variables, loops, and conditions. It can be optimized or refactored based on the analysis.";
        }
    }

    public double estimateExplainConfidence(String code) {
        if (code == null || code.isBlank()) return 0.4;
        int length = code.length();
        if (length < 40) return 0.6;
        if (length < 200) return 0.8;
        return 0.9;
    }

    public double estimateBugConfidence(String code) {
        if (code == null || code.isBlank()) return 0.3;
        int score = 0;
        if (code.contains("null")) score += 2;
        if (code.toLowerCase().contains("todo")) score += 2;
        if (code.toLowerCase().contains("fixme")) score += 2;
        if (score == 0) return 0.5;
        if (score <= 2) return 0.7;
        return 0.85;
    }

    public List<String> buildBaseTrace(String language) {
        List<String> trace = new ArrayList<>();
        trace.add("Parsed the snippet and built a simple structural representation.");
        trace.add("Ran a simulated ML model to recognize patterns in the code.");
        trace.add("Combined ML hints with rule-based checks to produce this explanation.");
        return trace;
    }
}
