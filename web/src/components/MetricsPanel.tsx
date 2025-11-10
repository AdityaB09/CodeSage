import React from "react";
import { Radar, RadarChart, PolarGrid, PolarAngleAxis, ResponsiveContainer } from "recharts";

type Props = {
  loc: number;
  complexity: number;
  nestingDepth: number;
  readability: number;
  explainConfidence: number;
  bugConfidence: number;
};

const MetricsPanel: React.FC<Props> = ({
  loc,
  complexity,
  nestingDepth,
  readability,
  explainConfidence,
  bugConfidence
}) => {
  const radarData = [
    { metric: "Readability", value: Math.round(readability * 100) },
    { metric: "Explain", value: Math.round(explainConfidence * 100) },
    { metric: "Bugs", value: Math.round(bugConfidence * 100) }
  ];

  return (
    <div className="bg-panel border border-borderSoft rounded-2xl p-3 flex flex-col gap-3">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-sm font-semibold uppercase tracking-wide text-textMuted">
            Metrics
          </h2>
          <p className="text-xs text-textMuted">
            Structural complexity and AI confidence.
          </p>
        </div>
      </div>

      <div className="flex justify-between text-xs my-1">
        <div>
          <p className="text-textMuted">Lines of code</p>
          <p className="font-semibold">{loc}</p>
        </div>
        <div>
          <p className="text-textMuted">Complexity</p>
          <p className="font-semibold">{complexity}</p>
        </div>
        <div>
          <p className="text-textMuted">Nesting depth</p>
          <p className="font-semibold">{nestingDepth}</p>
        </div>
      </div>

      <div className="h-40">
        <ResponsiveContainer width="100%" height="100%">
          <RadarChart data={radarData}>
            <PolarGrid />
            <PolarAngleAxis dataKey="metric" tick={{ fontSize: 10 }} />
            <Radar
              name="Score"
              dataKey="value"
              stroke="#38bdf8"
              fill="#38bdf8"
              fillOpacity={0.4}
            />
          </RadarChart>
        </ResponsiveContainer>
      </div>

      <div className="text-[11px] text-textMuted">
        Higher scores indicate more confident explanations and bug detection. Readability
        is derived from structural complexity.
      </div>
    </div>
  );
};

export default MetricsPanel;
