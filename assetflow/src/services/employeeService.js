// ─── Employee Service ─────────────────────────────────────────────────────────
import { api } from "./api";

export const getAllUsers = (token) => api("GET", "/users", null, token);

export const deleteUser = (userId, token) =>
  api("DELETE", `/users/${userId}`, null, token);
