// ─── Auth Context ─────────────────────────────────────────────────────────────
// Provides token + user state to the entire app.
// Wrap the root of the app with <AuthProvider>.

import { createContext, useContext, useState } from "react";
import { logoutUser } from "../services/authService";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [authState, setAuthState] = useState(() => {
    try {
      const token = localStorage.getItem("af_token");
      const user  = JSON.parse(localStorage.getItem("af_user") || "null");
      return { token, user };
    } catch {
      return { token: null, user: null };
    }
  });

  function login(token, user) {
    localStorage.setItem("af_token", token);
    localStorage.setItem("af_user", JSON.stringify(user));
    setAuthState({ token, user });
  }

  async function logout() {
    await logoutUser(authState.token);
    localStorage.removeItem("af_token");
    localStorage.removeItem("af_user");
    setAuthState({ token: null, user: null });
  }

  const isAdmin = authState.user?.role === "ROLE_ADMIN";

  return (
    <AuthContext.Provider value={{ ...authState, login, logout, isAdmin }}>
      {children}
    </AuthContext.Provider>
  );
}

// Convenience hook
export function useAuth() {
  return useContext(AuthContext);
}
