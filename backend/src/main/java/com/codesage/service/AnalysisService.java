package com.codesage.service;

import com.codesage.dto.*;
import com.codesage.model.AnalysisRecord;
import com.codesage.repository.AnalysisRecordRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AnalysisService {

    private final AstAnalysisService astAnalysisService;
    private final MlAnalysisService mlAnalysisService;
    private final RuleEngineService ruleEngineService;
    private final ScoringService scoringService;
    private final AnalysisRecordRepository repository;

    private final boolean historyEnabled;

    public AnalysisService(
            AstAnalysisService astAnalysisService,
            MlAnalysisService mlAnalysisService,
            RuleEngineService ruleEngineService,
            ScoringService scoringService,
            AnalysisRecordRepository repository,
            @Value("${codesage.history-enabled:true}") boolean historyEnabled
    ) {
        this.astAnalysisService = astAnalysisService;
        this.mlAnalysisService = mlAnalysisService;
        this.ruleEngineService = ruleEngineService;
        this.scoringService = scoringService;
        this.repository = repository;
        this.historyEnabled = historyEnabled;
    }

    public ExplainResponse explain(ExplainRequest request) {
        String code = request.getCode();
        String language = request.getLanguage();
        String mode = request.getMode();

        int loc = astAnalysisService.countLines(code);
        int complexity = astAnalysisService.estimateComplexity(code);
        int depth = astAnalysisService.estimateNestingDepth(code);

        String summary = mlAnalysisService.summarize(code, language, mode);
        double explainConf = mlAnalysisService.estimateExplainConfidence(code);
        double bugConf = mlAnalysisService.estimateBugConfidence(code);

        List<String> trace = new ArrayList<>(mlAnalysisService.buildBaseTrace(language));
        trace.add("Structural metrics → LOC: " + loc + ", complexity: " + complexity + ", nesting depth: " + depth + ".");
        trace.add("Explanation confidence estimated at " + String.format("%.2f", explainConf) + ".");

        if (historyEnabled) {
            AnalysisRecord record = new AnalysisRecord(language, code, summary, explainConf, bugConf);
            repository.save(record);
        }

        return new ExplainResponse(summary, trace, explainConf);
    }

    public BugReportResponse bugs(BugReportRequest request) {
        String code = request.getCode();
        String language = request.getLanguage();
        List<BugDto> bugs = ruleEngineService.detectBugs(code, language);
        double bugConf = mlAnalysisService.estimateBugConfidence(code);
        return new BugReportResponse(bugs, bugConf);
    }

    public ScoreResponse score(ExplainRequest request) {
        String code = request.getCode();
        int loc = astAnalysisService.countLines(code);
        int complexity = astAnalysisService.estimateComplexity(code);
        int depth = astAnalysisService.estimateNestingDepth(code);

        double explainConf = mlAnalysisService.estimateExplainConfidence(code);
        double bugConf = mlAnalysisService.estimateBugConfidence(code);
        double read = scoringService.computeReadability(loc, complexity, depth);

        return new ScoreResponse(loc, complexity, depth, read, explainConf, bugConf);
    }

    public List<AnalysisRecord> history() {
        List<AnalysisRecord> all = repository.findAll();
        all.sort(Comparator.comparing(AnalysisRecord::getCreatedAt).reversed());
        if (all.size() > 25) {
            return all.subList(0, 25);
        }
        return all;
    }
}
