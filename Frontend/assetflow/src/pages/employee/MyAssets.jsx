// ─── My Assets Page ───────────────────────────────────────────────────────────
import { useState, useEffect, useCallback } from "react";
import { getMyAllocations } from "../../services/allocationService";
import { Spinner } from "../../components/ui/Spinner";
import { ErrBanner } from "../../components/ui/ErrBanner";
import { Badge } from "../../components/ui/Badge";
import { cx } from "../../components/ui/cx";

export function MyAssets({ token }) {
  const [allocations, setAllocations] = useState([]);
  const [loading, setLoading]         = useState(true);
  const [error, setError]             = useState("");
  const [tab, setTab]                 = useState("ACTIVE");

  const load = useCallback(() => {
    setLoading(true);
    getMyAllocations(token)
      .then((d) => setAllocations(d || []))
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token]);

  useEffect(() => { load(); }, [load]);

  const filtered = tab === "ALL" ? allocations : allocations.filter((a) => a.allocationStatus === tab);

  if (loading) return <Spinner />;

  return (
    <div className="space-y-5">
      <ErrBanner msg={error} onClose={() => setError("")} />

      {/* Tabs */}
      <div className="flex gap-2">
        {["ALL", "ACTIVE", "CLOSED"].map((t) => (
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
            {t} ({t === "ALL" ? allocations.length : allocations.filter((a) => a.allocationStatus === t).length})
          </button>
        ))}
      </div>

      {/* Asset cards */}
      <div className="grid grid-cols-3 gap-4">
        {filtered.map((a) => (
          <div key={a.allocationId} className="bg-white rounded-xl border border-slate-100 shadow-sm overflow-hidden hover:shadow-md transition-all">
            <div className="h-2 bg-gradient-to-r from-blue-500 to-blue-400" />
            <div className="p-5">
              <div className="flex items-start justify-between mb-3">
                <div className="w-12 h-12 bg-blue-50 rounded-xl flex items-center justify-center text-2xl">
                  🖥
                </div>
                <Badge label={a.allocationStatus} />
              </div>
              <h4 className="font-bold text-slate-800">{a.assetName}</h4>
              <p className="text-xs text-slate-400 mt-0.5">Asset No: {a.assetNo || "—"}</p>
              <div className="mt-4 space-y-1 text-xs">
                <div className="flex justify-between">
                  <span className="text-slate-400">Allocated</span>
                  <span className="text-slate-700">{a.allocatedDate || "—"}</span>
                </div>
                {a.expectedReturnDate && (
                  <div className="flex justify-between">
                    <span className="text-slate-400">Expected Return</span>
                    <span className="text-slate-700">{a.expectedReturnDate}</span>
                  </div>
                )}
              </div>
            </div>
          </div>
        ))}
      </div>

      {filtered.length === 0 && (
        <div className="bg-white rounded-xl border border-slate-100 shadow-sm p-12 text-center">
          <div className="text-5xl mb-3">📦</div>
          <p className="text-slate-800 font-semibold">No assets found</p>
          <p className="text-slate-400 text-sm mt-1">You have no {tab.toLowerCase()} allocations</p>
        </div>
      )}
    </div>
  );
}
