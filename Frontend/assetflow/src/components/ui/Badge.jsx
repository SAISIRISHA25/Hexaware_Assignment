// ─── Badge Component ──────────────────────────────────────────────────────────
import { cx } from "./cx";
import { getStatusStyle } from "../../constants/statusStyles";

export function Badge({ label, className }) {
  const display = label?.replace("ROLE_", "") || label;
  return (
    <span className={cx("px-2.5 py-0.5 rounded-full text-xs font-medium", getStatusStyle(label), className)}>
      {display}
    </span>
  );
}
