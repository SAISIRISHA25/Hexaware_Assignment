// ─── Return Requests Admin Page ───────────────────────────────────────────────
import { useState, useEffect, useCallback } from "react";
import { getAllReturnRequests, approveReturnRequest, rejectReturnRequest } from "../../services/returnRequestService";
import { Spinner } from "../../components/ui/Spinner";
import { ErrBanner } from "../../components/ui/ErrBanner";
import { Badge } from "../../components/ui/Badge";
import { Avatar } from "../../components/ui/Avatar";

export function ReturnRequestsAdmin({ token }) {
  const [returns, setReturns] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError]     = useState("");
  const [saving, setSaving]   = useState(null);

  const load = useCallback(() => {
    setLoading(true);
    getAllReturnRequests(token)
      .then((d) => setReturns(d || []))
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token]);

  useEffect(() => { load(); }, [load]);

  const doAction = async (id, action) => {
    setSaving(id + action);
    setError("");
    try {
      if (action === "approve") {
        await approveReturnRequest(id, token);
      } else {
        await rejectReturnRequest(id, token);
      }
      load();
    } catch (e) {
      setError(e.message);
    } finally {
      setSaving(null);
    }
  };

  if (loading) return <Spinner />;

  return (
    <div className="space-y-4">
      <ErrBanner msg={error} onClose={() => setError("")} />

      <div className="bg-white rounded-xl border border-slate-100 shadow-sm">
        <div className="p-4 border-b border-slate-100">
          <h3 className="font-bold text-slate-800">Return Requests</h3>
          <p className="text-xs text-slate-500">Approve or reject asset return requests</p>
        </div>
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-slate-100">
              {["Employee", "Asset", "Reason", "Status", "Date", "Actions"].map((h) => (
                <th key={h} className="px-4 py-3 text-left text-xs font-semibold text-slate-500">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-50">
            {returns.map((r) => (
              <tr key={r.returnRequestId} className="hover:bg-slate-50">
                <td className="px-4 py-3 flex items-center gap-2">
                  <Avatar name={r.employeeName} size="sm" />
                  <span className="font-medium text-slate-800">{r.employeeName}</span>
                </td>
                <td className="px-4 py-3 text-slate-600">{r.assetName}</td>
                <td className="px-4 py-3 text-slate-500 text-xs max-w-[180px] truncate">—</td>
                <td className="px-4 py-3"><Badge label={r.returnStatus} /></td>
                <td className="px-4 py-3 text-slate-400 text-xs">{r.requestDate || "—"}</td>
                <td className="px-4 py-3">
                  {r.returnStatus === "PENDING" && (
                    <div className="flex gap-1.5">
                      <button
                        disabled={!!saving}
                        onClick={() => doAction(r.returnRequestId, "approve")}
                        className="w-7 h-7 border border-emerald-300 rounded-full flex items-center justify-center text-xs text-emerald-600 hover:bg-emerald-50 disabled:opacity-50"
                      >✓</button>
                      <button
                        disabled={!!saving}
                        onClick={() => doAction(r.returnRequestId, "reject")}
                        className="w-7 h-7 border border-red-300 rounded-full flex items-center justify-center text-xs text-red-500 hover:bg-red-50 disabled:opacity-50"
                      >✕</button>
                    </div>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {returns.length === 0 && <p className="text-center text-slate-400 py-8">No return requests</p>}
      </div>
    </div>
  );
}
