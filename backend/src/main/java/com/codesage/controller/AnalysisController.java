package com.codesage.controller;

import com.codesage.dto.*;
import com.codesage.model.AnalysisRecord;
import com.codesage.service.AnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/explain")
    public ResponseEntity<ExplainResponse> explain(@RequestBody ExplainRequest request) {
        return ResponseEntity.ok(analysisService.explain(request));
    }

    @PostMapping("/bugs")
    public ResponseEntity<BugReportResponse> bugs(@RequestBody BugReportRequest request) {
        return ResponseEntity.ok(analysisService.bugs(request));
    }

    @PostMapping("/score")
    public ResponseEntity<ScoreResponse> score(@RequestBody ExplainRequest request) {
        return ResponseEntity.ok(analysisService.score(request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<AnalysisRecord>> history() {
        return ResponseEntity.ok(analysisService.history());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("{\"service\":\"api\",\"status\":\"ok\"}");
    }
}
