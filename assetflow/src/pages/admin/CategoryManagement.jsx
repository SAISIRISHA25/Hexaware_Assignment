// ─── Category Management Page ─────────────────────────────────────────────────
import { useState, useEffect, useCallback } from "react";
import { getAllCategories, createCategory, updateCategory, deleteCategory } from "../../services/categoryService";
import { Spinner } from "../../components/ui/Spinner";
import { ErrBanner } from "../../components/ui/ErrBanner";
import { Modal } from "../../components/ui/Modal";
import { Input } from "../../components/ui/Input";
import { Textarea } from "../../components/ui/Textarea";
import { Btn } from "../../components/ui/Btn";
import { CAT_COLORS, CAT_ICONS } from "../../constants/categories";

export function CategoryManagement({ token }) {
  const [cats, setCats]       = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError]     = useState("");
  const [modal, setModal]     = useState(null);
  const [editing, setEditing] = useState(null);
  const [saving, setSaving]   = useState(false);
  const [form, setForm]       = useState({ categoryName: "", description: "" });

  const load = useCallback(() => {
    getAllCategories(token)
      .then((d) => setCats(d || []))
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [token]);

  useEffect(() => { load(); }, [load]);

  const openAdd  = () => { setForm({ categoryName: "", description: "" }); setEditing(null); setModal("form"); };
  const openEdit = (c) => { setForm({ categoryName: c.categoryName, description: c.description || "" }); setEditing(c); setModal("form"); };

  const save = async () => {
    setSaving(true);
    setError("");
    try {
      if (editing) {
        await updateCategory(editing.categoryId, form, token);
      } else {
        await createCategory(form, token);
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
    if (!window.confirm("Delete this category?")) return;
    try {
      await deleteCategory(id, token);
      load();
    } catch (e) {
      setError(e.message);
    }
  };

  if (loading) return <Spinner />;

  return (
    <div className="space-y-5">
      <ErrBanner msg={error} onClose={() => setError("")} />

      <div className="bg-white rounded-xl border border-slate-100 shadow-sm p-5">
        <div className="flex items-center justify-between mb-4">
          <div>
            <h3 className="font-bold text-slate-800">Asset Categories</h3>
            <p className="text-xs text-slate-500">Manage categories for assets</p>
          </div>
          <Btn onClick={openAdd}>+ Add Category</Btn>
        </div>
        <div className="grid grid-cols-4 gap-4">
          {cats.map((c, idx) => {
            const color = CAT_COLORS[idx % CAT_COLORS.length];
            const icon  = CAT_ICONS[idx % CAT_ICONS.length];
            return (
              <div key={c.categoryId} className="border border-slate-200 rounded-xl p-4 hover:shadow-sm transition-all relative overflow-hidden">
                <div className="absolute top-0 left-0 right-0 h-1 rounded-t-xl" style={{ background: color }} />
                <div className="w-10 h-10 rounded-xl flex items-center justify-center text-xl mt-1 mb-3" style={{ background: color + "20" }}>
                  {icon}
                </div>
                <h4 className="font-bold text-slate-800 text-sm">{c.categoryName}</h4>
                <p className="text-xs text-slate-400 mt-0.5">{c.description}</p>
                <div className="flex gap-1 mt-3">
                  <button onClick={() => openEdit(c)} className="text-slate-400 hover:text-blue-600 text-sm">✏</button>
                  <button onClick={() => handleDelete(c.categoryId)} className="text-slate-400 hover:text-red-500 text-sm">🗑</button>
                </div>
              </div>
            );
          })}
        </div>
      </div>

      <Modal open={modal === "form"} onClose={() => setModal(null)} title={editing ? "Edit Category" : "Add New Category"}>
        <ErrBanner msg={error} onClose={() => setError("")} />
        <div className="flex flex-col gap-4 mt-2">
          <Input label="Category Name" required placeholder="e.g. Laptops"
            value={form.categoryName} onChange={(e) => setForm((f) => ({ ...f, categoryName: e.target.value }))} />
          <Textarea label="Description" rows={3} placeholder="Brief description..."
            value={form.description} onChange={(e) => setForm((f) => ({ ...f, description: e.target.value }))} />
        </div>
        <div className="flex gap-3 mt-6">
          <Btn variant="ghost" className="flex-1" onClick={() => setModal(null)}>Cancel</Btn>
          <Btn className="flex-1" onClick={save} disabled={saving}>
            {saving ? "Saving…" : editing ? "Update" : "Create Category"}
          </Btn>
        </div>
      </Modal>
    </div>
  );
}
