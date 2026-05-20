// ─── Audit Verification Page ──────────────────────────────────────────────────
import { useState, useEffect, useCallback } from "react";
import { getAuditRequestsByEmployee, submitAuditResponse } from "../../services/auditService";
import { Spinner } from "../../components/ui/Spinner";
import { ErrBanner } from "../../components/ui/ErrBanner";
import { SuccessBanner } from "../../components/ui/SuccessBanner";
import { Badge } from "../../components/ui/Badge";
import { Modal } from "../../components/ui/Modal";
import { Select } from "../../components/ui/Select";
import { Textarea } from "../../components/ui/Textarea";
import { Btn } from "../../components/ui/Btn";

const STATUSES = ["VERIFIED", "NOT_VERIFIED", "DISCREPANCY", "MISSING"];

export function AuditVerification({ user, token }) {
  const [audits, setAudits]   = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError]     = useState("");
  const [success, setSuccess] = useState("");
  const [modal, setModal]     = useState(null);
  const [saving, setSaving]   = useState(false);
const [form, setForm] = useState({ verificationStatus: "VERIFIED", comment: "" });
  const load = useCallback(() => {
    setLoading(true);
    getAuditRequestsByEmployee(user.userId, token)
      .then((d) => setAudits(d || []))
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token, user.userId]);

  useEffect(() => { load(); }, [load]);

  const submit = async () => {
    setSaving(true);
    setError("");
    try {
      await submitAuditResponse({
  auditRequestId: modal.auditRequestId,
  verificationStatus: form.verificationStatus,
  comment: form.comment,
}, token);
      setModal(null);
      setSuccess("Audit response submitted successfully!");
      load();
      setTimeout(() => setSuccess(""), 4000);
    } catch (e) {
      setError(e.message);
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <Spinner />;

  const pending = audits.filter((a) => a.auditStatus === "PENDING");

  return (
    <div className="space-y-5">
      <ErrBanner msg={error} onClose={() => setError("")} />
      <SuccessBanner msg={success} />

      {pending.length > 0 && (
        <div className="bg-amber-50 border border-amber-200 rounded-xl p-4 flex items-center gap-3">
          <span className="text-2xl">⚠</span>
          <div>
            <p className="font-semibold text-amber-800">Pending Audit{pending.length > 1 ? "s" : ""}</p>
            <p className="text-amber-600 text-sm">
              You have {pending.length} pending audit{pending.length > 1 ? "s" : ""} awaiting your response
            </p>
          </div>
        </div>
      )}

      <div className="bg-white rounded-xl border border-slate-100 shadow-sm">
        <div className="p-4 border-b border-slate-100">
          <h3 className="font-bold text-slate-800">Audit Verification Requests</h3>
          <p className="text-xs text-slate-500">Respond to asset audit requests from admin</p>
        </div>
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-slate-100">
              {["Asset", "Status", "Audit Date", "Actions"].map((h) => (
                <th key={h} className="px-4 py-3 text-left text-xs font-semibold text-slate-500">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-50">
            {audits.map((a) => (
              <tr key={a.auditRequestId} className="hover:bg-slate-50">
                <td className="px-4 py-3 font-medium text-slate-800">{a.assetName}</td>
                <td className="px-4 py-3"><Badge label={a.auditStatus} /></td>
                <td className="px-4 py-3 text-slate-400 text-xs">{a.auditDate || "—"}</td>
                <td className="px-4 py-3">
                  {a.auditStatus === "PENDING" && (
                    <Btn
                      size="sm"
                      onClick={() => {
                        setModal(a);
setForm({ verificationStatus: "VERIFIED", comment: "" });                        setError("");
                      }}
                    >
                      Verify
                    </Btn>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {audits.length === 0 && (
          <div className="text-center py-12">
            <div className="text-4xl mb-2">📋</div>
            <p className="text-slate-500">No audit requests assigned to you</p>
          </div>
        )}
      </div>

      <Modal open={!!modal} onClose={() => setModal(null)} title="Submit Audit Verification">
        {modal && (
          <>
            <div className="bg-blue-50 border border-blue-100 rounded-xl p-4 mb-5 text-sm">
              <p className="text-slate-500 text-xs font-medium uppercase mb-1">Asset Being Audited</p>
              <p className="font-bold text-slate-800 text-base">{modal.assetName}</p>
            </div>
            <ErrBanner msg={error} onClose={() => setError("")} />
            <div className="flex flex-col gap-4 mt-2">
              <Select label="Verification Status" required value={form.verificationStatus}
  onChange={(e) => setForm((f) => ({ ...f, verificationStatus: e.target.value }))}>
  {STATUSES.map((s) => <option key={s} value={s}>{s.replace("_", " ")}</option>)}
</Select>
<Textarea label="Comment" rows={3}
  placeholder="Additional observations..."
  value={form.comment}
  onChange={(e) => setForm((f) => ({ ...f, comment: e.target.value }))} />
            </div>
            <div className="flex gap-3 mt-6">
              <Btn variant="ghost" className="flex-1" onClick={() => setModal(null)}>Cancel</Btn>
              <Btn className="flex-1" onClick={submit} disabled={saving}>
                {saving ? "Submitting…" : "Submit Audit Response"}
              </Btn>
            </div>
          </>
        )}
      </Modal>
    </div>
  );
}
