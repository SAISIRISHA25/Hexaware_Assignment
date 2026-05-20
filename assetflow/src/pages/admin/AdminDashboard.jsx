// ─── Admin Dashboard Page ─────────────────────────────────────────────────────
import { useState, useEffect } from "react";
import {
  BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip,
  ResponsiveContainer, PieChart, Pie, Cell,
} from "recharts";
import { getAllUsers } from "../../services/employeeService";
import { getAllAssets } from "../../services/assetService";
import { getAllAssetRequests } from "../../services/assetRequestService";
import { getAllAllocations } from "../../services/allocationService";
import { Spinner } from "../../components/ui/Spinner";
import { Badge } from "../../components/ui/Badge";
import { Avatar } from "../../components/ui/Avatar";
import { cx } from "../../components/ui/cx";

export function AdminDashboard({ token }) {
  const [users, setUsers]               = useState([]);
  const [assets, setAssets]             = useState([]);
  const [assetRequests, setAssetRequests] = useState([]);
  const [allocations, setAllocations]   = useState([]);
  const [loading, setLoading]           = useState(true);

  useEffect(() => {
    Promise.all([
      getAllUsers(token),
      getAllAssets(token),
      getAllAssetRequests(token),
      getAllAllocations(token),
    ])
      .then(([u, a, ar, al]) => {
        setUsers(u || []);
        setAssets(a?.content || a || []);
        setAssetRequests(ar || []);
        setAllocations(al || []);
      })
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [token]);

  if (loading) return <Spinner />;

  const countByField = (arr, key, val) => (arr || []).filter((x) => x[key] === val).length;
  const pending      = countByField(assetRequests, "requestStatus", "PENDING");
  const allocated    = countByField(assets, "assetStatus", "ALLOCATED");
  const available    = countByField(assets, "assetStatus", "AVAILABLE");
  const underService = countByField(assets, "assetStatus", "UNDER_SERVICE");

  const donutData = [
    { name: "Allocated",   value: allocated,    color: "#3B82F6" },
    { name: "Available",   value: available,    color: "#10B981" },
    { name: "In Service",  value: underService, color: "#F59E0B" },
  ].filter((d) => d.value > 0);

  const recentRequests = (assetRequests || []).slice(0, 6);

  return (
    <div className="space-y-6">
      {/* Hero banner */}
      <div className="bg-gradient-to-r from-blue-900 to-blue-700 rounded-2xl p-6 text-white relative overflow-hidden">
        <div className="absolute right-0 top-0 w-64 h-full opacity-10 flex items-center justify-end pr-8">
          <div className="text-9xl">📦</div>
        </div>
        <p className="text-blue-300 text-sm font-medium mb-1">Administrator Portal</p>
        <h2 className="text-2xl font-black mb-1">System Overview Dashboard</h2>
        <p className="text-blue-200 text-sm">Real-time asset management metrics and analytics</p>
      </div>

      {/* Stat cards */}
      <div className="grid grid-cols-4 gap-4">
        {[
          { label: "Total Employees",    val: users.length,                                                          sub: "Active users",       icon: "👥", color: "text-blue-600"    },
          { label: "Total Assets",       val: assets?.length || 0,                                                   sub: `${allocated} allocated`, icon: "📦", color: "text-purple-600" },
          { label: "Pending Requests",   val: pending,                                                               sub: "Awaiting review",    icon: "⏳", color: "text-orange-500"  },
          { label: "Active Allocations", val: countByField(allocations, "allocationStatus", "ACTIVE"),               sub: "Currently active",   icon: "🔗", color: "text-emerald-600" },
        ].map((s) => (
          <div key={s.label} className="bg-white rounded-xl border border-slate-100 p-5 shadow-sm">
            <div className="flex items-center justify-between mb-3">
              <p className="text-sm text-slate-500">{s.label}</p>
              <span className="text-2xl">{s.icon}</span>
            </div>
            <p className={cx("text-3xl font-black", s.color)}>{s.val}</p>
            <p className="text-xs text-slate-400 mt-1">{s.sub}</p>
          </div>
        ))}
      </div>

      {/* Charts row */}
      <div className="grid grid-cols-3 gap-4">
        <div className="col-span-2 bg-white rounded-xl border border-slate-100 shadow-sm p-5">
          <h3 className="font-bold text-slate-800 mb-1">Asset Status Distribution</h3>
          <p className="text-xs text-slate-500 mb-4">Current allocation breakdown</p>
          <ResponsiveContainer width="100%" height={200}>
            <BarChart data={[
              { name: "Available",    count: available    },
              { name: "Allocated",    count: allocated    },
              { name: "Under Service", count: underService },
            ]}>
              <CartesianGrid strokeDasharray="3 3" stroke="#F1F5F9" />
              <XAxis dataKey="name" tick={{ fontSize: 11, fill: "#94A3B8" }} />
              <YAxis tick={{ fontSize: 11, fill: "#94A3B8" }} />
              <Tooltip />
              <Bar dataKey="count" fill="#3B82F6" radius={[4, 4, 0, 0]} name="Count" />
            </BarChart>
          </ResponsiveContainer>
        </div>

        <div className="bg-white rounded-xl border border-slate-100 shadow-sm p-5">
          <h3 className="font-bold text-slate-800 mb-1">Allocation Breakdown</h3>
          <ResponsiveContainer width="100%" height={160}>
            <PieChart>
              <Pie
                data={donutData.length ? donutData : [{ name: "No Data", value: 1, color: "#CBD5E1" }]}
                cx="50%" cy="50%" innerRadius={45} outerRadius={70} dataKey="value"
              >
                {(donutData.length ? donutData : [{ color: "#CBD5E1" }]).map((d, i) => (
                  <Cell key={i} fill={d.color} />
                ))}
              </Pie>
            </PieChart>
          </ResponsiveContainer>
          {donutData.map((d) => (
            <div key={d.name} className="flex items-center justify-between text-xs mt-1">
              <div className="flex items-center gap-1.5">
                <div className="w-2.5 h-2.5 rounded-full" style={{ background: d.color }} />
                {d.name}
              </div>
              <span className="font-semibold">{d.value}</span>
            </div>
          ))}
        </div>
      </div>

      {/* Recent requests table */}
      <div className="bg-white rounded-xl border border-slate-100 shadow-sm p-5">
        <div className="flex items-center justify-between mb-4">
          <div>
            <h3 className="font-bold text-slate-800">Recent Asset Requests</h3>
            <p className="text-xs text-slate-500">Latest pending and processed requests</p>
          </div>
        </div>
        {recentRequests.length === 0 ? (
          <p className="text-center text-slate-400 py-8">No requests yet</p>
        ) : (
          <table className="w-full text-sm">
            <thead>
              <tr className="text-left border-b border-slate-100">
                {["Employee", "Category", "Status", "Date"].map((h) => (
                  <th key={h} className="pb-3 text-xs font-semibold text-slate-500">{h}</th>
                ))}
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-50">
              {recentRequests.map((r) => (
                <tr key={r.requestId} className="hover:bg-slate-50">
                  <td className="py-3 flex items-center gap-2">
                    <Avatar name={r.employeeName} size="sm" />
                    <span className="font-medium text-slate-800">{r.employeeName}</span>
                  </td>
                  <td className="py-3 text-slate-600">{r.categoryName}</td>
                  <td className="py-3"><Badge label={r.requestStatus} /></td>
                  <td className="py-3 text-slate-400 text-xs">{r.requestDate?.split("T")[0]}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}
