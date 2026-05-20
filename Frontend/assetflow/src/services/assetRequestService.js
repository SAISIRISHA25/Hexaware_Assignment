// ─── Asset Request Service ────────────────────────────────────────────────────
import { api } from "./api";

export const getAllAssetRequests = (token) =>
  api("GET", "/asset-requests", null, token);

export const getAssetRequestsByEmployee = (userId, token) =>
  api("GET", `/asset-requests/employee/${userId}`, null, token);

export const createAssetRequest = (payload, token) =>
  api("POST", "/asset-requests", payload, token);

export const approveAssetRequest = (requestId, token) =>
  api("PUT", `/asset-requests/${requestId}/approve`, null, token);

export const rejectAssetRequest = (requestId, token) =>
  api("PUT", `/asset-requests/${requestId}/reject`, null, token);
