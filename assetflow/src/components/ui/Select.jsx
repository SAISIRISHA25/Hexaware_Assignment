// ─── Select Component ─────────────────────────────────────────────────────────

export function Select({ label, required, children, ...props }) {
  return (
    <div>
      {label && (
        <label className="block text-sm font-medium text-slate-700 mb-1.5">
          {label}
          {required && <span className="text-red-500 ml-0.5">*</span>}
        </label>
      )}
      <select
        className="w-full border border-slate-200 rounded-lg px-3.5 py-2.5 text-sm outline-none focus:border-blue-400 focus:ring-2 focus:ring-blue-50 bg-slate-50 focus:bg-white appearance-none"
        {...props}
      >
        {children}
      </select>
    </div>
  );
}
