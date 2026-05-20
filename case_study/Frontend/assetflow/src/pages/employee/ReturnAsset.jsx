// ─── Return Asset Page ────────────────────────────────────────────────────────
import { useState, useEffect, useCallback } from "react";
import { getReturnRequestsByEmployee, createReturnRequest } from "../../services/returnRequestService";
import { getMyActiveAllocations } from "../../services/allocationService";
import { Spinner } from "../../components/ui/Spinner";
import { ErrBanner } from "../../components/ui/ErrBanner";
import { SuccessBanner } from "../../components/ui/SuccessBanner";
import { Badge } from "../../components/ui/Badge";
import { Modal } from "../../components/ui/Modal";
import { Select } from "../../components/ui/Select";
import { Textarea } from "../../components/ui/Textarea";
import { Btn } from "../../components/ui/Btn";

const RETURN_REASONS = [
  "No longer required",
  "Project completed",
  "Asset damaged",
  "Upgrading to new asset",
  "Leaving organization",
  "Other",
];

export function ReturnAsset({ user, token }) {
  const [returns, setReturns]   = useState([]);
  const [myAssets, setMyAssets] = useState([]);
  const [loading, setLoading]   = useState(true);
  const [error, setError]       = useState("");
  const [success, setSuccess]   = useState("");
  const [modal, setModal]       = useState(false);
  const [saving, setSaving]     = useState(false);
const [form, setForm] = useState({ allocationId: "", returnReason: "", remarks: "" });
  const load = useCallback(() => {
    setLoading(true);
    Promise.all([
      getReturnRequestsByEmployee(user.userId, token),
      getMyActiveAllocations(token),
    ])
      .then(([r, a]) => {
        setReturns(r || []);
        setMyAssets(a || []);
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token, user.userId]);

  useEffect(() => { load(); }, [load]);

  const submit = async () => {
   if (!form.allocationId || !form.returnReason) {
  setError("Please select an asset and reason");
  return;
}
    setSaving(true);
    setError("");
    try {
     const selected = myAssets.find((a) => String(a.allocationId) === String(form.allocationId));
await createReturnRequest({
  assetId: selected?.assetId,
  allocationId: parseInt(form.allocationId),
  returnReason: form.returnReason,
}, token);
      setModal(false);
      setSuccess("Return request submitted successfully!");
setForm({ allocationId: "", returnReason: "", remarks: "" });      load();
      setTimeout(() => setSuccess(""), 4000);
    } catch (e) {
      setError(e.message);
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <Spinner />;

  return (
    <div className="space-y-5">
      <ErrBanner msg={error} onClose={() => setError("")} />
      <SuccessBanner msg={success} />

      <div className="bg-white rounded-xl border border-slate-100 shadow-sm">
        <div className="p-4 border-b border-slate-100 flex items-center justify-between">
          <div>
            <h3 className="font-bold text-slate-800">Return Requests</h3>
            <p className="text-xs text-slate-500">Request to return a borrowed asset</p>
          </div>
          <Btn onClick={() => setModal(true)}>↩ Return Asset</Btn>
        </div>
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-slate-100">
              {["Asset", "Reason", "Status", "Date"].map((h) => (
                <th key={h} className="px-4 py-3 text-left text-xs font-semibold text-slate-500">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-50">
            {returns.map((r) => (
              <tr key={r.returnRequestId} className="hover:bg-slate-50">
                <td className="px-4 py-3 font-medium text-slate-800">{r.assetName}</td>
                <td className="px-4 py-3 text-slate-500 text-xs">{r.reason || "—"}</td>
                <td className="px-4 py-3"><Badge label={r.returnStatus} /></td>
                <td className="px-4 py-3 text-slate-400 text-xs">{r.requestDate || "—"}</td>
              </tr>
            ))}
          </tbody>
        </table>
        {returns.length === 0 && (
          <div className="text-center py-12">
            <div className="text-4xl mb-2">↩</div>
            <p className="text-slate-500">No return requests yet</p>
          </div>
        )}
      </div>

      <Modal open={modal} onClose={() => setModal(false)} title="Request Asset Return">
        <ErrBanner msg={error} onClose={() => setError("")} />
        <div className="flex flex-col gap-4 mt-2">
          <Select label="Asset to Return" required value={form.allocationId}
            onChange={(e) => setForm((f) => ({ ...f, allocationId: e.target.value }))}>
            <option value="">Select an asset</option>
            {myAssets.map((a) => (
              <option key={a.allocationId} value={a.allocationId}>{a.assetName}</option>
            ))}
          </Select>
          <Select label="Reason" required value={form.returnReason}
  onChange={(e) => setForm((f) => ({ ...f, returnReason: e.target.value }))}>
            <option value="">Select reason</option>
            {RETURN_REASONS.map((r) => <option key={r} value={r}>{r}</option>)}
          </Select>
          <Textarea label="Additional Remarks" rows={2}
            placeholder="Optional remarks..."
            value={form.remarks}
            onChange={(e) => setForm((f) => ({ ...f, remarks: e.target.value }))} />
        </div>
        <div className="flex gap-3 mt-6">
          <Btn variant="ghost" className="flex-1" onClick={() => setModal(false)}>Cancel</Btn>
          <Btn className="flex-1" onClick={submit} disabled={saving}>
            {saving ? "Submitting…" : "Submit Return Request"}
          </Btn>
        </div>
      </Modal>
    </div>
  );
}
