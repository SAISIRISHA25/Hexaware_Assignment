// ─── Return Request Service ───────────────────────────────────────────────────
import { api } from "./api";

export const getAllReturnRequests = (token) =>
  api("GET", "/return-requests", null, token);

export const getReturnRequestsByEmployee = (userId, token) =>
  api("GET", `/return-requests/employee/${userId}`, null, token);

export const createReturnRequest = (payload, token) =>
  api("POST", "/return-requests", payload, token);

export const approveReturnRequest = (requestId, token) =>
  api("PUT", `/return-requests/${requestId}/approve`, null, token);

export const rejectReturnRequest = (requestId, token) =>
  api("PUT", `/return-requests/${requestId}/reject`, null, token);
