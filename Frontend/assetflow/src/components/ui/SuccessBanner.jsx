// ─── Success Banner Component ─────────────────────────────────────────────────

export function SuccessBanner({ msg }) {
  if (!msg) return null;
  return (
    <div className="bg-emerald-50 border border-emerald-200 rounded-xl p-4 text-sm text-emerald-700">
      ✓ {msg}
    </div>
  );
}
