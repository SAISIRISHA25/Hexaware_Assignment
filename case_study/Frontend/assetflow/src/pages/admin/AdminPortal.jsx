// ─── Admin Portal Router ──────────────────────────────────────────────────────
// Receives activePage + renders the correct admin page.
import { AdminDashboard }       from "./AdminDashboard";
import { EmployeeManagement }   from "./EmployeeManagement";
import { AssetManagement }      from "./AssetManagement";
import { CategoryManagement }   from "./CategoryManagement";
import { AllocationManagement } from "./AllocationManagement";
import { RequestApprovals }     from "./RequestApprovals";
import { ServiceRequestsAdmin } from "./ServiceRequestsAdmin";
import { ReturnRequestsAdmin }  from "./ReturnRequestsAdmin";
import { AuditManagement }      from "./AuditManagement";
import { Reports }              from "./Reports";
import { ADMIN_NAV }            from "../../constants/navigation";

// Map nav id → human label
const PAGE_LABELS = Object.fromEntries(ADMIN_NAV.map((n) => [n.id, n.label]));

export function AdminPortal({ activePage, token }) {
  const props = { token };

  switch (activePage) {
    case "dashboard":   return <AdminDashboard   {...props} />;
    case "employees":   return <EmployeeManagement {...props} />;
    case "assets":      return <AssetManagement  {...props} />;
    case "categories":  return <CategoryManagement {...props} />;
    case "allocations": return <AllocationManagement {...props} />;
    case "requests":    return <RequestApprovals {...props} />;
    case "services":    return <ServiceRequestsAdmin {...props} />;
    case "returns":     return <ReturnRequestsAdmin {...props} />;
    case "audits":      return <AuditManagement  {...props} />;
    case "reports":     return <Reports          {...props} />;
    default:            return <AdminDashboard   {...props} />;
  }
}

export function getAdminPageTitle(activePage) {
  return PAGE_LABELS[activePage] || "Dashboard";
}
