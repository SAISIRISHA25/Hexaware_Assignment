// ─── App Shell ────────────────────────────────────────────────────────────────
// Wraps the Sidebar + TopNav + page content for authenticated views.
import { Sidebar } from "./Sidebar";
import { TopNav } from "./TopNav";

export function AppShell({ user, isAdmin, activePage, onNav, onLogout, pageTitle, pageSubtitle, children }) {
  return (
    <div className="min-h-screen">
      <Sidebar
        activePage={activePage}
        onNav={onNav}
        user={user}
        isAdmin={isAdmin}
        onLogout={onLogout}
      />
      <div className="ml-[210px]">
        <TopNav
          user={user}
          pageTitle={pageTitle}
          pageSubtitle={pageSubtitle}
          isAdmin={isAdmin}
          onLogout={onLogout}
        />
        <div className="p-6">{children}</div>
      </div>
    </div>
  );
}
