// ─── Login Page ───────────────────────────────────────────────────────────────
import { useState } from "react";
import { loginUser } from "../../services/authService";
import { useAuth } from "../../context/AuthContext";

export function LoginPage({ onShowRegister, registerSuccess }) {
  const { login } = useAuth();
  const [form, setForm]       = useState({ email: "", password: "" });
  const [loading, setLoading] = useState(false);
  const [error, setError]     = useState("");
  const [showPw, setShowPw]   = useState(false);

  const doLogin = async () => {
    if (!form.email || !form.password) {
      setError("Email and password are required");
      return;
    }
    setLoading(true);
    setError("");
    try {
      const { token, user } = await loginUser(form.email, form.password);
      login(token, user);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex">
      {}
      <div
        className="w-1/2 bg-gradient-to-br from-slate-900 via-blue-950 to-slate-900 flex flex-col p-12 relative overflow-hidden"
        style={{
          backgroundImage:
            "url('https://images.unsplash.com/photo-1497366216548-37526070297c?w=900&auto=format&fit=crop&q=60')",
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      >
        <div className="absolute inset-0 bg-gradient-to-br from-slate-900/90 via-blue-950/85 to-slate-900/90" />
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
            Manage Your<br />Corporate Assets<br />
            <span className="text-blue-400">Intelligently</span>
          </h1>
          <p className="text-slate-400 text-sm mb-10 max-w-sm">
            A unified platform to track, manage, and optimize all your enterprise assets with powerful analytics and automated workflows.
          </p>
          {[
            { icon: "🖥", title: "Asset Tracking", desc: "Real-time visibility into all company assets" },
            { icon: "👥", title: "Employee Management", desc: "Centralized employee asset allocation system" },
            { icon: "📊", title: "Advanced Analytics", desc: "Comprehensive reports and insights" },
          ].map((f) => (
            <div key={f.title} className="flex items-center gap-4 bg-white/5 border border-white/10 rounded-xl px-4 py-3 mb-3">
              <div className="w-10 h-10 bg-blue-600/30 rounded-lg flex items-center justify-center text-lg">{f.icon}</div>
              <div className="flex-1">
                <p className="text-white text-sm font-semibold">{f.title}</p>
                <p className="text-slate-400 text-xs">{f.desc}</p>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Right panel */}
      <div className="w-1/2 bg-white flex items-center justify-center p-12">
        <div className="w-full max-w-md">
          {registerSuccess && (
            <div className="mb-6 bg-emerald-50 border border-emerald-200 rounded-xl px-4 py-3 text-sm text-emerald-700 font-medium">
              ✓ Account created successfully! Please sign in.
            </div>
          )}
          <h2 className="text-3xl font-black text-slate-900 mb-1">Welcome back</h2>
          <p className="text-slate-500 text-sm mb-8">Sign in to access your asset management portal</p>

          <div className="flex flex-col gap-4">
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1.5">Email Address</label>
              <input
                value={form.email}
                onChange={(e) => setForm((f) => ({ ...f, email: e.target.value }))}
                type="email"
                placeholder="you@hexaware.com"
                className="w-full border border-slate-200 rounded-lg px-4 py-3 text-sm outline-none focus:border-blue-400 focus:ring-2 focus:ring-blue-50 bg-slate-50 focus:bg-white"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1.5">Password</label>
              <div className="relative">
                <input
                  value={form.password}
                  onChange={(e) => setForm((f) => ({ ...f, password: e.target.value }))}
                  type={showPw ? "text" : "password"}
                  placeholder="••••••••"
                  onKeyDown={(e) => e.key === "Enter" && doLogin()}
                  className="w-full border border-slate-200 rounded-lg px-4 py-3 text-sm outline-none focus:border-blue-400 bg-slate-50 focus:bg-white pr-10"
                />
                <button
                  onClick={() => setShowPw((s) => !s)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-slate-400"
                >
                  {showPw ? "🙈" : "👁"}
                </button>
              </div>
            </div>
            {error && (
              <p className="text-red-500 text-sm bg-red-50 border border-red-100 rounded-lg px-3 py-2">{error}</p>
            )}
            <button
              onClick={doLogin}
              disabled={loading}
              className="w-full bg-blue-600 hover:bg-blue-700 text-white rounded-lg py-3 text-sm font-semibold flex items-center justify-center gap-2 shadow-md shadow-blue-200 disabled:opacity-50"
            >
              🔒 {loading ? "Signing in…" : "Sign In →"}
            </button>
          </div>

          <p className="text-center text-sm text-slate-500 mt-6">
            Don&apos;t have an account?{" "}
            <button onClick={onShowRegister} className="text-blue-600 font-semibold hover:underline">
              Register here
            </button>
          </p>
          <p className="text-center text-xs text-slate-400 mt-4">© 2024 Hexaware Technologies. All rights reserved.</p>
        </div>
      </div>
    </div>
  );
}
