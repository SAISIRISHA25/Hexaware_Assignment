// ─── Sidebar Component ────────────────────────────────────────────────────────
import { cx } from "../ui/cx";
import { Avatar } from "../ui/Avatar";
import { ADMIN_NAV, EMP_NAV } from "../../constants/navigation";

export function Sidebar({ activePage, onNav, user, isAdmin, onLogout }) {
  const navItems = isAdmin ? ADMIN_NAV : EMP_NAV;

  return (
    <aside className="w-[210px] bg-slate-900 min-h-screen flex flex-col fixed left-0 top-0 z-30">
      {/* Logo */}
      <div className="px-5 py-5 border-b border-slate-700/50">
        <div className="flex items-center gap-2.5">
          <div className="w-8 h-8 bg-blue-600 rounded-lg flex items-center justify-center">
            <svg className="w-4 h-4 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2}
                d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
            </svg>
          </div>
          <div>
            <p className="text-white font-bold text-sm leading-none">AssetFlow</p>
            <p className="text-slate-400 text-xs">Management System</p>
          </div>
        </div>
      </div>

      {/* Portal label */}
      <div className="px-4 pt-4 pb-1">
        <p className="text-xs text-slate-400 font-medium uppercase tracking-wider">
          {isAdmin ? "Administrator" : "Employee"} Portal
        </p>
      </div>

      {/* Nav items */}
      <nav className="flex-1 px-3 py-1 overflow-y-auto">
        {navItems.map((item) => {
          const active = activePage === item.id;
          return (
            <button
              key={item.id}
              onClick={() => onNav(item.id)}
              className={cx(
                "w-full flex items-center gap-2.5 px-3 py-2.5 rounded-lg text-sm font-medium transition-all mb-0.5",
                active
                  ? "bg-blue-600 text-white"
                  : "text-slate-400 hover:text-white hover:bg-slate-800"
              )}
            >
              <span className="text-base">{item.icon}</span>
              <span>{item.label}</span>
            </button>
          );
        })}
      </nav>

      {/* User footer */}
      <div className="px-3 py-4 border-t border-slate-700/50">
        <div className="flex items-center gap-2.5 px-3 py-2">
          <Avatar name={user?.fullName} size="sm" />
          <div className="flex-1 min-w-0">
            <p className="text-white text-xs font-medium truncate">{user?.fullName}</p>
            <p className="text-slate-500 text-xs truncate">{user?.email}</p>
          </div>
          <button onClick={onLogout} className="text-slate-500 hover:text-white" title="Logout">
            →
          </button>
        </div>
      </div>
    </aside>
  );
}
