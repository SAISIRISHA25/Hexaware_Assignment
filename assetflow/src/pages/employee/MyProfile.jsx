// ─── My Profile Page ──────────────────────────────────────────────────────────
import { Avatar } from "../../components/ui/Avatar";
import { Badge } from "../../components/ui/Badge";

export function MyProfile({ user }) {
  if (!user) return null;

  const fields = [
    { label: "Full Name",    value: user.fullName    },
    { label: "Email",        value: user.email       },
    { label: "Phone",        value: user.phone       },
    { label: "Department",   value: user.department  },
    { label: "Designation",  value: user.designation },
  ];

  return (
    <div className="max-w-2xl mx-auto space-y-5">
      {/* Profile hero */}
      <div className="bg-white rounded-2xl border border-slate-100 shadow-sm overflow-hidden">
        <div className="h-24 bg-gradient-to-r from-blue-600 to-blue-400" />
        <div className="px-6 pb-6">
          <div className="-mt-10 mb-4">
            <div className="w-20 h-20 bg-blue-600 rounded-2xl flex items-center justify-center text-white text-3xl font-black border-4 border-white shadow-md">
              {user.fullName?.[0]?.toUpperCase() || "U"}
            </div>
          </div>
          <div className="flex items-start justify-between">
            <div>
              <h2 className="text-2xl font-black text-slate-900">{user.fullName}</h2>
              <p className="text-slate-500 text-sm">{user.designation || "Employee"}</p>
              <p className="text-slate-400 text-sm">{user.department}</p>
            </div>
            <Badge label={user.role} />
          </div>
        </div>
      </div>

      {/* Details */}
      <div className="bg-white rounded-xl border border-slate-100 shadow-sm p-6">
        <h3 className="font-bold text-slate-800 mb-4">Profile Information</h3>
        <div className="space-y-4">
          {fields.map((f) => (
            <div key={f.label} className="flex items-center gap-4 py-3 border-b border-slate-50 last:border-0">
              <p className="w-32 text-sm text-slate-400 font-medium">{f.label}</p>
              <p className="text-sm text-slate-800 font-semibold">{f.value || "—"}</p>
            </div>
          ))}
          <div className="flex items-center gap-4 py-3 border-b border-slate-50">
            <p className="w-32 text-sm text-slate-400 font-medium">Account Status</p>
            <Badge label={user.status} />
          </div>
          <div className="flex items-center gap-4 py-3">
            <p className="w-32 text-sm text-slate-400 font-medium">Role</p>
            <Badge label={user.role} />
          </div>
        </div>
      </div>
    </div>
  );
}
