// ─── Audit Service ────────────────────────────────────────────────────────────
import { api } from "./api";

export const getAllAuditRequests = (token) =>
  api("GET", "/audit-requests", null, token);

export const getAuditRequestsByEmployee = (userId, token) =>
  api("GET", `/audit-requests/employee/${userId}`, null, token);

export const createAuditRequest = (payload, token) =>
  api("POST", "/audit-requests", payload, token);

export const submitAuditResponse = (payload, token) =>
  api("POST", "/audit-responses", payload, token);
