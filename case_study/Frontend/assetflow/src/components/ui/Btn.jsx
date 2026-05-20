// ─── Button Component ─────────────────────────────────────────────────────────
import { cx } from "./cx";

const SIZES = {
  sm: "px-3 py-1.5 text-xs",
  md: "px-4 py-2.5 text-sm",
  lg: "px-6 py-3 text-sm",
};

const VARIANTS = {
  primary:   "bg-blue-600 hover:bg-blue-700 text-white shadow-sm",
  danger:    "bg-red-500 hover:bg-red-600 text-white shadow-sm",
  success:   "bg-emerald-600 hover:bg-emerald-700 text-white shadow-sm",
  ghost:     "bg-white hover:bg-slate-50 text-slate-700 border border-slate-200",
  outline:   "bg-white hover:bg-blue-50 text-blue-600 border border-blue-200",
  secondary: "bg-slate-100 hover:bg-slate-200 text-slate-700",
};

export function Btn({
  children,
  variant = "primary",
  size = "md",
  onClick,
  disabled,
  className,
  type = "button",
}) {
  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      className={cx(
        "inline-flex items-center justify-center gap-2 font-medium rounded-lg transition-all duration-150 disabled:opacity-50 disabled:cursor-not-allowed",
        SIZES[size],
        VARIANTS[variant],
        className
      )}
    >
      {children}
    </button>
  );
}
