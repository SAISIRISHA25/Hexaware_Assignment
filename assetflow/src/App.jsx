// ─── App.jsx ──────────────────────────────────────────────────────────────────
// Root component. Handles auth gating and top-level page routing.
import { useState } from "react";
import { AuthProvider, useAuth } from "./context/AuthContext";

import { LoginPage }    from "./components/auth/LoginPage";
import { RegisterPage } from "./components/auth/RegisterPage";
import { AppShell }     from "./components/layout/AppShell";

import { AdminPortal,    getAdminPageTitle }    from "./pages/admin/AdminPortal";
import { EmployeePortal, getEmployeePageTitle } from "./pages/employee/EmployeePortal";

function InnerApp() {
  const { token, user, isAdmin, logout } = useAuth();

  const [screen, setScreen]               = useState("login"); // "login" | "register"
  const [registerSuccess, setRegisterSuccess] = useState(false);

  const [activePage, setActivePage] = useState(isAdmin ? "dashboard" : "emp-dashboard");

  if (!token || !user) {
    if (screen === "register") {
      return (
        <RegisterPage
          onBack={() => setScreen("login")}
          onRegistered={() => {
            setRegisterSuccess(true);
            setScreen("login");
          }}
        />
      );
    }
    return (
      <LoginPage
        onShowRegister={() => { setRegisterSuccess(false); setScreen("register"); }}
        registerSuccess={registerSuccess}
      />
    );
  }

  // ── Authenticated ─────────────────────────────────────────────────────────
  const handleNav = (page) => setActivePage(page);

  const pageTitle = isAdmin
    ? getAdminPageTitle(activePage)
    : getEmployeePageTitle(activePage);

  return (
    <AppShell
      user={user}
      isAdmin={isAdmin}
      activePage={activePage}
      onNav={handleNav}
      onLogout={logout}
      pageTitle={pageTitle}
      pageSubtitle="Hexaware Asset Management"
    >
      {isAdmin ? (
        <AdminPortal activePage={activePage} token={token} />
      ) : (
        <EmployeePortal activePage={activePage} user={user} token={token} onNav={handleNav} />
      )}
    </AppShell>
  );
}

// ── Root App with Provider ────────────────────────────────────────────────────
export default function App() {
  return (
    <AuthProvider>
      <InnerApp />
    </AuthProvider>
  );
}
