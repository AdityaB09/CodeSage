import React, { useEffect, useState } from "react";
import axios from "axios";
import CodeEditor from "./components/CodeEditor";
import ExplanationPanel from "./components/ExplanationPanel";
import BugsPanel from "./components/BugsPanel";
import MetricsPanel from "./components/MetricsPanel";
import HistoryPanel from "./components/HistoryPanel";
import ModeToggle from "./components/ModeToggle";

const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

export type Bug = {
  id: string;
  line: number;
  severity: string;
  type: string;
  message: string;
  suggestion: string;
  fixedSnippet?: string | null;
};

export type AnalysisHistoryItem = {
  id: number;
  language: string;
  code: string;
  summary: string;
  explainConfidence: number;
  bugConfidence: number;
  createdAt: string;
};

const defaultCode = `public void bubbleSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n; i++) {
        for (int j = 1; j < n; j++) {
            if (arr[j] < arr[j - 1]) {
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
            }
        }
    }
}`;

const App: React.FC = () => {
  const [code, setCode] = useState<string>(defaultCode);
  const [language, setLanguage] = useState<string>("java");
  const [mode, setMode] = useState<"developer" | "student">("developer");
  const [summary, setSummary] = useState<string>("");
  const [trace, setTrace] = useState<string[]>([]);
  const [explainConfidence, setExplainConfidence] = useState<number>(0);
  const [bugs, setBugs] = useState<Bug[]>([]);
  const [bugConfidence, setBugConfidence] = useState<number>(0);
  const [loc, setLoc] = useState<number>(0);
  const [complexity, setComplexity] = useState<number>(0);
  const [nestingDepth, setNestingDepth] = useState<number>(0);
  const [readability, setReadability] = useState<number>(0);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [history, setHistory] = useState<AnalysisHistoryItem[]>([]);
  const [selectedBug, setSelectedBug] = useState<Bug | null>(null);
  const [darkMode, setDarkMode] = useState<boolean>(true);

  const runAnalysis = async () => {
    setIsLoading(true);
    try {
      const explainReq = { code, language, mode };
      const [explainRes, bugsRes, scoreRes] = await Promise.all([
        axios.post(`${API_URL}/explain`, explainReq),
        axios.post(`${API_URL}/bugs`, { code, language }),
        axios.post(`${API_URL}/score`, explainReq)
      ]);

      setSummary(explainRes.data.summary);
      setTrace(explainRes.data.trace || []);
      setExplainConfidence(explainRes.data.confidence || 0);

      setBugs(bugsRes.data.bugs || []);
      setBugConfidence(bugsRes.data.overallConfidence || 0);

      setLoc(scoreRes.data.loc);
      setComplexity(scoreRes.data.complexity);
      setNestingDepth(scoreRes.data.nestingDepth);
      setReadability(scoreRes.data.readabilityScore);
      setSelectedBug(null);

      // refresh history
      fetchHistory();
    } catch (err) {
      console.error("Analysis error", err);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchHistory = async () => {
    try {
      const res = await axios.get(`${API_URL}/history`);
      setHistory(res.data || []);
    } catch (err) {
      console.error("History error", err);
    }
  };

  useEffect(() => {
    fetchHistory();
  }, []);

  useEffect(() => {
    if (darkMode) {
      document.documentElement.classList.add("dark");
    } else {
      document.documentElement.classList.remove("dark");
    }
  }, [darkMode]);

  const handleHistorySelect = (item: AnalysisHistoryItem) => {
    setCode(item.code);
    setLanguage(item.language || "java");
    setSummary(item.summary);
    setExplainConfidence(item.explainConfidence);
    setBugConfidence(item.bugConfidence);
  };

  return (
    <div className={`min-h-screen ${darkMode ? "bg-bg text-white" : "bg-slate-100 text-slate-900"}`}>
      <div className="max-w-7xl mx-auto px-4 py-4 space-y-4">
        <header className="flex items-center justify-between gap-4">
          <div>
            <h1 className="text-2xl font-bold tracking-tight flex items-center gap-2">
              <span className="inline-flex h-7 w-7 rounded-full bg-accentSoft items-center justify-center text-xs font-bold">
                CS
              </span>
              CodeSage Studio
            </h1>
            <p className="text-sm text-textMuted mt-1">
              Explainable AI for code understanding – explanations, bugs, and reasoning traces.
            </p>
          </div>
          <div className="flex items-center gap-3">
            <ModeToggle mode={mode} setMode={setMode} />
            <button
              onClick={() => setDarkMode((d) => !d)}
              className="px-3 py-1.5 text-xs rounded-full border border-borderSoft bg-panelSoft hover:bg-slate-800 transition"
            >
              {darkMode ? "Light" : "Dark"} mode
            </button>
            <button
              onClick={runAnalysis}
              disabled={isLoading}
              className="px-4 py-2 text-sm font-semibold rounded-xl bg-accent hover:bg-accentSoft text-slate-900 shadow disabled:opacity-60"
            >
              {isLoading ? "Analyzing…" : "Run Analysis"}
            </button>
          </div>
        </header>

        <main className="grid grid-cols-12 gap-4">
          {/* Left: Editor + language selector */}
          <section className="col-span-12 md:col-span-5 flex flex-col gap-3">
            <div className={`${darkMode ? "bg-panel" : "bg-white"} border border-borderSoft rounded-2xl p-3 h-full flex flex-col`}>
              <div className="flex items-center justify-between mb-2">
                <div>
                  <h2 className="text-sm font-semibold uppercase tracking-wide text-textMuted">
                    Code
                  </h2>
                  <p className="text-xs text-textMuted">
                    Paste your snippet and select language.
                  </p>
                </div>
                <select
                  value={language}
                  onChange={(e) => setLanguage(e.target.value)}
                  className="text-xs rounded-lg border border-borderSoft bg-panelSoft px-2 py-1"
                >
                  <option value="java">Java</option>
                  <option value="python">Python</option>
                  <option value="javascript">JavaScript</option>
                </select>
              </div>
              <div className="flex-1 min-h-[260px] rounded-xl overflow-hidden border border-borderSoft">
                <CodeEditor
                  code={code}
                  language={language}
                  onChange={(val) => setCode(val ?? "")}
                  bugs={bugs}
                  selectedBug={selectedBug}
                />
              </div>
            </div>
          </section>

          {/* Middle: Explanation + Bugs */}
          <section className="col-span-12 md:col-span-4 flex flex-col gap-3">
            <ExplanationPanel summary={summary} trace={trace} explainConfidence={explainConfidence} />
            <BugsPanel
              bugs={bugs}
              bugConfidence={bugConfidence}
              onBugSelect={setSelectedBug}
              selectedBug={selectedBug}
            />
          </section>

          {/* Right: Metrics + History */}
          <section className="col-span-12 md:col-span-3 flex flex-col gap-3">
            <MetricsPanel
              loc={loc}
              complexity={complexity}
              nestingDepth={nestingDepth}
              readability={readability}
              explainConfidence={explainConfidence}
              bugConfidence={bugConfidence}
            />
            <HistoryPanel items={history} onSelect={handleHistorySelect} />
          </section>
        </main>
      </div>
    </div>
  );
};

export default App;
