// ─── Employee Management Page ─────────────────────────────────────────────────
import { useState, useEffect, useCallback } from "react";
import { getAllUsers, deleteUser } from "../../services/employeeService";
import { Spinner } from "../../components/ui/Spinner";
import { ErrBanner } from "../../components/ui/ErrBanner";
import { Badge } from "../../components/ui/Badge";
import { Avatar } from "../../components/ui/Avatar";
import { cx } from "../../components/ui/cx";

export function EmployeeManagement({ token }) {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading]     = useState(true);
  const [error, setError]         = useState("");
  const [search, setSearch]       = useState("");

  const load = useCallback(() => {
    setLoading(true);
    getAllUsers(token)
      .then((d) => setEmployees(d || []))
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token]);

  useEffect(() => { load(); }, [load]);

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this employee?")) return;
    try {
      await deleteUser(id, token);
      setEmployees((es) => es.filter((e) => e.userId !== id));
    } catch (e) {
      setError(e.message);
    }
  };

  const filtered = employees.filter(
    (e) =>
      !search ||
      e.fullName?.toLowerCase().includes(search.toLowerCase()) ||
      e.email?.includes(search)
  );

  if (loading) return <Spinner />;

  return (
    <div className="space-y-5">
      <ErrBanner msg={error} onClose={() => setError("")} />

      {/* Stats */}
      <div className="grid grid-cols-4 gap-4">
        {[
          { label: "Total Employees", val: employees.length,                                       color: "text-blue-600"    },
          { label: "Active",          val: employees.filter((e) => e.status === "ACTIVE").length,   color: "text-emerald-600" },
          { label: "Inactive",        val: employees.filter((e) => e.status !== "ACTIVE").length,   color: "text-slate-600"   },
          { label: "Admins",          val: employees.filter((e) => e.role === "ROLE_ADMIN").length, color: "text-purple-600"  },
        ].map((s) => (
          <div key={s.label} className="bg-white rounded-xl border border-slate-100 shadow-sm p-5 text-center">
            <p className={cx("text-3xl font-black", s.color)}>{s.val}</p>
            <p className="text-sm text-slate-500 mt-1">{s.label}</p>
          </div>
        ))}
      </div>

      {/* Table */}
      <div className="bg-white rounded-xl border border-slate-100 shadow-sm">
        <div className="p-4 border-b border-slate-100 flex items-center gap-3">
          <div className="relative flex-1 max-w-xs">
            <span className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400 text-sm">🔍</span>
            <input
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              placeholder="Search employees..."
              className="w-full border border-slate-200 rounded-lg pl-9 pr-4 py-2 text-sm outline-none focus:border-blue-400 bg-slate-50"
            />
          </div>
        </div>
        <table className="w-full text-sm">
          <thead className="border-b border-slate-100">
            <tr>
              {["Employee", "Department", "Contact", "Role", "Status", "Actions"].map((h) => (
                <th key={h} className="px-4 py-3 text-left text-xs font-semibold text-slate-500">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-50">
            {filtered.map((emp) => (
              <tr key={emp.userId} className="hover:bg-slate-50">
                <td className="px-4 py-3">
                  <div className="flex items-center gap-3">
                    <Avatar name={emp.fullName} size="sm" />
                    <div>
                      <p className="font-semibold text-slate-800">{emp.fullName}</p>
                      <p className="text-xs text-slate-400">{emp.designation}</p>
                    </div>
                  </div>
                </td>
                <td className="px-4 py-3 text-slate-600">{emp.department || "—"}</td>
                <td className="px-4 py-3">
                  <p className="text-xs text-slate-600">✉ {emp.email}</p>
                  {emp.phone && <p className="text-xs text-slate-500">📞 {emp.phone}</p>}
                </td>
                <td className="px-4 py-3"><Badge label={emp.role} /></td>
                <td className="px-4 py-3"><Badge label={emp.status} /></td>
                <td className="px-4 py-3">
                  <button
                    onClick={() => handleDelete(emp.userId)}
                    className="text-slate-400 hover:text-red-500 text-sm"
                  >
                    🗑
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {filtered.length === 0 && (
          <p className="text-center text-slate-400 py-8">No employees found</p>
        )}
      </div>
    </div>
  );
}
