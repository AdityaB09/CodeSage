import React from "react";
import { Bug } from "../App";

type Props = {
  bugs: Bug[];
  bugConfidence: number;
  onBugSelect: (bug: Bug | null) => void;
  selectedBug: Bug | null;
};

const severityColor = (severity: string) => {
  switch (severity) {
    case "error":
      return "bg-red-500/10 text-red-300 border-red-500/40";
    case "warning":
      return "bg-yellow-500/10 text-yellow-200 border-yellow-500/30";
    default:
      return "bg-sky-500/10 text-sky-200 border-sky-500/30";
  }
};

const BugsPanel: React.FC<Props> = ({ bugs, bugConfidence, onBugSelect, selectedBug }) => {
  return (
    <div className="bg-panel border border-borderSoft rounded-2xl p-3 flex flex-col gap-3 h-full">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-sm font-semibold uppercase tracking-wide text-textMuted">
            Bugs & smells
          </h2>
          <p className="text-xs text-textMuted">
            ML + rules suggestions with severity.
          </p>
        </div>
        <div className="text-right text-xs">
          <p className="text-textMuted">Bug detection confidence</p>
          <p className="font-semibold">{(bugConfidence * 100).toFixed(0)}%</p>
        </div>
      </div>
      <div className="space-y-2 overflow-y-auto max-h-64 pr-1">
        {bugs.length === 0 ? (
          <p className="text-xs text-textMuted">
            No issues detected yet. Run analysis or tweak your code.
          </p>
        ) : (
          bugs.map((bug) => (
            <button
              key={bug.id}
              onClick={() => onBugSelect(bug)}
              className={`w-full text-left rounded-xl border px-3 py-2 text-xs ${
                severityColor(bug.severity)
              } ${
                selectedBug?.id === bug.id
                  ? "ring-1 ring-accentSoft shadow"
                  : "hover:bg-slate-900/40"
              }`}
            >
              <div className="flex items-center justify-between mb-1">
                <span className="font-semibold capitalize">
                  {bug.type.replace(/_/g, " ")}
                </span>
                <span className="text-[10px] uppercase tracking-wide">
                  Line {bug.line > 0 ? bug.line : "-"}
                </span>
              </div>
              <p className="mb-1">{bug.message}</p>
              {bug.suggestion && (
                <p className="text-[11px] text-textMuted">
                  Suggestion: {bug.suggestion}
                </p>
              )}
            </button>
          ))
        )}
      </div>
    </div>
  );
};

export default BugsPanel;
