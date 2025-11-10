import React from "react";

type Props = {
  summary: string;
  trace: string[];
  explainConfidence: number;
};

const ExplanationPanel: React.FC<Props> = ({ summary, trace, explainConfidence }) => {
  return (
    <div className="bg-panel border border-borderSoft rounded-2xl p-3 flex flex-col gap-3 h-full">
      <div>
        <h2 className="text-sm font-semibold uppercase tracking-wide text-textMuted">
          Explanation
        </h2>
        <p className="text-xs text-textMuted">
          Natural language summary and reasoning trace.
        </p>
      </div>
      <div className="bg-slate-900/40 rounded-xl p-3 text-sm min-h-[70px]">
        {summary ? (
          <p>{summary}</p>
        ) : (
          <p className="text-textMuted">
            Run analysis to see what CodeSage thinks this code does.
          </p>
        )}
      </div>
      <div className="space-y-2 overflow-y-auto max-h-52 pr-1">
        <h3 className="text-xs font-semibold text-textMuted uppercase tracking-wide">
          Reasoning trace
        </h3>
        {trace && trace.length > 0 ? (
          <ol className="space-y-1 text-xs">
            {trace.map((step, idx) => (
              <li key={idx} className="flex gap-2 items-start">
                <span className="mt-0.5 h-2 w-2 rounded-full bg-accentSoft flex-shrink-0" />
                <span>{step}</span>
              </li>
            ))}
          </ol>
        ) : (
          <p className="text-xs text-textMuted">No trace yet.</p>
        )}
      </div>
      <div className="mt-auto pt-2 border-t border-borderSoft flex items-center justify-between text-xs">
        <span className="text-textMuted">Explanation confidence</span>
        <span className="font-semibold">{(explainConfidence * 100).toFixed(0)}%</span>
      </div>
    </div>
  );
};

export default ExplanationPanel;
