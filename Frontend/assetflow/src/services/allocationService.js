// ─── Allocation Service ───────────────────────────────────────────────────────
import { api } from "./api";

export const getAllAllocations = (token) =>
  api("GET", "/allocations", null, token);

export const getMyAllocations = (token) =>
  api("GET", "/allocations/me", null, token);

export const getMyActiveAllocations = (token) =>
  api("GET", "/allocations/me/active", null, token);

export const createAllocation = (payload, token) =>
  api("POST", "/allocations", payload, token);

export const closeAllocation = (allocationId, token) =>
  api("PUT", `/allocations/${allocationId}/close`, null, token);
