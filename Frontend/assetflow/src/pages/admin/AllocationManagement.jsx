// ─── Allocation Management Page ───────────────────────────────────────────────
import { useState, useEffect, useCallback } from "react";
import { getAllAllocations, createAllocation, closeAllocation } from "../../services/allocationService";
import { getAllUsers } from "../../services/employeeService";
import { getAllAssets } from "../../services/assetService";
import { Spinner } from "../../components/ui/Spinner";
import { ErrBanner } from "../../components/ui/ErrBanner";
import { Badge } from "../../components/ui/Badge";
import { Avatar } from "../../components/ui/Avatar";
import { Modal } from "../../components/ui/Modal";
import { Select } from "../../components/ui/Select";
import { Input } from "../../components/ui/Input";
import { Textarea } from "../../components/ui/Textarea";
import { Btn } from "../../components/ui/Btn";

export function AllocationManagement({ token }) {
  const [allocations, setAllocations] = useState([]);
  const [employees, setEmployees]     = useState([]);
  const [assets, setAssets]           = useState([]);
  const [loading, setLoading]         = useState(true);
  const [error, setError]             = useState("");
  const [modal, setModal]             = useState(null);
  const [saving, setSaving]           = useState(false);
  const [form, setForm]               = useState({ employeeId: "", assetId: "", expectedReturnDate: "", remarks: "" });

  const load = useCallback(() => {
    setLoading(true);
    Promise.all([
      getAllAllocations(token),
      getAllUsers(token),
      getAllAssets(token),
    ])
      .then(([al, u, a]) => {
        setAllocations(al || []);
        setEmployees(u || []);
        setAssets(a?.content || a || []);
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token]);

  useEffect(() => { load(); }, [load]);

  const allocate = async () => {
    setSaving(true);
    setError("");
    try {
      const payload = {
        employeeId: parseInt(form.employeeId),
        assetId: parseInt(form.assetId),
        ...(form.expectedReturnDate ? { expectedReturnDate: form.expectedReturnDate } : {}),
        remarks: form.remarks,
      };
      await createAllocation(payload, token);
      setModal(null);
      load();
    } catch (e) {
      setError(e.message);
    } finally {
      setSaving(false);
    }
  };

  const handleClose = async (id) => {
    if (!window.confirm("Close this allocation?")) return;
    try {
      await closeAllocation(id, token);
      load();
    } catch (e) {
      setError(e.message);
    }
  };

  if (loading) return <Spinner />;

  const available = assets.filter((a) => a.assetStatus === "AVAILABLE");

  return (
    <div className="space-y-5">
      <ErrBanner msg={error} onClose={() => setError("")} />

      <div className="bg-white rounded-xl border border-slate-100 shadow-sm">
        <div className="p-4 border-b border-slate-100 flex items-center justify-between">
          <div>
            <h3 className="font-bold text-slate-800">Asset Allocations</h3>
            <p className="text-xs text-slate-500">Assign assets to employees</p>
          </div>
          <Btn onClick={() => { setForm({ employeeId: "", assetId: "", expectedReturnDate: "", remarks: "" }); setModal("alloc"); }}>
            + Allocate Asset
          </Btn>
        </div>
        <table className="w-full text-sm">
          <thead className="border-b border-slate-100">
            <tr>
              {["Asset", "Employee", "Allocated Date", "Expected Return", "Status", "Actions"].map((h) => (
                <th key={h} className="px-4 py-3 text-left text-xs font-semibold text-slate-500">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-50">
            {allocations.map((a) => (
              <tr key={a.allocationId} className="hover:bg-slate-50">
                <td className="px-4 py-3 font-semibold text-slate-800">{a.assetName}</td>
                <td className="px-4 py-3">
                  <div className="flex items-center gap-2">
                    <Avatar name={a.employeeName} size="sm" />
                    <span>{a.employeeName}</span>
                  </div>
                </td>
                <td className="px-4 py-3 text-slate-500 text-xs">{a.allocatedDate || "—"}</td>
                <td className="px-4 py-3 text-slate-500 text-xs">{a.expectedReturnDate || "—"}</td>
                <td className="px-4 py-3"><Badge label={a.allocationStatus} /></td>
                <td className="px-4 py-3">
                  {a.allocationStatus === "ACTIVE" && (
                    <Btn size="sm" variant="secondary" onClick={() => handleClose(a.allocationId)}>Close</Btn>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {allocations.length === 0 && <p className="text-center text-slate-400 py-8">No allocations found</p>}
      </div>

      <Modal open={modal === "alloc"} onClose={() => setModal(null)} title="Allocate Asset to Employee">
        <ErrBanner msg={error} onClose={() => setError("")} />
        <div className="flex flex-col gap-4 mt-2">
          <Select label="Employee" required value={form.employeeId}
            onChange={(e) => setForm((f) => ({ ...f, employeeId: e.target.value }))}>
            <option value="">Select Employee</option>
            {employees.filter((e) => e.role === "ROLE_EMPLOYEE").map((e) => (
              <option key={e.userId} value={e.userId}>{e.fullName}</option>
            ))}
          </Select>
          <Select label="Asset (Available only)" required value={form.assetId}
            onChange={(e) => setForm((f) => ({ ...f, assetId: e.target.value }))}>
            <option value="">Select Asset</option>
            {available.map((a) => (
              <option key={a.assetId} value={a.assetId}>{a.assetName} ({a.assetNo})</option>
            ))}
          </Select>
          <Input label="Expected Return Date" type="date"
            value={form.expectedReturnDate}
            onChange={(e) => setForm((f) => ({ ...f, expectedReturnDate: e.target.value }))} />
          <Textarea label="Remarks" rows={2}
            value={form.remarks} onChange={(e) => setForm((f) => ({ ...f, remarks: e.target.value }))} />
        </div>
        <div className="flex gap-3 mt-6">
          <Btn variant="ghost" className="flex-1" onClick={() => setModal(null)}>Cancel</Btn>
          <Btn className="flex-1" onClick={allocate} disabled={saving}>
            {saving ? "Allocating…" : "Allocate Asset"}
          </Btn>
        </div>
      </Modal>
    </div>
  );
}
