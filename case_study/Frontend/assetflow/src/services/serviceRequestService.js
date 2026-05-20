// ─── Service Request Service ──────────────────────────────────────────────────
import { api } from "./api";

export const getAllServiceRequests = (token) =>
  api("GET", "/service-requests", null, token);

export const getServiceRequestsByEmployee = (userId, token) =>
  api("GET", `/service-requests/employee/${userId}`, null, token);

export const createServiceRequest = (payload, token) =>
  api("POST", "/service-requests", payload, token);

export const updateServiceRequestStatus = (requestId, status, token) =>
  api("PUT", `/service-requests/${requestId}/status?status=${status}`, null, token);
