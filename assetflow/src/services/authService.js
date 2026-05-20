// ─── Auth Service ─────────────────────────────────────────────────────────────
import { api } from "./api";

export async function loginUser(email, password) {
  const data = await api("POST", "/auth/login", { email, password });
  const token = data.token;
  const profile = await api("GET", "/users/me", null, token);
  return { token, user: profile };
}

export async function registerUser(formData) {
  return api("POST", "/auth/register", {
    fullName: formData.fullName,
    email: formData.email,
    password: formData.password,
    phone: formData.phone || undefined,
    department: formData.department || undefined,
    designation: formData.designation || undefined,
  });
}

export async function logoutUser(token) {
  try {
    await api("POST", "/auth/logout", null, token);
  } catch {
    // ignore logout errors
  }
}
