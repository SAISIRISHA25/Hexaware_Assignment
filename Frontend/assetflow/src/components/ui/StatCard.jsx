// ─── Stat Card Component ──────────────────────────────────────────────────────
// Reusable numeric stat card used across dashboards.
import { cx } from "./cx";

export function StatCard({ label, value, icon, color = "text-blue-600", bg = "bg-white border-slate-100", sub }) {
  return (
    <div className={cx("rounded-xl border shadow-sm p-5", bg)}>
      {icon && (
        <div className="flex items-center justify-between mb-3">
          <p className="text-sm text-slate-500">{label}</p>
          <span className="text-2xl">{icon}</span>
        </div>
      )}
      {!icon && <p className="text-sm text-slate-500 mb-2">{label}</p>}
      <p className={cx("text-3xl font-black", color)}>{value}</p>
      {sub && <p className="text-xs text-slate-400 mt-1">{sub}</p>}
    </div>
  );
}
