// ─── Status badge style map ───────────────────────────────────────────────────

export const STATUS_STYLES = {
  PENDING:       "bg-amber-100 text-amber-700 border border-amber-200",
  APPROVED:      "bg-emerald-100 text-emerald-700 border border-emerald-200",
  REJECTED:      "bg-red-100 text-red-700 border border-red-200",
  ACTIVE:        "bg-emerald-100 text-emerald-700 border border-emerald-200",
  CLOSED:        "bg-slate-100 text-slate-600 border border-slate-200",
  AVAILABLE:     "bg-emerald-100 text-emerald-700 border border-emerald-200",
  ALLOCATED:     "bg-blue-100 text-blue-700 border border-blue-200",
  UNDER_SERVICE: "bg-orange-100 text-orange-700 border border-orange-200",
  IN_PROGRESS:   "bg-blue-100 text-blue-700 border border-blue-200",
  COMPLETED:     "bg-emerald-100 text-emerald-700 border border-emerald-200",
  VERIFIED:      "bg-emerald-100 text-emerald-700 border border-emerald-200",
  NOT_VERIFIED:  "bg-red-100 text-red-700 border border-red-200",
  FULFILLED:     "bg-purple-100 text-purple-700 border border-purple-200",
  DISCREPANCY:   "bg-red-100 text-red-700 border border-red-200",
  MISSING:       "bg-red-100 text-red-700 border border-red-200",
  HIGH:          "bg-amber-100 text-amber-700 border border-amber-200",
  MEDIUM:        "bg-sky-100 text-sky-700 border border-sky-200",
  LOW:           "bg-slate-100 text-slate-600 border border-slate-200",
  OPEN:          "bg-emerald-100 text-emerald-700 border border-emerald-200",
  RESOLVED:      "bg-emerald-100 text-emerald-700 border border-emerald-200",
  EXCELLENT:     "bg-emerald-100 text-emerald-700 border border-emerald-200",
  GOOD:          "bg-sky-100 text-sky-700 border border-sky-200",
  FAIR:          "bg-amber-100 text-amber-700 border border-amber-200",
  ROLE_ADMIN:    "bg-purple-100 text-purple-700 border border-purple-200",
  ROLE_EMPLOYEE: "bg-blue-100 text-blue-700 border border-blue-200",
  ACTIVE_STATUS: "bg-emerald-100 text-emerald-700 border border-emerald-200",
  INACTIVE:      "bg-slate-100 text-slate-600 border border-slate-200",
};

export const getStatusStyle = (status) =>
  STATUS_STYLES[status?.toUpperCase()] || "bg-slate-100 text-slate-600";
