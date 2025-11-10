import React, { useRef } from "react";
import Editor, { OnMount } from "@monaco-editor/react";
import { Bug } from "../App";

type Props = {
  code: string;
  language: string;
  onChange: (value: string | undefined) => void;
  bugs: Bug[];
  selectedBug: Bug | null;
};

const CodeEditor: React.FC<Props> = ({ code, language, onChange, bugs, selectedBug }) => {
  const editorRef = useRef<any>(null);

  const handleMount: OnMount = (editor) => {
    editorRef.current = editor;
    updateDecorations();
  };

  const updateDecorations = () => {
    if (!editorRef.current) return;
    const decorations = bugs.map((bug) => ({
      range: {
        startLineNumber: bug.line || 1,
        startColumn: 1,
        endLineNumber: bug.line || 1,
        endColumn: 1000
      },
      options: {
        isWholeLine: true,
        className:
          bug.id === selectedBug?.id
            ? "bg-slate-800"
            : bug.severity === "error"
            ? "bg-red-900/40"
            : bug.severity === "warning"
            ? "bg-yellow-900/30"
            : "bg-slate-800/40",
        glyphMarginClassName:
          bug.severity === "error"
            ? "codesage-glyph-error"
            : bug.severity === "warning"
            ? "codesage-glyph-warning"
            : "codesage-glyph-info"
      }
    }));
    editorRef.current.deltaDecorations([], decorations);
    if (selectedBug && selectedBug.line > 0) {
      editorRef.current.revealLineInCenter(selectedBug.line);
    }
  };

  React.useEffect(() => {
    updateDecorations();
  }, [bugs, selectedBug]);

  return (
    <div className="h-full">
      <style>
        {`
        .codesage-glyph-error {
          background-color: #ef4444;
          width: 8px;
          border-radius: 999px;
        }
        .codesage-glyph-warning {
          background-color: #eab308;
          width: 8px;
          border-radius: 999px;
        }
        .codesage-glyph-info {
          background-color: #38bdf8;
          width: 8px;
          border-radius: 999px;
        }
        `}
      </style>
      <Editor
        height="100%"
        defaultLanguage={language === "javascript" ? "javascript" : language}
        language={language === "javascript" ? "javascript" : language}
        theme="vs-dark"
        value={code}
        onChange={onChange}
        onMount={handleMount}
        options={{
          fontSize: 13,
          minimap: { enabled: false },
          glyphMargin: true,
          scrollBeyondLastLine: false,
          automaticLayout: true
        }}
      />
    </div>
  );
};

export default CodeEditor;
