// ─── Request Approvals Page ───────────────────────────────────────────────────
import { useState, useEffect, useCallback } from "react";
import { getAllAssetRequests, approveAssetRequest, rejectAssetRequest } from "../../services/assetRequestService";
import { getAllUsers } from "../../services/employeeService";
import { getAllAssets } from "../../services/assetService";
import { createAllocation } from "../../services/allocationService";
import { Spinner } from "../../components/ui/Spinner";
import { ErrBanner } from "../../components/ui/ErrBanner";
import { Badge } from "../../components/ui/Badge";
import { Avatar } from "../../components/ui/Avatar";
import { Modal } from "../../components/ui/Modal";
import { Input } from "../../components/ui/Input";
import { Textarea } from "../../components/ui/Textarea";
import { Btn } from "../../components/ui/Btn";
import { cx } from "../../components/ui/cx";

const TABS = ["ALL", "PENDING", "APPROVED", "REJECTED"];

export function RequestApprovals({ token }) {
  const [requests, setRequests]     = useState([]);
  const [employees, setEmployees]   = useState([]);
  const [assets, setAssets]         = useState([]);
  const [loading, setLoading]       = useState(true);
  const [error, setError]           = useState("");
  const [tab, setTab]               = useState("ALL");
  const [search, setSearch]         = useState("");
  const [rejectSaving, setRejectSaving] = useState(null);
  const [approveModal, setApproveModal] = useState(null);
  const [allocForm, setAllocForm]   = useState({ assetId: "", expectedReturnDate: "", remarks: "" });
  const [allocSaving, setAllocSaving] = useState(false);

  const load = useCallback(() => {
    setLoading(true);
    Promise.all([
      getAllAssetRequests(token),
      getAllUsers(token),
      getAllAssets(token),
    ])
      .then(([ar, u, a]) => {
        setRequests(ar || []);
        setEmployees(u || []);
        setAssets(a?.content || a || []);
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token]);

  useEffect(() => { load(); }, [load]);

  const openApprove = (r) => {
    setApproveModal(r);
    setAllocForm({ assetId: "", expectedReturnDate: "", remarks: "" });
    setError("");
  };

  const doApproveAndAllocate = async () => {
    if (!allocForm.assetId) { setError("Please select an asset to allocate"); return; }
    setAllocSaving(true);
    setError("");
    try {
      const emp = employees.find((e) => e.fullName === approveModal.employeeName);
      if (!emp) throw new Error(`Employee "${approveModal.employeeName}" not found in system`);
      await approveAssetRequest(approveModal.requestId, token);
      await createAllocation({
        employeeId: emp.userId,
        assetId: parseInt(allocForm.assetId),
        ...(allocForm.expectedReturnDate ? { expectedReturnDate: allocForm.expectedReturnDate } : {}),
        ...(allocForm.remarks ? { remarks: allocForm.remarks } : {}),
      }, token);
      setApproveModal(null);
      load();
    } catch (e) {
      setError(e.message);
    } finally {
      setAllocSaving(false);
    }
  };

  const doApproveOnly = async () => {
    setAllocSaving(true);
    setError("");
    try {
      await approveAssetRequest(approveModal.requestId, token);
      setApproveModal(null);
      load();
    } catch (e) {
      setError(e.message);
    } finally {
      setAllocSaving(false);
    }
  };

  const doReject = async (id) => {
    setRejectSaving(id);
    setError("");
    try {
      await rejectAssetRequest(id, token);
      load();
    } catch (e) {
      setError(e.message);
    } finally {
      setRejectSaving(null);
    }
  };

  const filtered = requests.filter(
    (r) =>
      (tab === "ALL" || r.requestStatus === tab) &&
      (!search ||
        r.employeeName?.toLowerCase().includes(search.toLowerCase()) ||
        r.categoryName?.toLowerCase().includes(search.toLowerCase()))
  );

  const counts = Object.fromEntries(
    TABS.map((t) => [t, t === "ALL" ? requests.length : requests.filter((r) => r.requestStatus === t).length])
  );

  const eligibleAssets = approveModal
    ? assets.filter(
        (a) =>
          a.assetStatus === "AVAILABLE" &&
          a.categoryName?.toLowerCase() === approveModal.categoryName?.toLowerCase()
      )
    : [];

  if (loading) return <Spinner />;

  return (
    <div className="space-y-4">
      <ErrBanner msg={error} onClose={() => setError("")} />

      {/* Tabs */}
      <div className="flex gap-2 bg-white rounded-xl border border-slate-100 shadow-sm p-2">
        {TABS.map((t) => (
          <button
            key={t}
            onClick={() => setTab(t)}
            className={cx(
              "flex items-center gap-1.5 px-4 py-2 rounded-lg text-sm font-medium transition-all",
              tab === t ? "bg-blue-600 text-white shadow-sm" : "text-slate-600 hover:bg-slate-50"
            )}
          >
            {t}
            <span className={cx("text-xs px-1.5 py-0.5 rounded-full", tab === t ? "bg-blue-500" : "bg-slate-200 text-slate-600")}>
              {counts[t]}
            </span>
          </button>
        ))}
      </div>

      {/* Table */}
      <div className="bg-white rounded-xl border border-slate-100 shadow-sm">
        <div className="p-4 border-b border-slate-100 flex items-center gap-3">
          <div className="relative">
            <span className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400 text-sm">🔍</span>
            <input
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              placeholder="Search requests..."
              className="border border-slate-200 rounded-lg pl-9 pr-4 py-2 text-sm outline-none bg-slate-50 w-52"
            />
          </div>
          <p className="ml-auto text-xs text-slate-500">{filtered.length} requests</p>
        </div>
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-slate-100">
              {["Employee", "Category", "Reason", "Status", "Date", "Actions"].map((h) => (
                <th key={h} className="px-4 py-3 text-left text-xs font-semibold text-slate-500">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-50">
            {filtered.map((r) => (
              <tr key={r.requestId} className="hover:bg-slate-50">
                <td className="px-4 py-3">
                  <div className="flex items-center gap-2">
                    <Avatar name={r.employeeName} size="sm" />
                    <span className="font-medium text-slate-800">{r.employeeName}</span>
                  </div>
                </td>
                <td className="px-4 py-3 text-slate-600">{r.categoryName}</td>
                <td className="px-4 py-3 text-slate-500 text-xs max-w-[200px] truncate">{r.requestReason}</td>
                <td className="px-4 py-3"><Badge label={r.requestStatus} /></td>
                <td className="px-4 py-3 text-slate-400 text-xs">{r.requestDate || "—"}</td>
                <td className="px-4 py-3">
                  {r.requestStatus === "PENDING" && (
                    <div className="flex gap-1.5">
                      <button
                        onClick={() => openApprove(r)}
                        className="w-7 h-7 border border-emerald-300 rounded-full flex items-center justify-center text-xs text-emerald-600 hover:bg-emerald-50"
                      >✓</button>
                      <button
                        disabled={rejectSaving === r.requestId}
                        onClick={() => doReject(r.requestId)}
                        className="w-7 h-7 border border-red-300 rounded-full flex items-center justify-center text-xs text-red-500 hover:bg-red-50 disabled:opacity-50"
                      >✕</button>
                    </div>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {filtered.length === 0 && <p className="text-center text-slate-400 py-8">No requests</p>}
      </div>

      {/* Approve & Allocate Modal */}
      <Modal open={!!approveModal} onClose={() => setApproveModal(null)} title="Approve Request & Allocate Asset" size="md">
        {approveModal && (
          <>
            <div className="bg-blue-50 border border-blue-100 rounded-xl p-4 mb-5">
              <p className="text-xs text-blue-500 font-semibold uppercase tracking-wide mb-2">Asset Request Details</p>
              <div className="grid grid-cols-2 gap-3 text-sm">
                <div>
                  <p className="text-slate-400 text-xs">Requested By</p>
                  <div className="flex items-center gap-2 mt-1">
                    <Avatar name={approveModal.employeeName} size="sm" />
                    <span className="font-semibold text-slate-800">{approveModal.employeeName}</span>
                  </div>
                </div>
                <div>
                  <p className="text-slate-400 text-xs">Category Requested</p>
                  <p className="font-semibold text-slate-800 mt-1">📦 {approveModal.categoryName}</p>
                </div>
                <div className="col-span-2">
                  <p className="text-slate-400 text-xs">Reason</p>
                  <p className="text-slate-700 text-sm mt-1 italic">"{approveModal.requestReason}"</p>
                </div>
              </div>
            </div>

            <ErrBanner msg={error} onClose={() => setError("")} />

            <div className="flex flex-col gap-4 mt-2">
              <div>
                <label className="block text-sm font-medium text-slate-700 mb-1.5">
                  Select Asset to Allocate <span className="text-red-500">*</span>
                  <span className="ml-2 text-xs text-slate-400 font-normal">
                    ({eligibleAssets.length} available in {approveModal.categoryName})
                  </span>
                </label>
                {eligibleAssets.length === 0 ? (
                  <div className="bg-amber-50 border border-amber-200 rounded-lg px-4 py-3 text-sm text-amber-700">
                    ⚠ No available assets in the <strong>{approveModal.categoryName}</strong> category.
                  </div>
                ) : (
                  <select
                    value={allocForm.assetId}
                    onChange={(e) => setAllocForm((f) => ({ ...f, assetId: e.target.value }))}
                    className="w-full border border-slate-200 rounded-lg px-3.5 py-2.5 text-sm outline-none focus:border-blue-400 focus:ring-2 focus:ring-blue-50 bg-slate-50 focus:bg-white"
                  >
                    <option value="">— Select an asset —</option>
                    {eligibleAssets.map((a) => (
                      <option key={a.assetId} value={a.assetId}>
                        {a.assetName} {a.assetNo ? `(${a.assetNo})` : ""}
                      </option>
                    ))}
                  </select>
                )}
              </div>
              <Input label="Expected Return Date" type="date"
                value={allocForm.expectedReturnDate}
                onChange={(e) => setAllocForm((f) => ({ ...f, expectedReturnDate: e.target.value }))} />
              <Textarea label="Remarks" rows={2} placeholder="Optional notes for the employee..."
                value={allocForm.remarks}
                onChange={(e) => setAllocForm((f) => ({ ...f, remarks: e.target.value }))} />
            </div>

            <div className="flex gap-3 mt-6">
              <Btn variant="ghost" className="flex-1" onClick={() => setApproveModal(null)}>Cancel</Btn>
              {eligibleAssets.length === 0 ? (
                <Btn variant="success" className="flex-1" disabled={allocSaving} onClick={doApproveOnly}>
                  {allocSaving ? "Approving…" : "✓ Approve Only"}
                </Btn>
              ) : (
                <Btn variant="success" className="flex-1" disabled={allocSaving} onClick={doApproveAndAllocate}>
                  {allocSaving ? "Processing…" : "✓ Approve & Allocate"}
                </Btn>
              )}
            </div>
          </>
        )}
      </Modal>
    </div>
  );
}
