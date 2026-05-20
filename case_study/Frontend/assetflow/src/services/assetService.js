// ─── Asset Service ────────────────────────────────────────────────────────────
import { api } from "./api";

export const getAllAssets = (token) =>
  api("GET", "/assets?page=0&size=200", null, token);

export const createAsset = (payload, token) =>
  api("POST", "/assets", payload, token);

export const updateAsset = (assetId, payload, token) =>
  api("PUT", `/assets/${assetId}`, payload, token);

export const deleteAsset = (assetId, token) =>
  api("DELETE", `/assets/${assetId}`, null, token);
