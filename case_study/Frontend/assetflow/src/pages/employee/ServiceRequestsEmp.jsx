// ─── Service Requests Employee Page ──────────────────────────────────────────
import { useState, useEffect, useCallback } from "react";
import { getServiceRequestsByEmployee, createServiceRequest } from "../../services/serviceRequestService";
import { getMyActiveAllocations } from "../../services/allocationService";
import { Spinner } from "../../components/ui/Spinner";
import { ErrBanner } from "../../components/ui/ErrBanner";
import { SuccessBanner } from "../../components/ui/SuccessBanner";
import { Badge } from "../../components/ui/Badge";
import { Modal } from "../../components/ui/Modal";
import { Select } from "../../components/ui/Select";
import { Textarea } from "../../components/ui/Textarea";
import { Btn } from "../../components/ui/Btn";

const ISSUE_TYPES = ["MALFUNCTION", "REPAIR", "UPGRADE", "REPLACEMENT", "OTHER"];

export function ServiceRequestsEmp({ user, token }) {
  const [tickets, setTickets]         = useState([]);
  const [myAssets, setMyAssets]       = useState([]);
  const [loading, setLoading]         = useState(true);
  const [error, setError]             = useState("");
  const [success, setSuccess]         = useState("");
  const [modal, setModal]             = useState(false);
  const [saving, setSaving]           = useState(false);
  const [form, setForm]               = useState({ allocationId: "", issueType: "MALFUNCTION", description: "" });

  const load = useCallback(() => {
    setLoading(true);
    Promise.all([
      getServiceRequestsByEmployee(user.userId, token),
      getMyActiveAllocations(token),
    ])
      .then(([t, a]) => {
        setTickets(t || []);
        setMyAssets(a || []);
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token, user.userId]);

  useEffect(() => { load(); }, [load]);

  const submit = async () => {
    if (!form.allocationId || !form.description.trim()) {
      setError("Please select an asset and describe the issue");
      return;
    }
    setSaving(true);
    setError("");
    try {
      const selected = myAssets.find((a) => String(a.allocationId) === String(form.allocationId));
await createServiceRequest({
  assetId: selected?.assetId,
  issueType: form.issueType,
  description: form.description,
}, token);
      setModal(false);
      setSuccess("Service request submitted!");
      setForm({ allocationId: "", issueType: "MALFUNCTION", description: "" });
      load();
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
            <h3 className="font-bold text-slate-800">My Service Requests</h3>
            <p className="text-xs text-slate-500">Raise and track asset service tickets</p>
          </div>
          <Btn onClick={() => setModal(true)}>+ Raise Ticket</Btn>
        </div>
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-slate-100">
              {["Asset", "Issue Type", "Description", "Status", "Date"].map((h) => (
                <th key={h} className="px-4 py-3 text-left text-xs font-semibold text-slate-500">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-50">
            {tickets.map((t) => (
              <tr key={t.serviceRequestId} className="hover:bg-slate-50">
                <td className="px-4 py-3 font-medium text-slate-800">{t.assetName}</td>
                <td className="px-4 py-3"><Badge label={t.issueType} /></td>
                <td className="px-4 py-3 text-slate-500 text-xs max-w-[200px] truncate">{t.description}</td>
                <td className="px-4 py-3"><Badge label={t.serviceStatus} /></td>
                <td className="px-4 py-3 text-slate-400 text-xs">{t.requestDate || "—"}</td>
              </tr>
            ))}
          </tbody>
        </table>
        {tickets.length === 0 && (
          <div className="text-center py-12">
            <div className="text-4xl mb-2">🔧</div>
            <p className="text-slate-500">No service tickets raised yet</p>
          </div>
        )}
      </div>

      <Modal open={modal} onClose={() => setModal(false)} title="Raise Service Request">
        <ErrBanner msg={error} onClose={() => setError("")} />
        <div className="flex flex-col gap-4 mt-2">
          <Select label="Asset" required value={form.allocationId}
            onChange={(e) => setForm((f) => ({ ...f, allocationId: e.target.value }))}>
            <option value="">Select an asset</option>
            {myAssets.map((a) => (
              <option key={a.allocationId} value={a.allocationId}>{a.assetName}</option>
            ))}
          </Select>
          <Select label="Issue Type" value={form.issueType}
            onChange={(e) => setForm((f) => ({ ...f, issueType: e.target.value }))}>
            {ISSUE_TYPES.map((t) => <option key={t} value={t}>{t}</option>)}
          </Select>
          <Textarea label="Description" required rows={3}
            placeholder="Describe the issue in detail..."
            value={form.description}
            onChange={(e) => setForm((f) => ({ ...f, description: e.target.value }))} />
        </div>
        <div className="flex gap-3 mt-6">
          <Btn variant="ghost" className="flex-1" onClick={() => setModal(false)}>Cancel</Btn>
          <Btn className="flex-1" onClick={submit} disabled={saving}>
            {saving ? "Submitting…" : "Submit Ticket"}
          </Btn>
        </div>
      </Modal>
    </div>
  );
}
