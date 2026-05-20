// ─── Error Banner Component ───────────────────────────────────────────────────

export function ErrBanner({ msg, onClose }) {
  if (!msg) return null;
  return (
    <div className="flex items-center gap-3 bg-red-50 border border-red-200 rounded-xl px-4 py-3 text-sm text-red-700">
      <span>⚠</span>
      <span className="flex-1">{msg}</span>
      {onClose && (
        <button onClick={onClose} className="text-red-400 hover:text-red-600">×</button>
      )}
    </div>
  );
}
