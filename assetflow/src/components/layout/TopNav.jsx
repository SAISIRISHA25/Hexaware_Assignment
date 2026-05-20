// ─── Top Navigation Bar ───────────────────────────────────────────────────────
import { useState } from "react";
import { Avatar } from "../ui/Avatar";

const today = new Date().toLocaleDateString("en-US", {
  weekday: "long",
  year: "numeric",
  month: "long",
  day: "numeric",
});

export function TopNav({ user, pageTitle, pageSubtitle, isAdmin, onLogout }) {
  const [showUser, setShowUser] = useState(false);

  return (
    <header className="h-16 bg-white border-b border-slate-100 flex items-center px-6 gap-4 sticky top-0 z-20">
      <div className="flex-1">
        <h1 className="text-lg font-bold text-slate-900 leading-none">{pageTitle}</h1>
        {pageSubtitle && (
          <p className="text-xs text-slate-500 mt-0.5">
            {pageSubtitle} • {today}
          </p>
        )}
      </div>

      <div className="relative">
        <button
          onClick={() => setShowUser((s) => !s)}
          className="flex items-center gap-2.5 hover:bg-slate-50 rounded-xl px-2 py-1.5 transition-all"
        >
          <Avatar name={user?.fullName} size="sm" />
          <div className="text-left">
            <p className="text-sm font-semibold text-slate-800 leading-none">{user?.fullName || "User"}</p>
            <p className="text-xs text-slate-500">{isAdmin ? "Administrator" : "Employee"}</p>
          </div>
          <span className="text-slate-400 text-xs">▾</span>
        </button>

        {showUser && (
          <div className="absolute right-0 top-12 w-56 bg-white border border-slate-200 rounded-xl shadow-2xl z-50 overflow-hidden">
            <div className="px-4 py-3 border-b border-slate-100">
              <p className="font-semibold text-slate-800 text-sm">{user?.fullName}</p>
              <p className="text-xs text-slate-500">{user?.email}</p>
            </div>
            <div className="p-1">
              <div className="border-t border-slate-100 my-1" />
              <button
                onClick={onLogout}
                className="w-full text-left px-3 py-2 text-sm text-red-600 hover:bg-red-50 rounded-lg flex items-center gap-2"
              >
                🚪 Sign Out
              </button>
            </div>
          </div>
        )}
      </div>
    </header>
  );
}
