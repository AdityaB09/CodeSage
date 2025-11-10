package com.codesage.service;

import org.springframework.stereotype.Service;

@Service
public class ScoringService {

    public double computeReadability(int loc, int complexity, int depth) {
        if (loc == 0) return 0.5;
        double base = 1.0 - Math.min(1.0, (complexity + depth) / (double) (loc + 5));
        return Math.max(0.1, Math.min(base, 1.0));
    }
}
