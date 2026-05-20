// ─── API Service Layer ────────────────────────────────────────────────────────
// All HTTP calls to the backend live here.
// Usage: import { api } from '../services/api'

const BASE = "http://localhost:8080/api/v1";

function getHeaders(token) {
  return {
    "Content-Type": "application/json",
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
  };
}


export async function api(method, path, body, token) {
  const res = await fetch(`${BASE}${path}`, {
    method,
    headers: getHeaders(token),
    body: body ? JSON.stringify(body) : undefined,
  });
  const data = await res.json().catch(() => ({}));
  if (!res.ok) throw new Error(data?.message || "Request failed");
  return data?.data ?? data;
}
