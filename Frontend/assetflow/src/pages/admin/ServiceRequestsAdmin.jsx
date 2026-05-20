// ─── Service Requests Admin Page ─────────────────────────────────────────────
import { useState, useEffect, useCallback } from "react";
import { getAllServiceRequests, updateServiceRequestStatus } from "../../services/serviceRequestService";
import { Spinner } from "../../components/ui/Spinner";
import { ErrBanner } from "../../components/ui/ErrBanner";
import { Badge } from "../../components/ui/Badge";
import { Avatar } from "../../components/ui/Avatar";
import { Modal } from "../../components/ui/Modal";
import { Select } from "../../components/ui/Select";
import { Btn } from "../../components/ui/Btn";
import { cx } from "../../components/ui/cx";

const TABS = ["ALL", "PENDING", "IN_PROGRESS", "COMPLETED", "REJECTED"];

export function ServiceRequestsAdmin({ token }) {
  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError]     = useState("");
  const [tab, setTab]         = useState("ALL");
  const [modal, setModal]     = useState(null);
  const [selected, setSelected] = useState(null);
  const [newStatus, setNewStatus] = useState("IN_PROGRESS");
  const [saving, setSaving]   = useState(false);

  const load = useCallback(() => {
    setLoading(true);
    getAllServiceRequests(token)
      .then((d) => setTickets(d || []))
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token]);

  useEffect(() => { load(); }, [load]);

  const updateStatus = async () => {
    setSaving(true);
    setError("");
    try {
      await updateServiceRequestStatus(selected.serviceRequestId, newStatus, token);
      setModal(null);
      load();
    } catch (e) {
      setError(e.message);
    } finally {
      setSaving(false);
    }
  };

  const filtered = tickets.filter((t) => tab === "ALL" || t.serviceStatus === tab);

  if (loading) return <Spinner />;

  return (
    <div className="space-y-4">
      <ErrBanner msg={error} onClose={() => setError("")} />

      {/* Stat cards */}
      <div className="grid grid-cols-4 gap-4">
        {[
          { label: "Pending",     val: tickets.filter((t) => t.serviceStatus === "PENDING").length,     color: "text-amber-500"   },
          { label: "In Progress", val: tickets.filter((t) => t.serviceStatus === "IN_PROGRESS").length, color: "text-blue-600"    },
          { label: "Completed",   val: tickets.filter((t) => t.serviceStatus === "COMPLETED").length,   color: "text-emerald-600" },
          { label: "Rejected",    val: tickets.filter((t) => t.serviceStatus === "REJECTED").length,    color: "text-red-500"     },
        ].map((s) => (
          <div key={s.label} className="bg-white rounded-xl border border-slate-100 shadow-sm p-5 text-center">
            <p className={cx("text-3xl font-black", s.color)}>{s.val}</p>
            <p className="text-xs text-slate-500 mt-1">{s.label}</p>
          </div>
        ))}
      </div>

      {/* Tab filters */}
      <div className="flex gap-2">
        {TABS.map((t) => (
          <button
            key={t}
            onClick={() => setTab(t)}
            className={cx(
              "px-4 py-2 rounded-lg text-sm font-medium border transition-all",
              tab === t
                ? "bg-blue-600 text-white border-blue-600"
                : "bg-white text-slate-600 border-slate-200 hover:border-blue-200"
            )}
          >
            {t.replace("_", " ")}
          </button>
        ))}
      </div>

      {/* Tickets table */}
      <div className="bg-white rounded-xl border border-slate-100 shadow-sm">
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-slate-100">
              {["Employee", "Asset", "Issue Type", "Description", "Status", "Actions"].map((h) => (
                <th key={h} className="px-4 py-3 text-left text-xs font-semibold text-slate-500">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-50">
            {filtered.map((t) => (
              <tr key={t.serviceRequestId} className="hover:bg-slate-50">
                <td className="px-4 py-3 flex items-center gap-2">
                  <Avatar name={t.employeeName} size="sm" />
                  <span className="font-medium text-slate-800">{t.employeeName}</span>
                </td>
                <td className="px-4 py-3 text-slate-600">{t.assetName}</td>
                <td className="px-4 py-3 text-slate-500">{t.issueType}</td>
                <td className="px-4 py-3 text-slate-500 text-xs max-w-[180px] truncate">{t.description}</td>
                <td className="px-4 py-3"><Badge label={t.serviceStatus} /></td>
                <td className="px-4 py-3">
                  {t.serviceStatus !== "COMPLETED" && t.serviceStatus !== "REJECTED" && (
                    <button
                      onClick={() => { setSelected(t); setNewStatus("IN_PROGRESS"); setModal("update"); }}
                      className="text-slate-400 hover:text-blue-600 text-sm"
                    >
                      🔧
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {filtered.length === 0 && <p className="text-center text-slate-400 py-8">No tickets</p>}
      </div>

      <Modal open={modal === "update"} onClose={() => setModal(null)} title="Update Service Request">
        <ErrBanner msg={error} onClose={() => setError("")} />
        <div className="mt-2">
          <Select label="New Status" value={newStatus} onChange={(e) => setNewStatus(e.target.value)}>
            <option value="IN_PROGRESS">In Progress</option>
            <option value="COMPLETED">Completed</option>
            <option value="REJECTED">Rejected</option>
          </Select>
        </div>
        <div className="flex gap-3 mt-6">
          <Btn variant="ghost" className="flex-1" onClick={() => setModal(null)}>Cancel</Btn>
          <Btn className="flex-1" onClick={updateStatus} disabled={saving}>
            {saving ? "Updating…" : "Update Status"}
          </Btn>
        </div>
      </Modal>
    </div>
  );
}
