// ─── Request Asset Page ───────────────────────────────────────────────────────
import { useState, useEffect, useCallback } from "react";
import { getAllCategories } from "../../services/categoryService";
import { getAllAssets } from "../../services/assetService";
import { createAssetRequest, getAssetRequestsByEmployee } from "../../services/assetRequestService";
import { Spinner } from "../../components/ui/Spinner";
import { ErrBanner } from "../../components/ui/ErrBanner";
import { SuccessBanner } from "../../components/ui/SuccessBanner";
import { Badge } from "../../components/ui/Badge";
import { Modal } from "../../components/ui/Modal";
import { Select } from "../../components/ui/Select";
import { Textarea } from "../../components/ui/Textarea";
import { Btn } from "../../components/ui/Btn";
import { cx } from "../../components/ui/cx";
import { CAT_COLORS, CAT_ICONS } from "../../constants/categories";

export function RequestAsset({ user, token }) {
  const [categories, setCategories] = useState([]);
  const [assets, setAssets]         = useState([]);
  const [myRequests, setMyRequests] = useState([]);
  const [loading, setLoading]       = useState(true);
  const [error, setError]           = useState("");
  const [success, setSuccess]       = useState("");
  const [modal, setModal]           = useState(false);
  const [saving, setSaving]         = useState(false);
  const [searchCat, setSearchCat]   = useState("");
  const [form, setForm]             = useState({ categoryId: "", requestReason: "" });

  const load = useCallback(() => {
    setLoading(true);
    Promise.all([
      getAllCategories(token),
      getAllAssets(token),
      getAssetRequestsByEmployee(user.userId, token),
    ])
      .then(([c, a, r]) => {
        setCategories(c || []);
        setAssets(a?.content || a || []);
        setMyRequests(r || []);
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token, user.userId]);

  useEffect(() => { load(); }, [load]);

  const submit = async () => {
    if (!form.categoryId || !form.requestReason.trim()) {
      setError("Please select a category and provide a reason");
      return;
    }
    setSaving(true);
    setError("");
    try {
      await createAssetRequest({
        categoryId: parseInt(form.categoryId),
        requestReason: form.requestReason,
      }, token);
      setModal(false);
      setSuccess("Asset request submitted successfully!");
      setForm({ categoryId: "", requestReason: "" });
      load();
      setTimeout(() => setSuccess(""), 4000);
    } catch (e) {
      setError(e.message);
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <Spinner />;

  const filteredCats = categories.filter((c) =>
    !searchCat || c.categoryName.toLowerCase().includes(searchCat.toLowerCase())
  );

  return (
    <div className="space-y-5">
      <ErrBanner msg={error} onClose={() => setError("")} />
      <SuccessBanner msg={success} />

      {/* Browse categories */}
      <div className="bg-white rounded-xl border border-slate-100 shadow-sm p-5">
        <div className="flex items-center justify-between mb-4">
          <div>
            <h3 className="font-bold text-slate-800">Browse Asset Categories</h3>
            <p className="text-xs text-slate-500">Select a category to request an asset</p>
          </div>
          <div className="flex items-center gap-3">
            <div className="relative">
              <span className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400 text-sm">🔍</span>
              <input
                value={searchCat}
                onChange={(e) => setSearchCat(e.target.value)}
                placeholder="Search categories..."
                className="border border-slate-200 rounded-lg pl-9 pr-4 py-2 text-sm outline-none bg-slate-50 w-44"
              />
            </div>
            <Btn onClick={() => setModal(true)}>+ New Request</Btn>
          </div>
        </div>
        <div className="grid grid-cols-4 gap-3">
          {filteredCats.map((c, i) => {
            const color     = CAT_COLORS[i % CAT_COLORS.length];
            const icon      = CAT_ICONS[i % CAT_ICONS.length];
            const available = assets.filter(
              (a) => a.categoryName === c.categoryName && a.assetStatus === "AVAILABLE"
            ).length;
            return (
              <div
                key={c.categoryId}
                className="border border-slate-200 rounded-xl p-4 hover:shadow-sm cursor-pointer transition-all relative overflow-hidden"
                onClick={() => { setForm((f) => ({ ...f, categoryId: String(c.categoryId) })); setModal(true); }}
              >
                <div className="absolute top-0 left-0 right-0 h-1 rounded-t-xl" style={{ background: color }} />
                <div
                  className="w-10 h-10 rounded-xl flex items-center justify-center text-xl mt-1 mb-3"
                  style={{ background: color + "20" }}
                >
                  {icon}
                </div>
                <h4 className="font-bold text-slate-800 text-sm">{c.categoryName}</h4>
                <p className="text-xs text-slate-400 mt-0.5 mb-3">{c.description}</p>
                <div className="flex items-center justify-between">
                  <span
                    className={cx(
                      "text-xs font-semibold px-2 py-0.5 rounded-full",
                      available > 0 ? "bg-emerald-100 text-emerald-700" : "bg-red-100 text-red-600"
                    )}
                  >
                    {available} available
                  </span>
                  <span className="text-blue-600 text-xs font-semibold">Request →</span>
                </div>
              </div>
            );
          })}
        </div>
      </div>

      {/* My Request History */}
      <div className="bg-white rounded-xl border border-slate-100 shadow-sm p-5">
        <h3 className="font-bold text-slate-800 mb-4">My Request History</h3>
        {myRequests.length === 0 ? (
          <p className="text-slate-400 text-sm text-center py-6">No requests submitted yet</p>
        ) : (
          <table className="w-full text-sm">
            <thead>
              <tr className="border-b border-slate-100">
                {["Category", "Reason", "Status", "Date"].map((h) => (
                  <th key={h} className="pb-3 text-left text-xs font-semibold text-slate-500">{h}</th>
                ))}
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-50">
              {myRequests.map((r) => (
                <tr key={r.requestId} className="hover:bg-slate-50">
                  <td className="py-3 font-medium text-slate-800">{r.categoryName}</td>
                  <td className="py-3 text-slate-500 text-xs max-w-[200px] truncate">{r.requestReason}</td>
                  <td className="py-3"><Badge label={r.requestStatus} /></td>
                  <td className="py-3 text-slate-400 text-xs">{r.requestDate || "—"}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {/* Request Modal */}
      <Modal open={modal} onClose={() => setModal(false)} title="Request New Asset">
        <ErrBanner msg={error} onClose={() => setError("")} />
        <div className="flex flex-col gap-4 mt-2">
          <Select label="Asset Category" required value={form.categoryId}
            onChange={(e) => setForm((f) => ({ ...f, categoryId: e.target.value }))}>
            <option value="">Select a category</option>
            {categories.map((c) => <option key={c.categoryId} value={c.categoryId}>{c.categoryName}</option>)}
          </Select>
          <Textarea label="Reason for Request" required rows={3}
            placeholder="Explain why you need this asset..."
            value={form.requestReason}
            onChange={(e) => setForm((f) => ({ ...f, requestReason: e.target.value }))} />
        </div>
        <div className="flex gap-3 mt-6">
          <Btn variant="ghost" className="flex-1" onClick={() => setModal(false)}>Cancel</Btn>
          <Btn className="flex-1" onClick={submit} disabled={saving}>
            {saving ? "Submitting…" : "Submit Request"}
          </Btn>
        </div>
      </Modal>
    </div>
  );
}
