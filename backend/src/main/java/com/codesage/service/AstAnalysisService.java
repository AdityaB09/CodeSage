package com.codesage.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AstAnalysisService {

    // Very simple heuristics placeholder instead of ANTLR.
    public int countLines(String code) {
        if (code == null || code.isBlank()) return 0;
        return (int) Arrays.stream(code.split("\\R"))
                .filter(line -> !line.trim().isEmpty())
                .count();
    }

    public int estimateComplexity(String code) {
        if (code == null) return 0;
        int complexity = 1; // base
        String lower = code.toLowerCase();
        complexity += countOccurrences(lower, "if");
        complexity += countOccurrences(lower, "for");
        complexity += countOccurrences(lower, "while");
        complexity += countOccurrences(lower, "case ");
        return complexity;
    }

    public int estimateNestingDepth(String code) {
        if (code == null) return 0;
        int depth = 0;
        int maxDepth = 0;
        for (char c : code.toCharArray()) {
            if (c == '{') {
                depth++;
                if (depth > maxDepth) {
                    maxDepth = depth;
                }
            } else if (c == '}') {
                depth = Math.max(0, depth - 1);
            }
        }
        return maxDepth;
    }

    private int countOccurrences(String text, String token) {
        int index = 0;
        int count = 0;
        while ((index = text.indexOf(token, index)) != -1) {
            count++;
            index += token.length();
        }
        return count;
    }
}
