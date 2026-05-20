// ─── Register Page ────────────────────────────────────────────────────────────
import { useState } from "react";
import { registerUser } from "../../services/authService";

export function RegisterPage({ onBack, onRegistered }) {
  const [form, setForm] = useState({
    fullName: "", email: "", password: "", phone: "", department: "", designation: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError]     = useState("");
  const [showPw, setShowPw]   = useState(false);

  const doRegister = async () => {
    if (!form.fullName || !form.email || !form.password) {
      setError("Full name, email and password are required");
      return;
    }
    setLoading(true);
    setError("");
    try {
      await registerUser(form);
      onRegistered();
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex">
      {/* Left panel */}
      <div className="w-1/2 bg-gradient-to-br from-slate-900 via-blue-950 to-slate-900 flex flex-col p-12 relative overflow-hidden">
        <div
          className="absolute inset-0 opacity-10"
          style={{
            backgroundImage:
              "radial-gradient(circle at 20% 50%, #3B82F6 0%, transparent 50%), radial-gradient(circle at 80% 20%, #6366F1 0%, transparent 50%)",
          }}
        />
        <div className="relative z-10">
          <div className="flex items-center gap-3 mb-16">
            <div className="w-10 h-10 bg-blue-600 rounded-xl flex items-center justify-center">
              <svg className="w-6 h-6 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2}
                  d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
              </svg>
            </div>
            <div>
              <p className="text-white font-bold text-lg leading-none">AssetFlow</p>
              <p className="text-blue-300 text-xs">Enterprise Management System</p>
            </div>
          </div>
          <h1 className="text-4xl font-black text-white leading-tight mb-3">
            Join<br />AssetFlow<br />
            <span className="text-blue-400">Today</span>
          </h1>
          <p className="text-slate-400 text-sm mb-10 max-w-sm">
            Create your employee account to start managing assets, raise service requests, and track your allocated equipment.
          </p>
          <div className="bg-white/5 border border-white/10 rounded-xl p-5">
            <p className="text-white font-semibold text-sm mb-2">Password Requirements</p>
            {["At least 8 characters", "One uppercase letter (A–Z)", "One digit (0–9)", "One special character (!@#$%...)"].map((r) => (
              <div key={r} className="flex items-center gap-2 mt-1">
                <div className="w-1.5 h-1.5 bg-blue-400 rounded-full" />
                <p className="text-slate-400 text-xs">{r}</p>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Right panel */}
      <div className="w-1/2 bg-white flex items-center justify-center p-12">
        <div className="w-full max-w-md">
          <button onClick={onBack} className="flex items-center gap-2 text-slate-500 text-sm mb-6 hover:text-blue-600">
            ← Back to Sign In
          </button>
          <h2 className="text-3xl font-black text-slate-900 mb-1">Create Account</h2>
          <p className="text-slate-500 text-sm mb-8">Register as an employee to get started</p>

          <div className="flex flex-col gap-4">
            <div className="grid grid-cols-2 gap-4">
              <div className="col-span-2">
                <label className="block text-sm font-medium text-slate-700 mb-1.5">Full Name <span className="text-red-500">*</span></label>
                <input
                  value={form.fullName}
                  onChange={(e) => setForm((f) => ({ ...f, fullName: e.target.value }))}
                  type="text" placeholder="John Smith"
                  className="w-full border border-slate-200 rounded-lg px-4 py-3 text-sm outline-none focus:border-blue-400 focus:ring-2 focus:ring-blue-50 bg-slate-50 focus:bg-white"
                />
              </div>
              <div className="col-span-2">
                <label className="block text-sm font-medium text-slate-700 mb-1.5">Email Address <span className="text-red-500">*</span></label>
                <input
                  value={form.email}
                  onChange={(e) => setForm((f) => ({ ...f, email: e.target.value }))}
                  type="email" placeholder="you@hexaware.com"
                  className="w-full border border-slate-200 rounded-lg px-4 py-3 text-sm outline-none focus:border-blue-400 focus:ring-2 focus:ring-blue-50 bg-slate-50 focus:bg-white"
                />
              </div>
              <div className="col-span-2">
                <label className="block text-sm font-medium text-slate-700 mb-1.5">Password <span className="text-red-500">*</span></label>
                <div className="relative">
                  <input
                    value={form.password}
                    onChange={(e) => setForm((f) => ({ ...f, password: e.target.value }))}
                    type={showPw ? "text" : "password"}
                    placeholder="Min. 8 chars, 1 uppercase, 1 digit, 1 special"
                    className="w-full border border-slate-200 rounded-lg px-4 py-3 text-sm outline-none focus:border-blue-400 bg-slate-50 focus:bg-white pr-10"
                  />
                  <button onClick={() => setShowPw((s) => !s)} className="absolute right-3 top-1/2 -translate-y-1/2 text-slate-400">
                    {showPw ? "🙈" : "👁"}
                  </button>
                </div>
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1.5">Phone</label>
                <input
                  value={form.phone}
                  onChange={(e) => setForm((f) => ({ ...f, phone: e.target.value }))}
                  type="tel" placeholder="10-digit number"
                  className="w-full border border-slate-200 rounded-lg px-4 py-3 text-sm outline-none focus:border-blue-400 bg-slate-50 focus:bg-white"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1.5">Department</label>
                <input
                  value={form.department}
                  onChange={(e) => setForm((f) => ({ ...f, department: e.target.value }))}
                  type="text" placeholder="e.g. IT, Finance"
                  className="w-full border border-slate-200 rounded-lg px-4 py-3 text-sm outline-none focus:border-blue-400 bg-slate-50 focus:bg-white"
                />
              </div>
              <div className="col-span-2">
                <label className="block text-sm font-medium text-slate-700 mb-1.5">Designation</label>
                <input
                  value={form.designation}
                  onChange={(e) => setForm((f) => ({ ...f, designation: e.target.value }))}
                  type="text" placeholder="e.g. Software Engineer"
                  className="w-full border border-slate-200 rounded-lg px-4 py-3 text-sm outline-none focus:border-blue-400 bg-slate-50 focus:bg-white"
                />
              </div>
            </div>

            {error && (
              <p className="text-red-500 text-sm bg-red-50 border border-red-100 rounded-lg px-3 py-2">{error}</p>
            )}
            <button
              onClick={doRegister}
              disabled={loading}
              className="w-full bg-blue-600 hover:bg-blue-700 text-white rounded-lg py-3 text-sm font-semibold flex items-center justify-center gap-2 shadow-md shadow-blue-200 disabled:opacity-50"
            >
              {loading ? "Creating account…" : "Create Account →"}
            </button>
          </div>

          <p className="text-center text-sm text-slate-500 mt-6">
            Already have an account?{" "}
            <button onClick={onBack} className="text-blue-600 font-semibold hover:underline">Sign in</button>
          </p>
        </div>
      </div>
    </div>
  );
}
