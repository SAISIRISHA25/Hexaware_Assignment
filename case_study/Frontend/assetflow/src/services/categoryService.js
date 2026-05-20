// ─── Category Service ─────────────────────────────────────────────────────────
import { api } from "./api";

export const getAllCategories = (token) =>
  api("GET", "/categories", null, token);

export const createCategory = (payload, token) =>
  api("POST", "/categories", payload, token);

export const updateCategory = (categoryId, payload, token) =>
  api("PUT", `/categories/${categoryId}`, payload, token);

export const deleteCategory = (categoryId, token) =>
  api("DELETE", `/categories/${categoryId}`, null, token);
