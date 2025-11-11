package com.codesage.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Placeholder for a local "ML-style" analysis.
// No DJL / CodeBERT / ANTLR here – just lightweight heuristics.
@Service
public class MlAnalysisService {

    public String summarize(String code, String language, String mode) {
        if (code == null || code.isBlank()) {
            return "No code provided.";
        }

        String lang = language == null ? "code" : language.toLowerCase();
        String trimmed = code.trim();

        boolean hasLoop = trimmed.contains("for") || trimmed.contains("while");
        boolean hasIf = trimmed.contains("if");
        boolean hasMethod = trimmed.contains("(") && trimmed.contains(")");

        boolean isStudent = "student".equalsIgnoreCase(mode);

        if (isStudent) {
            // Student-friendly teaching style.
            StringBuilder sb = new StringBuilder();
            sb.append("This ").append(lang).append(" snippet defines some logic. ");
            if (hasMethod) {
                sb.append("It uses a function or method to group related steps together. ");
            }
            if (hasLoop) {
                sb.append("Inside the code, there is a loop that repeats a block of statements. ");
            }
            if (hasIf) {
                sb.append("There is also an if-condition that decides which path the program will take. ");
            }
            sb.append("You can read it line by line to understand how the data moves through the code.");
            return sb.toString();
        } else {
            // Developer-focused style.
            StringBuilder sb = new StringBuilder();
            sb.append("This ").append(lang).append(" snippet implements some business logic ");
            if (hasLoop) {
                sb.append("using one or more loops ");
            }
            if (hasIf) {
                sb.append("and conditional branches ");
            }
            sb.append("over its inputs. ");

            sb.append("It can likely be refactored or optimized by reviewing variable usage, ");
            sb.append("control-flow complexity, and potential edge cases such as null values or empty inputs.");
            return sb.toString();
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
        if (code.toLowerCase().contains("null")) score += 2;
        if (code.toLowerCase().contains("todo")) score += 2;
        if (code.toLowerCase().contains("fixme")) score += 2;
        if (score == 0) return 0.5;
        if (score <= 2) return 0.7;
        return 0.85;
    }

    public List<String> buildBaseTrace(String language, String mode) {
        List<String> trace = new ArrayList<>();
        String lang = language == null ? "code" : language.toLowerCase();

        trace.add("Parsed the " + lang + " snippet and built a simple structural representation.");
        trace.add("Ran a lightweight pattern recognizer (no external models) to understand loops, conditions, and method structure.");
        trace.add("Combined heuristic ML-style hints with rule-based checks to produce this explanation.");

        if ("student".equalsIgnoreCase(mode)) {
            trace.add("Used student mode: emphasised line-by-line behaviour and basic concepts in the explanation.");
        } else {
            trace.add("Used developer mode: emphasised performance, code smells, and refactoring hints.");
        }

        return trace;
    }
}
