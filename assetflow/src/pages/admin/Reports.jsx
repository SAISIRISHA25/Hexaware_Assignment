// ─── Reports & Analytics Page ─────────────────────────────────────────────────
import { useState, useEffect } from "react";
import {
  BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend,
  ResponsiveContainer, RadarChart, Radar, PolarGrid, PolarAngleAxis,
} from "recharts";
import { getAllAssets } from "../../services/assetService";
import { getAllAssetRequests } from "../../services/assetRequestService";
import { getAllServiceRequests } from "../../services/serviceRequestService";
import { getAllUsers } from "../../services/employeeService";
import { Spinner } from "../../components/ui/Spinner";
import { cx } from "../../components/ui/cx";

const RADAR_DATA = [
  { subject: "Asset Utilization",    value: 86 },
  { subject: "Audit Compliance",     value: 72 },
  { subject: "Request Resolution",   value: 78 },
  { subject: "Service SLA",          value: 82 },
  { subject: "Employee Satisfaction", value: 65 },
];

export function Reports({ token }) {
  const [data, setData] = useState(null);

  useEffect(() => {
    Promise.all([
      getAllAssets(token),
      getAllAssetRequests(token),
      getAllServiceRequests(token),
      getAllUsers(token),
    ])
      .then(([a, ar, sr, u]) => {
        setData({
          assets:          a?.content || a || [],
          assetRequests:   ar || [],
          serviceRequests: sr || [],
          users:           u  || [],
        });
      })
      .catch(console.error);
  }, [token]);

  if (!data) return <Spinner />;

  const totalValue   = data.assets.reduce((s, a) => s + Number(a.assetValue || 0), 0);
  const approvalRate = data.assetRequests.length
    ? Math.round(
        (data.assetRequests.filter((r) => r.requestStatus === "APPROVED").length /
          data.assetRequests.length) *
          100
      )
    : 0;

  return (
    <div className="space-y-4">
      {/* Summary cards */}
      <div className="grid grid-cols-4 gap-4">
        {[
          { label: "Total Asset Value", val: `₹${(totalValue / 1000).toFixed(0)}K`, color: "text-blue-700",    bg: "bg-blue-50 border-blue-100"     },
          { label: "Total Assets",      val: data.assets.length,                     color: "text-purple-700",  bg: "bg-purple-50 border-purple-100"  },
          { label: "Approval Rate",     val: `${approvalRate}%`,                     color: "text-emerald-700", bg: "bg-emerald-50 border-emerald-100" },
          { label: "Total Employees",   val: data.users.length,                      color: "text-amber-700",   bg: "bg-amber-50 border-amber-100"    },
        ].map((s) => (
          <div key={s.label} className={cx("rounded-xl border p-5 shadow-sm", s.bg)}>
            <p className="text-xs text-slate-500 mb-2">{s.label}</p>
            <p className={cx("text-3xl font-black", s.color)}>{s.val}</p>
          </div>
        ))}
      </div>

      {/* Charts */}
      <div className="grid grid-cols-2 gap-4">
        <div className="bg-white rounded-xl border border-slate-100 shadow-sm p-5">
          <h3 className="font-bold text-slate-800 mb-4">Asset Request Status</h3>
          <ResponsiveContainer width="100%" height={220}>
            <BarChart data={[
              { name: "Pending",  count: data.assetRequests.filter((r) => r.requestStatus === "PENDING").length  },
              { name: "Approved", count: data.assetRequests.filter((r) => r.requestStatus === "APPROVED").length },
              { name: "Rejected", count: data.assetRequests.filter((r) => r.requestStatus === "REJECTED").length },
            ]}>
              <CartesianGrid strokeDasharray="3 3" stroke="#F1F5F9" />
              <XAxis dataKey="name" tick={{ fontSize: 11, fill: "#94A3B8" }} />
              <YAxis tick={{ fontSize: 11, fill: "#94A3B8" }} />
              <Tooltip /><Legend />
              <Bar dataKey="count" fill="#3B82F6" radius={[3, 3, 0, 0]} name="Count" />
            </BarChart>
          </ResponsiveContainer>
        </div>

        <div className="bg-white rounded-xl border border-slate-100 shadow-sm p-5">
          <h3 className="font-bold text-slate-800 mb-4">Performance Metrics</h3>
          <ResponsiveContainer width="100%" height={220}>
            <RadarChart data={RADAR_DATA}>
              <PolarGrid />
              <PolarAngleAxis dataKey="subject" tick={{ fontSize: 10 }} />
              <Radar dataKey="value" stroke="#3B82F6" fill="#3B82F6" fillOpacity={0.2} />
            </RadarChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
}
