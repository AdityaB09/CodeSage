package com.codesage.service;

import com.codesage.dto.BugDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleEngineService {

    public List<BugDto> detectBugs(String code, String language) {
        List<BugDto> bugs = new ArrayList<>();
        if (code == null || code.isBlank()) {
            return bugs;
        }

        String[] lines = code.split("\\R");

        // Rule: possible null pointer
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.contains("get") && line.toLowerCase().contains("null")) {
                bugs.add(new BugDto(
                        "B_NPE_" + (i + 1),
                        i + 1,
                        "error",
                        "possible_null_pointer",
                        "This line mentions null and a getter call. There may be a null pointer risk.",
                        "Check for null before calling methods on this variable.",
                        line + " // TODO: add null check here"
                ));
            }
        }

        // Rule: unused variable (very naive)
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if ((line.startsWith("int ") || line.startsWith("String ")) && line.endsWith(";")
                    && line.contains("=")) {
                String varName = extractVarName(line);
                if (varName != null && !isUsedLater(lines, i + 1, varName)) {
                    bugs.add(new BugDto(
                            "B_UNUSED_" + (i + 1),
                            i + 1,
                            "warning",
                            "unused_variable",
                            "Variable '" + varName + "' seems to be declared and not used later.",
                            "Remove the variable or use it in the code.",
                            "// removed unused variable '" + varName + "'"
                    ));
                }
            }
        }

        // Rule: nested loops (performance)
        int loopCount = 0;
        for (String l : lines) {
            String trimmed = l.trim().toLowerCase();
            if (trimmed.startsWith("for ") || trimmed.startsWith("for(") ||
                trimmed.startsWith("while ") || trimmed.startsWith("while(")) {
                loopCount++;
            }
        }
        if (loopCount >= 2) {
            bugs.add(new BugDto(
                    "B_PERF_NESTED",
                    0,
                    "info",
                    "nested_loops",
                    "Detected multiple loop constructs; this may be an O(n^2) pattern.",
                    "Consider optimizing nested loops or using more efficient data structures.",
                    null
            ));
        }

        return bugs;
    }

    private String extractVarName(String line) {
        try {
            String[] parts = line.replace(";", "").split("=");
            String left = parts[0].trim();        // int x
            String[] tokens = left.split("\\s+");
            return tokens[tokens.length - 1];
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isUsedLater(String[] lines, int startIndex, String varName) {
        for (int i = startIndex; i < lines.length; i++) {
            if (lines[i].contains(varName)) {
                return true;
            }
        }
        return false;
    }
}
