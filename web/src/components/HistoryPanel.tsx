import React from "react";
import { AnalysisHistoryItem } from "../App";

type Props = {
  items: AnalysisHistoryItem[];
  onSelect: (item: AnalysisHistoryItem) => void;
};

const HistoryPanel: React.FC<Props> = ({ items, onSelect }) => {
  return (
    <div className="bg-panel border border-borderSoft rounded-2xl p-3 flex flex-col gap-3">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-sm font-semibold uppercase tracking-wide text-textMuted">
            History
          </h2>
          <p className="text-xs text-textMuted">
            Recent analyses (persisted in PostgreSQL).
          </p>
        </div>
      </div>
      <div className="space-y-1 max-h-56 overflow-y-auto pr-1 text-xs">
        {items.length === 0 ? (
          <p className="text-textMuted">No history yet.</p>
        ) : (
          items.map((item) => (
            <button
              key={item.id}
              onClick={() => onSelect(item)}
              className="w-full text-left rounded-lg border border-borderSoft px-2 py-1 hover:bg-slate-900/60"
            >
              <div className="flex items-center justify-between">
                <span className="font-semibold capitalize text-[11px]">
                  {item.language || "code"}
                </span>
                <span className="text-[10px] text-textMuted">
                  {new Date(item.createdAt).toLocaleTimeString()}
                </span>
              </div>
              <p className="truncate text-textMuted">
                {item.summary || item.code.slice(0, 60)}
              </p>
            </button>
          ))
        )}
      </div>
    </div>
  );
};

export default HistoryPanel;
