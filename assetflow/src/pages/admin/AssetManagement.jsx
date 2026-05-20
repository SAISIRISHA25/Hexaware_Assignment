// ─── Asset Management Page ────────────────────────────────────────────────────
import { useState, useEffect, useCallback } from "react";
import { getAllAssets, createAsset, updateAsset, deleteAsset } from "../../services/assetService";
import { getAllCategories } from "../../services/categoryService";
import { getAllAllocations } from "../../services/allocationService";
import { Spinner } from "../../components/ui/Spinner";
import { ErrBanner } from "../../components/ui/ErrBanner";
import { Badge } from "../../components/ui/Badge";
import { Avatar } from "../../components/ui/Avatar";
import { Modal } from "../../components/ui/Modal";
import { Input } from "../../components/ui/Input";
import { Select } from "../../components/ui/Select";
import { Textarea } from "../../components/ui/Textarea";
import { Btn } from "../../components/ui/Btn";
import { cx } from "../../components/ui/cx";

const STATUS_TABS = ["All Assets", "AVAILABLE", "ALLOCATED", "UNDER_SERVICE"];

const BLANK_FORM = {
  assetNo: "", assetName: "", assetModel: "", assetValue: "",
  assetCondition: "Excellent", categoryId: "",
  manufacturingDate: "", expiryDate: "", description: "", imageUrl: "",
};

export function AssetManagement({ token }) {
  const [assets, setAssets]       = useState([]);
  const [categories, setCategories] = useState([]);
  const [allocMap, setAllocMap]   = useState({});
  const [loading, setLoading]     = useState(true);
  const [error, setError]         = useState("");
  const [modal, setModal]         = useState(null);
  const [editing, setEditing]     = useState(null);
  const [saving, setSaving]       = useState(false);
  const [form, setForm]           = useState(BLANK_FORM);
  const [activeTab, setActiveTab] = useState("All Assets");
  const [search, setSearch]       = useState("");

  const load = useCallback(() => {
    setLoading(true);
    Promise.all([
      getAllAssets(token),
      getAllCategories(token),
      getAllAllocations(token),
    ])
      .then(([a, c, al]) => {
        setAssets(a?.content || a || []);
        setCategories(c || []);
        const map = {};
        (al || [])
          .filter((x) => x.allocationStatus === "ACTIVE")
          .forEach((x) => { map[x.assetName] = x.employeeName; });
        setAllocMap(map);
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token]);

  useEffect(() => { load(); }, [load]);

  const filtered = assets.filter(
    (a) =>
      (!search || a.assetName?.toLowerCase().includes(search.toLowerCase()) || a.assetNo?.includes(search)) &&
      (activeTab === "All Assets" || a.assetStatus === activeTab)
  );

  const openAdd = () => { setForm(BLANK_FORM); setEditing(null); setModal("form"); };
  const openEdit = (a) => {
    setForm({
      assetNo: a.assetNo || "", assetName: a.assetName, assetModel: a.assetModel || "",
      assetValue: String(a.assetValue || ""), assetCondition: a.assetCondition || "Excellent",
      categoryId: "", manufacturingDate: a.manufacturingDate || "",
      expiryDate: a.expiryDate || "", description: a.description || "", imageUrl: a.imageUrl || "",
    });
    setEditing(a);
    setModal("form");
  };

  const save = async () => {
    setSaving(true);
    setError("");
    try {
      const payload = {
        assetNo: form.assetNo, assetName: form.assetName, assetModel: form.assetModel,
        assetValue: parseFloat(form.assetValue), assetCondition: form.assetCondition,
        categoryId: parseInt(form.categoryId),
        ...(form.manufacturingDate ? { manufacturingDate: form.manufacturingDate } : {}),
        ...(form.expiryDate ? { expiryDate: form.expiryDate } : {}),
        description: form.description,
        ...(form.imageUrl ? { imageUrl: form.imageUrl } : {}),
      };
      if (editing) {
        await updateAsset(editing.assetId, payload, token);
      } else {
        await createAsset(payload, token);
      }
      setModal(null);
      load();
    } catch (e) {
      setError(e.message);
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this asset?")) return;
    try {
      await deleteAsset(id, token);
      load();
    } catch (e) {
      setError(e.message);
    }
  };

  if (loading) return <Spinner />;

  const counts = { "All Assets": assets.length };
  ["AVAILABLE", "ALLOCATED", "UNDER_SERVICE"].forEach((s) => {
    counts[s] = assets.filter((a) => a.assetStatus === s).length;
  });

  return (
    <div className="space-y-5">
      <ErrBanner msg={error} onClose={() => setError("")} />

      {/* Tab stat cards */}
      <div className="grid grid-cols-4 gap-3">
        {STATUS_TABS.map((t) => (
          <button
            key={t}
            onClick={() => setActiveTab(t)}
            className={cx(
              "bg-white rounded-xl border shadow-sm p-4 text-center transition-all",
              activeTab === t ? "border-blue-500 ring-1 ring-blue-200" : "border-slate-100 hover:border-blue-200"
            )}
          >
            <p className={cx("text-2xl font-black", activeTab === t ? "text-blue-600" : "text-slate-800")}>
              {counts[t]}
            </p>
            <p className="text-xs text-slate-500 mt-1">{t.replace("_", " ")}</p>
          </button>
        ))}
      </div>

      {/* Assets table */}
      <div className="bg-white rounded-xl border border-slate-100 shadow-sm">
        <div className="p-4 border-b border-slate-100 flex items-center gap-3">
          <div className="relative">
            <span className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400 text-sm">🔍</span>
            <input
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              placeholder="Search assets..."
              className="border border-slate-200 rounded-lg pl-9 pr-4 py-2 text-sm outline-none focus:border-blue-400 bg-slate-50 w-52"
            />
          </div>
          <div className="ml-auto">
            <Btn onClick={openAdd}>+ Add Asset</Btn>
          </div>
        </div>
        <table className="w-full text-sm">
          <thead className="border-b border-slate-100">
            <tr>
              {["Asset", "Asset No", "Category", "Assigned To", "Value", "Condition", "Status", "Actions"].map((h) => (
                <th key={h} className="px-4 py-3 text-left text-xs font-semibold text-slate-500">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-50">
            {filtered.map((a) => (
              <tr key={a.assetId} className="hover:bg-slate-50">
                <td className="px-4 py-3">
                  <div className="flex items-center gap-2">
                    {a.imageUrl ? (
                      <img
                        src={a.imageUrl}
                        alt={a.assetName}
                        className="w-8 h-8 rounded-lg object-cover border border-slate-100"
                        onError={(e) => { e.target.style.display = "none"; }}
                      />
                    ) : (
                      <div className="w-8 h-8 bg-blue-50 rounded-lg flex items-center justify-center text-sm">🖥</div>
                    )}
                    <div>
                      <p className="font-semibold text-slate-800">{a.assetName}</p>
                      <p className="text-xs text-slate-400">{a.assetModel}</p>
                    </div>
                  </div>
                </td>
                <td className="px-4 py-3 font-mono text-xs text-slate-500">{a.assetNo}</td>
                <td className="px-4 py-3 text-slate-600">{a.categoryName}</td>
                <td className="px-4 py-3">
                  {allocMap[a.assetName] ? (
                    <div className="flex items-center gap-1.5">
                      <Avatar name={allocMap[a.assetName]} size="sm" />
                      <span className="text-slate-700 text-xs">{allocMap[a.assetName]}</span>
                    </div>
                  ) : (
                    <span className="text-slate-400 text-xs">—</span>
                  )}
                </td>
                <td className="px-4 py-3 font-semibold text-slate-800">
                  ₹{Number(a.assetValue || 0).toLocaleString()}
                </td>
                <td className="px-4 py-3"><Badge label={a.assetCondition} /></td>
                <td className="px-4 py-3"><Badge label={a.assetStatus} /></td>
                <td className="px-4 py-3 flex gap-2">
                  <button onClick={() => openEdit(a)} className="text-slate-400 hover:text-blue-600">✏</button>
                  <button onClick={() => handleDelete(a.assetId)} className="text-slate-400 hover:text-red-500">🗑</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {filtered.length === 0 && <p className="text-center text-slate-400 py-8">No assets found</p>}
      </div>

      {/* Add / Edit Modal */}
      <Modal open={modal === "form"} onClose={() => setModal(null)} title={editing ? "Edit Asset" : "Add New Asset"} size="lg">
        <ErrBanner msg={error} onClose={() => setError("")} />
        <div className="grid grid-cols-2 gap-4 mt-2">
          <div className="col-span-2">
            <Input label="Asset Name" required placeholder="e.g. Dell XPS 15 Laptop"
              value={form.assetName} onChange={(e) => setForm((f) => ({ ...f, assetName: e.target.value }))} />
          </div>
          <Input label="Asset No" required placeholder="AST-001"
            value={form.assetNo} onChange={(e) => setForm((f) => ({ ...f, assetNo: e.target.value }))} />
          <Input label="Model" placeholder="XPS 15 9530"
            value={form.assetModel} onChange={(e) => setForm((f) => ({ ...f, assetModel: e.target.value }))} />
          <Input label="Purchase Value (₹)" required type="number" placeholder="50000"
            value={form.assetValue} onChange={(e) => setForm((f) => ({ ...f, assetValue: e.target.value }))} />
          <Select label="Category" required value={form.categoryId}
            onChange={(e) => setForm((f) => ({ ...f, categoryId: e.target.value }))}>
            <option value="">Select Category</option>
            {categories.map((c) => <option key={c.categoryId} value={c.categoryId}>{c.categoryName}</option>)}
          </Select>
          <Select label="Condition" value={form.assetCondition}
            onChange={(e) => setForm((f) => ({ ...f, assetCondition: e.target.value }))}>
            <option value="Excellent">Excellent</option>
            <option value="Good">Good</option>
            <option value="Fair">Fair</option>
          </Select>
          <Input label="Manufacturing Date" type="date"
            value={form.manufacturingDate} onChange={(e) => setForm((f) => ({ ...f, manufacturingDate: e.target.value }))} />
          <Input label="Expiry Date" type="date"
            value={form.expiryDate} onChange={(e) => setForm((f) => ({ ...f, expiryDate: e.target.value }))} />
          <div className="col-span-2">
            <Textarea label="Description" rows={2}
              value={form.description} onChange={(e) => setForm((f) => ({ ...f, description: e.target.value }))} />
          </div>
        </div>
        <div className="flex gap-3 mt-6">
          <Btn variant="ghost" className="flex-1" onClick={() => setModal(null)}>Cancel</Btn>
          <Btn className="flex-1" onClick={save} disabled={saving}>
            {saving ? "Saving…" : editing ? "Update Asset" : "Add Asset"}
          </Btn>
        </div>
      </Modal>
    </div>
  );
}
