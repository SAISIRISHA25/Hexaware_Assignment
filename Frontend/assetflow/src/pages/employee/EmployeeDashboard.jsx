// ─── Employee Dashboard Page ──────────────────────────────────────────────────
import { useState, useEffect } from "react";
import { getMyAllocations } from "../../services/allocationService";
import { getAssetRequestsByEmployee } from "../../services/assetRequestService";
import { getAuditRequestsByEmployee } from "../../services/auditService";
import { Spinner } from "../../components/ui/Spinner";
import { Badge } from "../../components/ui/Badge";
import { Btn } from "../../components/ui/Btn";
import { cx } from "../../components/ui/cx";

export function EmployeeDashboard({ user, token, onNav }) {
  const [allocations, setAllocations] = useState([]);
  const [requests, setRequests]       = useState([]);
  const [audits, setAudits]           = useState([]);
  const [loading, setLoading]         = useState(true);

  useEffect(() => {
    if (!user) return;
    Promise.all([
      getMyAllocations(token),
      getAssetRequestsByEmployee(user.userId, token),
      getAuditRequestsByEmployee(user.userId, token),
    ])
      .then(([al, ar, au]) => {
        setAllocations(al || []);
        setRequests(ar || []);
        setAudits(au || []);
      })
      .catch(console.error)
      .finally(() => setLoading(false));
  }, [user, token]);

  if (loading) return <Spinner />;

  const active  = allocations.filter((a) => a.allocationStatus === "ACTIVE");
  const pending = requests.filter((r) => r.requestStatus === "PENDING").length;

  return (
    <div className="space-y-5">
      {/* Hero banner */}
      <div className="bg-gradient-to-r from-blue-700 to-blue-500 rounded-2xl p-6 text-white relative overflow-hidden">
        <div className="flex items-start justify-between">
          <div>
            <h2 className="text-3xl font-black">{user?.fullName || "Employee"}</h2>
            <p className="text-blue-200 text-sm mt-1">
              {user?.designation || "Employee"} • {user?.department || "Department"}
            </p>
          </div>
          <div className="text-center bg-white/10 rounded-2xl px-4 py-3">
            <p className="text-xs text-blue-200">Active Assets</p>
            <p className="text-3xl font-black">{active.length}</p>
            <Btn
              size="sm"
              variant="ghost"
              className="mt-2 !bg-white !text-blue-700 text-xs"
              onClick={() => onNav("request-asset")}
            >
              + Request Asset
            </Btn>
          </div>
        </div>
      </div>

      {/* Stat cards */}
      <div className="grid grid-cols-4 gap-4">
        {[
          { label: "Active Allocations", val: active.length,                                          icon: "📦", color: "text-blue-600"    },
          { label: "Pending Requests",   val: pending,                                                icon: "⏰", color: "text-amber-500"   },
          { label: "Service Tickets",    val: 0,                                                      icon: "🔧", color: "text-purple-600"  },
          { label: "Pending Audits",     val: audits.filter((a) => a.auditStatus === "PENDING").length, icon: "📋", color: "text-emerald-600" },
        ].map((s) => (
          <div key={s.label} className="bg-white rounded-xl border border-slate-100 shadow-sm p-5">
            <div className="flex items-center justify-between mb-2">
              <p className="text-xs text-slate-500">{s.label}</p>
              <span className="text-2xl">{s.icon}</span>
            </div>
            <p className={cx("text-3xl font-black", s.color)}>{s.val}</p>
          </div>
        ))}
      </div>

      {/* Quick actions + allocations */}
      <div className="grid grid-cols-2 gap-4">
        <div className="bg-white rounded-xl border border-slate-100 shadow-sm p-5">
          <h3 className="font-bold text-slate-800 mb-4">Quick Actions</h3>
          <div className="flex flex-col gap-2">
            {[
              { label: "Request New Asset",   color: "bg-blue-600 hover:bg-blue-700",     page: "request-asset"      },
              { label: "Raise Service Ticket", color: "bg-emerald-600 hover:bg-emerald-700", page: "emp-services"     },
              { label: "Return an Asset",     color: "bg-orange-500 hover:bg-orange-600", page: "emp-returns"        },
              { label: "Audit Verification",  color: "bg-purple-600 hover:bg-purple-700", page: "audit-verification" },
            ].map((a) => (
              <button
                key={a.label}
                onClick={() => onNav(a.page)}
                className={cx(
                  "flex items-center justify-between px-4 py-3 rounded-xl text-sm font-semibold text-white transition-all",
                  a.color
                )}
              >
                <span>{a.label}</span>
                <span>→</span>
              </button>
            ))}
          </div>
        </div>

        <div className="bg-white rounded-xl border border-slate-100 shadow-sm p-5">
          <div className="flex items-center justify-between mb-4">
            <h3 className="font-bold text-slate-800">My Active Allocations</h3>
            <button onClick={() => onNav("my-assets")} className="text-blue-600 text-xs font-medium">
              View all →
            </button>
          </div>
          <div className="space-y-3">
            {active.length === 0 && <p className="text-slate-400 text-sm">No active allocations</p>}
            {active.slice(0, 4).map((a) => (
              <div key={a.allocationId} className="flex items-center gap-3 p-3 bg-slate-50 rounded-xl">
                <div className="w-9 h-9 bg-white rounded-xl border border-slate-200 flex items-center justify-center text-lg">🖥</div>
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-semibold text-slate-800 truncate">{a.assetName}</p>
                  <p className="text-xs text-slate-400">{a.assetNo}</p>
                </div>
                <Badge label={a.allocationStatus} />
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
