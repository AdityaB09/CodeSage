import React from "react";

type Props = {
  mode: "developer" | "student";
  setMode: (m: "developer" | "student") => void;
};

const ModeToggle: React.FC<Props> = ({ mode, setMode }) => {
  return (
    <div className="flex items-center text-xs border border-borderSoft rounded-full overflow-hidden bg-panelSoft">
      <button
        onClick={() => setMode("developer")}
        className={`px-3 py-1 ${
          mode === "developer" ? "bg-accent text-slate-900 font-semibold" : "text-textMuted"
        }`}
      >
        Dev
      </button>
      <button
        onClick={() => setMode("student")}
        className={`px-3 py-1 ${
          mode === "student" ? "bg-accent text-slate-900 font-semibold" : "text-textMuted"
        }`}
      >
        Student
      </button>
    </div>
  );
};

export default ModeToggle;
