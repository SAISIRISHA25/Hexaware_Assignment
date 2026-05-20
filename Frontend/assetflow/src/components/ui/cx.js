// ─── cx helper ────────────────────────────────────────────────────────────────
// Joins class names, filtering out falsy values.
// Usage: cx("base", isActive && "active", className)

export function cx(...classes) {
  return classes.filter(Boolean).join(" ");
}
