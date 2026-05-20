// ─── Audit Management Page ────────────────────────────────────────────────────
import { useState, useEffect, useCallback } from "react";
import { getAllAuditRequests, createAuditRequest } from "../../services/auditService";
import { getAllUsers } from "../../services/employeeService";
import { getAllAssets } from "../../services/assetService";
import { Spinner } from "../../components/ui/Spinner";
import { ErrBanner } from "../../components/ui/ErrBanner";
import { Badge } from "../../components/ui/Badge";
import { Avatar } from "../../components/ui/Avatar";
import { Modal } from "../../components/ui/Modal";
import { Select } from "../../components/ui/Select";
import { Textarea } from "../../components/ui/Textarea";
import { Btn } from "../../components/ui/Btn";
import { cx } from "../../components/ui/cx";

export function AuditManagement({ token }) {
  const [audits, setAudits]       = useState([]);
  const [employees, setEmployees] = useState([]);
  const [assets, setAssets]       = useState([]);
  const [loading, setLoading]     = useState(true);
  const [error, setError]         = useState("");
  const [modal, setModal]         = useState(false);
  const [saving, setSaving]       = useState(false);
  const [form, setForm]           = useState({ employeeId: "", assetId: "", remarks: "" });

  const load = useCallback(() => {
    setLoading(true);
    Promise.all([
      getAllAuditRequests(token),
      getAllUsers(token),
      getAllAssets(token),
    ])
      .then(([au, u, a]) => {
        setAudits(au || []);
        setEmployees(u || []);
        setAssets(a?.content || a || []);
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token]);

  useEffect(() => { load(); }, [load]);

  const schedule = async () => {
    setSaving(true);
    setError("");
    try {
      await createAuditRequest({
        employeeId: parseInt(form.employeeId),
        assetId: parseInt(form.assetId),
        remarks: form.remarks,
      }, token);
      setModal(false);
      load();
    } catch (e) {
      setError(e.message);
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <Spinner />;

  const pending   = audits.filter((a) => a.auditStatus === "PENDING").length;
  const completed = audits.filter((a) => a.auditStatus === "COMPLETED").length;

  return (
    <div className="space-y-4">
      <ErrBanner msg={error} onClose={() => setError("")} />

      {/* Stats */}
      <div className="grid grid-cols-3 gap-4">
        {[
          { label: "Total Audits", val: audits.length, color: "text-blue-600"    },
          { label: "Pending",      val: pending,        color: "text-amber-600"   },
          { label: "Completed",    val: completed,      color: "text-emerald-600" },
        ].map((s) => (
          <div key={s.label} className="bg-white rounded-xl border border-slate-100 shadow-sm p-5 text-center">
            <p className={cx("text-3xl font-black", s.color)}>{s.val}</p>
            <p className="text-sm text-slate-500 mt-1">{s.label}</p>
          </div>
        ))}
      </div>

      {/* Table */}
      <div className="bg-white rounded-xl border border-slate-100 shadow-sm">
        <div className="p-4 border-b border-slate-100 flex items-center justify-between">
          <div>
            <h3 className="font-bold text-slate-800">Audit Requests</h3>
          </div>
          <Btn size="sm" onClick={() => { setForm({ employeeId: "", assetId: "", remarks: "" }); setModal(true); }}>
            + Schedule Audit
          </Btn>
        </div>
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-slate-100">
              {["Employee", "Asset", "Status", "Audit Date"].map((h) => (
                <th key={h} className="px-4 py-3 text-left text-xs font-semibold text-slate-500">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-50">
            {audits.map((a) => (
              <tr key={a.auditRequestId} className="hover:bg-slate-50">
                <td className="px-4 py-3 flex items-center gap-2">
                  <Avatar name={a.employeeName} size="sm" />
                  <span className="font-medium text-slate-800">{a.employeeName}</span>
                </td>
                <td className="px-4 py-3 text-slate-600">{a.assetName}</td>
                <td className="px-4 py-3"><Badge label={a.auditStatus} /></td>
                <td className="px-4 py-3 text-slate-400 text-xs">{a.auditDate || "—"}</td>
              </tr>
            ))}
          </tbody>
        </table>
        {audits.length === 0 && <p className="text-center text-slate-400 py-8">No audit requests</p>}
      </div>

      <Modal open={modal} onClose={() => setModal(false)} title="Schedule New Audit">
        <ErrBanner msg={error} onClose={() => setError("")} />
        <div className="flex flex-col gap-4 mt-2">
          <Select label="Employee" required value={form.employeeId}
            onChange={(e) => setForm((f) => ({ ...f, employeeId: e.target.value }))}>
            <option value="">Select Employee</option>
            {employees.filter((e) => e.role === "ROLE_EMPLOYEE").map((e) => (
              <option key={e.userId} value={e.userId}>{e.fullName}</option>
            ))}
          </Select>
          <Select label="Asset" required value={form.assetId}
            onChange={(e) => setForm((f) => ({ ...f, assetId: e.target.value }))}>
            <option value="">Select Asset</option>
            {assets.map((a) => (
              <option key={a.assetId} value={a.assetId}>{a.assetName} ({a.assetNo})</option>
            ))}
          </Select>
          <Textarea label="Remarks" rows={2}
            value={form.remarks} onChange={(e) => setForm((f) => ({ ...f, remarks: e.target.value }))} />
        </div>
        <div className="flex gap-3 mt-6">
          <Btn variant="ghost" className="flex-1" onClick={() => setModal(false)}>Cancel</Btn>
          <Btn className="flex-1" onClick={schedule} disabled={saving}>
            {saving ? "Scheduling…" : "Schedule Audit"}
          </Btn>
        </div>
      </Modal>
    </div>
  );
}
